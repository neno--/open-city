package com.github.nenomm.oc.rest;

public class ErrorDTO {

	private String message;

	private String description;

	public ErrorDTO(Exception e) {
		this.message = e.getClass().getName();
		this.description = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}
