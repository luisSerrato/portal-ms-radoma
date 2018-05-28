package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

public class DataNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 8913276433744329025L;
	
	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(Throwable throwable) {
		super(throwable);
	}
	
	

}
