package com.github.nenomm.oc.config;

import com.github.nenomm.oc.security.CustomAuthenticationProvider;
import com.github.nenomm.oc.security.CustomTokenAuthenticationFilter;
import com.github.nenomm.oc.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	private UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable().authorizeRequests().anyRequest().permitAll();

		//http.addFilterBefore(new CustomTokenAuthenticationFilter(), BasicAuthenticationFilter.class);

		// @formatter:off
		http.exceptionHandling().and()
				.csrf().disable().headers().frameOptions().disable().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/v1/cities/**").permitAll()
				//.antMatchers("/v1/users/**/favorites").authenticated()
				.antMatchers("/v1/token").permitAll()
				.antMatchers("/v1/users").permitAll();
		// @formatter:on

		http.antMatcher("/v1/users/**/favorites").authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(new CustomTokenAuthenticationFilter(userService), BasicAuthenticationFilter.class);
	}
}
