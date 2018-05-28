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

package com.citibanamex.api.productname.exceptions;

import com.citibanamex.api.productname.model.base.BaseErrorResponse;
import com.citibanamex.api.productname.util.ProductNameProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * This is a APIFactoryGlobalExceptionHandler class to handle all Exceptions.
 *
 *
 */
@RestControllerAdvice
@EnableWebMvc
public class ApiFactoryGlobalExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ApiFactoryGlobalExceptionHandler.class);

  /**
   * object to have access properties files.
   */
  @Autowired
  private ProductNameProperties productNameProperties;

  /**
   * This method is to handle APIFactoryBadRequestException category.
   *
   * @param ex - Exception that will be thrown.
   * @param webRequest - webRequest object to access request information.
   * @return BaseErrorResponse
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = ApiFactoryBadRequestException.class)
  public ResponseEntity<BaseErrorResponse> handleBadRequestException(
      ApiFactoryBadRequestException ex, WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.BAD_REQUEST);
  }


  /**
   * This method is to handle ApiFactoryExternalAPiException.
   *
   * @param ex - Exception that was thrown.
   * @param webRequest - webRequest object to access request information.
   * @return BaseErrorResponse.
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = ApiFactoryExternalApiException.class)
  public ResponseEntity<BaseErrorResponse> handleExternalApiException(
      ApiFactoryExternalApiException ex, WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);

  }

  /**
   * This method is to handle BaseException category
   *
   * @param ex.
   * @param webRequest - webRequest object to access request information.
   * @return BaseErrorResponse
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = ApiFactoryServerError.class)
  public ResponseEntity<BaseErrorResponse> handleBaseException(ApiFactoryServerError ex,
      WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * This method is to handle APIFactoryNotFoundException category
   * @param webRequest - webRequest object to access request information.
   * @param ex - Exception that was thrown.
   * @return BaseErrorResponse
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = ApiFactoryDataNotFoundException.class)
  public ResponseEntity<BaseErrorResponse> handleDataNotFoundException(
      ApiFactoryDataNotFoundException ex, WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(true));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * This method is to handle APIFactoryUnauthorizedException category
   * @param webRequest - webRequest object to access request information.
   * @param ex - Exception that was thrown.
   * @return BaseErrorResponse.
   */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(value = ApiFactoryUnauthorizedException.class)
  public ResponseEntity<BaseErrorResponse> handleHttpHeaderException(
      ApiFactoryUnauthorizedException ex, WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * This method is to handle ApiFactoryFallBackException category
   * @param webRequest - webRequest object to access request information.
   * @param ex - Exception that was thrown.
   * @return BaseErrorResponse
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = ApiFactoryFallBackException.class)
  public ResponseEntity<BaseErrorResponse> handleFallBackException(ApiFactoryFallBackException ex,
      WebRequest webRequest) {

    LOG.error("*************************" + ex);
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * This exception is to handle ProductNameException category.
   * @param ex - Exception that was thrown.
   * @param webRequest - webRequest object to access request information.
   * @return BaseErrorResponse.
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = ProductNameException.class)
  public ResponseEntity<BaseErrorResponse> handleInvalidBinException(ProductNameException ex,
      WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = setResponse(ex, webRequest.getDescription(false));
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.BAD_REQUEST);
  }

  /**
   * This method is to handle APIFactoryNotFoundException category.
   * @param webRequest - webRequest object to access request information.
   * @param ex - Exception that was thrown.
   * @return BaseErrorResponse
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public ResponseEntity<BaseErrorResponse> handleResourceNotFoundException(
      final NoHandlerFoundException ex, WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = new BaseErrorResponse();
    baseErrorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND));
    baseErrorResponse.setDetails(productNameProperties.getMessageResourceNotFoundDescription());
    baseErrorResponse.setLocation(webRequest.getDescription(false));
    baseErrorResponse.setMoreInfo(productNameProperties.getMessageResourceNotFoundDescription());
    baseErrorResponse.setType(productNameProperties.getMessageError());
    LOG.error(productNameProperties.getMessageHttpResponse()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getCode()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getDetails()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getLocation());
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * This method is to handle ServletRequestBindingException category.
   * @param exception - Exception that was thrown.
   * @param webRequest - webRequest object to access request information.
   * @return BaseErrorResponse.
   */
  @ExceptionHandler(ServletRequestBindingException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<BaseErrorResponse> handleException(ServletRequestBindingException exception,
      WebRequest webRequest) {
    BaseErrorResponse baseErrorResponse = new BaseErrorResponse();
    baseErrorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
    baseErrorResponse.setDetails(exception.getMessage());
    baseErrorResponse.setLocation(webRequest.getDescription(false));
    baseErrorResponse.setMoreInfo(exception.getMessage());
    baseErrorResponse.setType(productNameProperties.getMessageError());
    LOG.error(productNameProperties.getMessageHttpResponse()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getCode()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getDetails()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getLocation());
    return new ResponseEntity<BaseErrorResponse>(baseErrorResponse, HttpStatus.BAD_REQUEST);
  }


  /**
   * This method set a BaseErrorResponse object with error information.
   * @param ex - Exception that was thrown.
   * @return BaseErrorResponse
   */
  private BaseErrorResponse setResponse(ApiFactoryBaseException ex, String location) {
    BaseErrorResponse baseErrorResponse = new BaseErrorResponse();
    baseErrorResponse.setCode(ex.getCode());
    baseErrorResponse.setDetails(ex.getDetails());
    if (!StringUtils.isEmpty(location)) {
      baseErrorResponse.setLocation(location);
    } else {
      baseErrorResponse.setLocation(ex.getLocation());
    }
    if (!StringUtils.isEmpty(ex.getMoreInfo())) {
      baseErrorResponse.setMoreInfo(ex.getMoreInfo());
    } else {
      baseErrorResponse.setMoreInfo(ex.getDetails());
    }
    baseErrorResponse.setType(ex.getType());
    LOG.error(productNameProperties.getMessageHttpResponse()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getCode()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getDetails()
        + productNameProperties.getMessageHyphen() + baseErrorResponse.getLocation());
    return baseErrorResponse;
  }


}
