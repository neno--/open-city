package com.github.nenomm.oc.security;

import com.github.nenomm.oc.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

		logger.info("in custom filter");

		String authorization = request.getHeader("Authorization");

		if (!StringUtils.isEmpty(authorization)) {

			CustomUserDetails customUserDetails = userService.findByToken(authorization);

			if (customUserDetails != null) {

				customUserDetails.setAuthenticated(true);

				logger.info("storing user data in security context");
				SecurityContextHolder.getContext().setAuthentication(customUserDetails);

			} else {
				logger.info("cannot use token from header");
			}
		} else {
			logger.info("no token found in header");
		}

		filterChain.doFilter(request, response);
	}
}