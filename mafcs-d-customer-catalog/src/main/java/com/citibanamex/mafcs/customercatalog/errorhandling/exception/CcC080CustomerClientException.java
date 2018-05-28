package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

public class CcC080CustomerClientException extends RuntimeException {

	private static final long serialVersionUID = -2783727869968231370L;

	public CcC080CustomerClientException() {
		 // Do nothing because is required
	}

	public CcC080CustomerClientException(String message) {
		super(message);
	}

	public CcC080CustomerClientException(Throwable throwable) {
		super(throwable);
	}

}
