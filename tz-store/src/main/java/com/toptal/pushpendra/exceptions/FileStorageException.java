package com.toptal.pushpendra.exceptions;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 8414843984713058459L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
