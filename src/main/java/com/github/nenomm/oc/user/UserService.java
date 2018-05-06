package com.github.nenomm.oc.user;

import com.github.nenomm.oc.city.City;
import com.github.nenomm.oc.city.CityDTO;
import com.github.nenomm.oc.city.CityRepository;
import com.github.nenomm.oc.config.AppProperties;
import com.github.nenomm.oc.core.EntityIdentifier;
import com.github.nenomm.oc.security.CustomUserDetails;
import com.github.nenomm.oc.token.Token;
import com.github.nenomm.oc.token.TokenDTO;
import com.github.nenomm.oc.token.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private CityRepository cityRepository;

	public User findById(EntityIdentifier identifier) {

		logger.info("getting user by id");

		return userRepository.findById(identifier).get();
	}

	public User create(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), Password.getNew(passwordEncoder.encode(userDTO.getPassword())));

		userRepository.save(user);

		return user;
	}

	public Token validateByEmailAndPassword(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail()).get();

		logger.info("found user by email: {}", user.getEmail());

		if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword().toString())) {
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
			throw new AccessDeniedException("invalid token");
		} else if (token.get().isExpired()) {
			throw new AccessDeniedException("token expired");
		}

		logger.info("token {} found for user {}", token, token.get().getUser());

		return new CustomUserDetails(token.get().getUser().getId());
	}

	public void deleteToken(TokenDTO tokenDTO) {

		Optional<Token> token = tokenRepository.findByToken(tokenDTO.getToken());

		tokenRepository.delete(token.get());
	}

	@Transactional
	public Set<City> getFavorites(EntityIdentifier identifier) {

		return findById(identifier).getFavorites();
	}

	@Transactional
	public City addFavorite(CityDTO cityDTO, EntityIdentifier identifier) {

		City city = cityRepository.findByName(cityDTO.getName()).get();
		User user = userRepository.findById(identifier).get();

		logger.info("adding city {} to favorites for user {}", user, city);

		user.addCity(city);

		return city;
	}

	@Transactional
	public City removeFavorite(CityDTO cityDTO, EntityIdentifier identifier) {

		City city = cityRepository.findByName(cityDTO.getName()).get();
		User user = userRepository.findById(identifier).get();

		logger.info("removing city {} from favorites for user {}", user, city);

		user.removeCity(city);

		return city;
	}
}
