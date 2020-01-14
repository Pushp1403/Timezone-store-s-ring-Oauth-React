package com.toptal.pushpendra.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailVerificationMissingException extends AuthenticationException {

	private static final long serialVersionUID = 3217501400283085600L;

	public EmailVerificationMissingException(String msg) {
		super(msg);
	}

}
