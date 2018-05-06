package com.github.nenomm.oc.config;

import com.github.nenomm.oc.OpenCityApp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = OpenCityApp.APP_PREFIX)
public class AppProperties {

	private Token token = new Token();

	public Token getToken() {
		return token;
	}

	public static class Token {

		private int lifetime = 60 * 60 * 24; // default validity is 24 hrs

		public int getLifetime() {
			return lifetime;
		}

		public void setLifetime(int lifetime) {
			this.lifetime = lifetime;
		}
	}
}
