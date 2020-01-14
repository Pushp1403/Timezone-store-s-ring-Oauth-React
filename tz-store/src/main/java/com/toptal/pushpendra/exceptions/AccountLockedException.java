package com.toptal.pushpendra.exceptions;

public class AccountLockedException extends RuntimeException {

	private static final long serialVersionUID = 3941159754778749291L;

	public AccountLockedException() {
		super();
	}

	public AccountLockedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountLockedException(String message) {
		super(message);
	}

	public AccountLockedException(Throwable cause) {
		super(cause);
	}
	
	

}
