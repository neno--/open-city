package com.github.nenomm.oc.security;

import com.github.nenomm.oc.user.User;
import com.github.nenomm.oc.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
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
	protected void initFilterBean() {

		logger.info("executing this");

		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				getFilterConfig().getServletContext());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String auth = request.getHeader("Authorization");

		// validate the value in xAuth
		if (StringUtils.isEmpty(auth)) {
			throw new SecurityException();
		}

		// The token is 'valid' so magically get a user id from it
		CustomUserDetails customUserDetails = userService.findByToken(auth);

		// Create our Authentication and let Spring know about it

		logger.info("storing user data in security context");
		SecurityContextHolder.getContext().setAuthentication(customUserDetails);

		filterChain.doFilter(request, response);
	}
}