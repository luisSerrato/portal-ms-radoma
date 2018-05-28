package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

public class FilterFormatException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6304743683507356758L;
	private String varName;

	public FilterFormatException(){}
	
	public FilterFormatException(String message){
		super(message);
	}
	
	public FilterFormatException(String message, String varName){
		super(message);
		this.varName = varName;
	}
	
	public FilterFormatException(Throwable e){
		super(e);
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
