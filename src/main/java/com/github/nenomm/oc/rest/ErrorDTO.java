package com.github.nenomm.oc.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class ErrorDTO {

	private String error;

	private String message;

	private HttpStatus httpStatus;

	public ErrorDTO(Exception e, HttpStatus httpStatus) {
		this.error = e.getClass().getName();
		this.message = e.getMessage();
		this.httpStatus = httpStatus;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	@JsonIgnore
	public int getHttpStatusCode() {
		return httpStatus.value();
	}
}
