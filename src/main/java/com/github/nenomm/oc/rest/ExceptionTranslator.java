package com.github.nenomm.oc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionTranslator {

	private Logger logger = LoggerFactory.getLogger(ExceptionTranslator.class);

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO processMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		logger.error("Error:", e);
		return new ErrorDTO(e);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorDTO processNoSuchElementException(NoSuchElementException e) {
		logger.error("Error:", e);
		return new ErrorDTO(e);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorDTO processDataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.error("Error:", e);
		return new ErrorDTO(e);
	}

	// default handler
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO processException(Exception e) {
		logger.error("Error:", e);
		return new ErrorDTO(e);
	}


}