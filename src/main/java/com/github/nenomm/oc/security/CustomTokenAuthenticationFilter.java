package com.github.nenomm.oc.security;

import com.github.nenomm.oc.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(CustomTokenAuthenticationFilter.class);

	private UserService userService;

	public CustomTokenAuthenticationFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");

		if (StringUtils.isEmpty(authorization)) {
			throw new AccessDeniedException("missing token");
		}

		// Retrieve user related data from the token
		CustomUserDetails customUserDetails = userService.findByToken(authorization);

		// Create our Authentication and let Spring know about it
		logger.info("storing user data in security context");
		SecurityContextHolder.getContext().setAuthentication(customUserDetails);

		filterChain.doFilter(request, response);
	}
}