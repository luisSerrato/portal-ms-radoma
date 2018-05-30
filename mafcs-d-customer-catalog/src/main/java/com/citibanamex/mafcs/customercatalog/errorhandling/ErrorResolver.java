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

package com.citibanamex.mafcs.customercatalog.errorhandling;

import com.citibanamex.itmt.ccutil.constants.ErrorCatalog;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.BadRequestFeignException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.DataNotFoundException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.FilterFormatException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorResolver {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorResolver.class);

  /**
   * To handle Exception.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */ 
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse resolveException(HttpServletRequest req, Exception ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.EXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());

    return errorResponse;
  }


  /**
   * To handle NumberFormatException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */ 
  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse resolveNumberFormatException(HttpServletRequest req, Exception ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.EXCEPTION_ERROR_CODE);
    errorResponse.setDetails("FormatException");

    return errorResponse;
  }

  /**
   * To handle ValidationException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */ 
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse resolveValidationException(HttpServletRequest req, Exception ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.VALIDATIONEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());

    return errorResponse;
  }

  /**
   * To handle BadRequestFeignException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(BadRequestFeignException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse resolveBadRequestFeignException(HttpServletRequest req, Exception ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.BADREQUESTFEIGNEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());

    return errorResponse;
  }

  /**
   * To handle HttpMessageNotReadableException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse resolveHttpMessageNotReadableException(HttpServletRequest req,
      HttpMessageNotReadableException ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.INVALID.name());
    errorResponse.setCode(Constants.HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());

    return errorResponse;
  }

  /**
   * To handle MethodArgumentNotValidException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse resolveMethodArgumentNotValidException(HttpServletRequest req,
      MethodArgumentNotValidException ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.INVALID.name());
    errorResponse.setCode(Constants.HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());
    List<String> fields = new ArrayList<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    for (FieldError fieldError : fieldErrors) {
      fields.add(fieldError.getField());
    }
    errorResponse.setLocation(fields.toString());

    return errorResponse;
  }

  /**
   * To handle DataNotFoundException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(DataNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse resolveDataNotFoundExceptionException(HttpServletRequest req,
      DataNotFoundException ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.DATANOTFOUNDEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ErrorCatalog.GENERIC_FAILURE_DESC);
    errorResponse.setLocation(Constants.MSGWITHOUTCOMMENTS);
    errorResponse.setMoreInfo(ex.getMessage());

    return errorResponse;
  }

  /**
   * To handle CcC080CustomerClientException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(CcC080CustomerClientException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse resolveCcC080AddressClientException(HttpServletRequest req, Exception ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.CCC080CUSTOMERCLIENTEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());

    return errorResponse;
  }
  
  /**
   * To handle FilterFormatException.
   * @param req.
   * @param ex.
   * @return ErrorResponse.
   */
  @ExceptionHandler(FilterFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse resolverFilterFormatException(HttpServletRequest req,
      FilterFormatException ex) {

    LOG.error(ex.getMessage(), ex);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setType(ErrorType.ERROR.name());
    errorResponse.setCode(Constants.VALIDATIONEXCEPTION_ERROR_CODE);
    errorResponse.setDetails(ex.getMessage());
    errorResponse.setLocation(ex.getVarName());
    errorResponse.setMoreInfo("May be [3-12] letters of the word to search");

    return errorResponse;
  }
}
