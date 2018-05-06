package com.github.nenomm.oc.token;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TokenDTO {

	private String token;

	@JsonCreator
	public TokenDTO() {
	}

	private TokenDTO(Token token) {
		this.token = token.getToken();
	}

	public String getToken() {
		return token;
	}

	public static TokenDTO fromToken(Token token) {
		return new TokenDTO(token);
	}
}
