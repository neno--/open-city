package com.github.nenomm.oc.token;

public class TokenDTO {

	private String token;

	public TokenDTO(Token token) {
		this.token = token.getToken();
	}

	public String getToken() {
		return token;
	}

	public static TokenDTO fromToken(Token token) {
		return new TokenDTO(token);
	}
}
