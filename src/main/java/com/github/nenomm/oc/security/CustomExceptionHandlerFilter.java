package com.github.nenomm.oc.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenomm.oc.rest.ErrorDTO;
import com.github.nenomm.oc.rest.ExceptionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class CustomExceptionHandlerFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(CustomExceptionHandlerFilter.class);

	private ExceptionTranslator exceptionTranslator;

	public CustomExceptionHandlerFilter(ExceptionTranslator exceptionTranslator) {
		this.exceptionTranslator = exceptionTranslator;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (AccessDeniedException e) {
			sendErrorReponse(exceptionTranslator.processAccessDeniedException(e), response);
		} catch (Exception e) {
			sendErrorReponse(exceptionTranslator.processException(e), response);
		}
	}

	private void sendErrorReponse(ErrorDTO errorDTO, HttpServletResponse response) throws IOException {

		logger.info("sending back exception message");

		response.setStatus(errorDTO.getHttpStatusCode());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

		// pass down the actual obj that exception handler normally send
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();

		out.print(mapper.writeValueAsString(errorDTO));
		out.flush();
	}
}