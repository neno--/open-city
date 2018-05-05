package com.github.nenomm.oc.rest;

public class ErrorDTO {

	private String error;

	private String message;

	public ErrorDTO(Exception e) {
		this.error = e.getClass().getName();
		this.message = e.getMessage();
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
}
