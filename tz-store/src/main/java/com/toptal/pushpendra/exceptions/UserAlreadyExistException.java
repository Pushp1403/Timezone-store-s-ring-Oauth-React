package com.toptal.pushpendra.exceptions;

public class UserAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = 7948351045442287659L;

	public UserAlreadyExistException() {
		super();
	}

	public UserAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistException(String message) {
		super(message);
	}

	public UserAlreadyExistException(Throwable cause) {
		super(cause);
	}
	
	

}
