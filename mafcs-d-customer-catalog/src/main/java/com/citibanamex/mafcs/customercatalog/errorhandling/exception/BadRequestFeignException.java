/*
 * Copyright (C) 2018 by Citigroup. All rights reserved. Citigroup claims copyright in this computer
 * program as an unpublished work, one or more versions of which were first used to provide services
 * to customers on the dates indicated in the foregoing notice. Claim of copyright does not imply
 * waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of Citigroup. Use, examination,
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of all or any part
 * of this software program are strictly prohibited except by express written agreement with
 * Citigroup.
 */
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