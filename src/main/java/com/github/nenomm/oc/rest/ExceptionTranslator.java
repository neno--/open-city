package com.github.nenomm.oc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO processMethodArgumentNotValidException(MethodArgumentNotValidException e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDTO processHttpMessageNotReadableException(HttpMessageNotReadableException e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorDTO processNoSuchElementException(NoSuchElementException e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorDTO processDataIntegrityViolationException(DataIntegrityViolationException e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorDTO processAccessDeniedException(AccessDeniedException e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.FORBIDDEN);
	}

	// default handler
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDTO processException(Exception e) {

		logger.error(e.getMessage(), e);

		return new ErrorDTO(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
