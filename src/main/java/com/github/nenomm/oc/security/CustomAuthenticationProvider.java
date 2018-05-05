package com.github.nenomm.oc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);


	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication;

		logger.info("User login: {}", customUserDetails.getPrincipal());

		return customUserDetails;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUserDetails.class.isAssignableFrom(authentication);
	}
}
