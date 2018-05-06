package com.github.nenomm.oc.config;

import com.github.nenomm.oc.rest.ExceptionTranslator;
import com.github.nenomm.oc.security.CustomAuthenticationProvider;
import com.github.nenomm.oc.security.CustomExceptionHandlerFilter;
import com.github.nenomm.oc.security.CustomTokenAuthenticationFilter;
import com.github.nenomm.oc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Order(1)
public class OtherRoutesSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling().and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/v1/token").permitAll()
				.antMatchers("/v1/users").permitAll()
				.antMatchers("/v1/users/**/favorites").authenticated();

		http.antMatcher("/v1/users/**/favorites")
				.addFilterBefore(new CustomTokenAuthenticationFilter(userService), BasicAuthenticationFilter.class)
				.addFilterBefore(new CustomExceptionHandlerFilter(exceptionTranslator), CustomTokenAuthenticationFilter.class);
	}
}
