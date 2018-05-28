package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

import org.springframework.http.HttpHeaders;

import com.netflix.hystrix.exception.HystrixBadRequestException;


public class BadRequestFeignException extends HystrixBadRequestException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2302612502922257686L;
	private final int status;
    private final HttpHeaders headers;
    private final String message;
    private final Throwable cause;

    public BadRequestFeignException(int status, HttpHeaders headers, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.headers = headers;
        this.message = message;
        this.cause = cause;
    }

	public int getStatus() {
		return status;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}
	@Override
	public String getMessage() {
		return message;
	}
	@Override
	public Throwable getCause() {
		return cause;
	}
   
}