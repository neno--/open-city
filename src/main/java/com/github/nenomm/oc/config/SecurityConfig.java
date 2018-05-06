package com.github.nenomm.oc.config;

import com.github.nenomm.oc.rest.ExceptionTranslator;
import com.github.nenomm.oc.security.CustomAuthenticationProvider;
import com.github.nenomm.oc.security.CustomExceptionHandlerFilter;
import com.github.nenomm.oc.security.CustomTokenAuthenticationFilter;
import com.github.nenomm.oc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/v1/cities/**").permitAll()
				.antMatchers("/v1/token").permitAll()
				.antMatchers("/v1/users").permitAll()
				.antMatchers(HttpMethod.POST, "/v1/cities/**").authenticated()
				.antMatchers("/v1/users/**/favorites").authenticated();

		http.requestMatcher(new AntPathRequestMatcher("/v1/users/cities/**", HttpMethod.POST.name()))
				.addFilterBefore(new CustomTokenAuthenticationFilter(userService), BasicAuthenticationFilter.class)
				.addFilterBefore(new CustomExceptionHandlerFilter(exceptionTranslator), CustomTokenAuthenticationFilter.class);

		http.antMatcher("/v1/users/**/favorites")
				.addFilterBefore(new CustomTokenAuthenticationFilter(userService), BasicAuthenticationFilter.class)
				.addFilterBefore(new CustomExceptionHandlerFilter(exceptionTranslator), CustomTokenAuthenticationFilter.class);
	}
}
