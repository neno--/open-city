package com.github.nenomm.oc.user;

import com.github.nenomm.oc.core.EntityIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User getById(EntityIdentifier identifier) {

		logger.info("getting user by id");

		return userRepository.findById(identifier).get();
	}

	public User create(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), Password.getNew(userDTO.getPassword()));

		userRepository.save(user);

		return user;
	}
}
