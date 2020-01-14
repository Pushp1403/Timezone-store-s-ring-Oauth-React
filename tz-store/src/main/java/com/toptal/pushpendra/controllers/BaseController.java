package com.toptal.pushpendra.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {

	private final Logger logger = LoggerFactory.getLogger(BaseController.class);

	public <T> ResponseEntity<T> buildSuccessResponse(T body) {
		logger.info("Request with URL '{}' processed successfully",
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
						.getRequestURI());
		return new ResponseEntity<T>(body, HttpStatus.OK);
	}

	public <T> ResponseEntity<T> buildErrorResponse(T body, HttpStatus ststus) {
		return new ResponseEntity<T>(body, ststus);
	}

}
