package com.shoponline.exception;

public class CredentialValidationException extends Exception{

	private String exceptionMessage;

	public CredentialValidationException(String message) {
		this.exceptionMessage = message;
	}

	@Override
	public String getMessage() {
		return this.exceptionMessage;
	}
}
