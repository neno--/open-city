package com.github.nenomm.oc.user;

import com.github.nenomm.oc.config.AppProperties;
import com.github.nenomm.oc.core.EntityIdentifier;
import com.github.nenomm.oc.security.CustomUserDetails;
import com.github.nenomm.oc.token.Token;
import com.github.nenomm.oc.token.TokenDTO;
import com.github.nenomm.oc.token.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	public User findById(EntityIdentifier identifier) {

		logger.info("getting user by id");

		return userRepository.findById(identifier).get();
	}

	public User create(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), Password.getNew(userDTO.getPassword()));

		userRepository.save(user);

		return user;
	}

	public Token validateByEmailAndPassword(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail()).get();

		logger.info("found user by email: {}", user.getEmail());

		if (!user.getPassword().equals(Password.getNew(userDTO.getPassword()))) {
			logger.info("password do not match for user {}", user);
			throw new RuntimeException("invalid credentials");
		}

		Token token = new Token(user, OffsetDateTime.now().plusSeconds(appProperties.getToken().getLifetime()));
		logger.info("created new token {} for user {}", token, user);

		tokenRepository.save(token);

		return token;
	}

	@Transactional
	public CustomUserDetails findByToken(String tokenValue) {

		Optional<Token> token = tokenRepository.findByToken(tokenValue);

		if (!token.isPresent()) {
			logger.info("no token found for value {}", tokenValue);
			return null;
		} else if (token.get().isExpired()) {
			logger.info("token {} expired", token.get());
			return null;
		}

		logger.info("token {} found for user {}", token, token.get().getUser());

		return new CustomUserDetails(token.get().getUser().getId());
	}

	public void deleteToken(TokenDTO tokenDTO) {

		Optional<Token> token = tokenRepository.findByToken(tokenDTO.getToken());

		tokenRepository.delete(token.get());
	}
}
