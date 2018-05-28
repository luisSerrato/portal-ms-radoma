package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 5055749614660328043L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable throwable) {
		super(throwable);
	}
}
