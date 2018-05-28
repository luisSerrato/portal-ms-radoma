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

import lombok.Getter;


/**
 * main Exception which all the exception in microservice inherit.
 * @author
 *
 */

public class ApiFactoryBaseException extends Exception {

  /**
   * serialVersion.
   */
  @Getter
  private static final long serialVersionUID = 1L;

  /**
   * type of the error.
   */
  @Getter
  private final String type;

  /**
   * code of the error.
   */
  @Getter
  private final String code;

  /**
   * details of the error.
   */
  @Getter
  private final String details;

  /**
   * error variable.
   */
  @Getter
  private final String location;

  /**
   * more info about of the error.
   */
  @Getter
  private final String moreInfo;

  /**
   * default constructor.
   */
  public ApiFactoryBaseException() {
    super();
    type = null;
    details = null;
    location = null;
    code = null;
    moreInfo = null;

  }

  /**
   * Constructor with arguments.
   * @param message - message of the exception.
   * @param cause - cause of the exception.
   * @param enableSuppression - Suppression enabled.
   * @param writableStackTrace - writableStackTrace.
   */
  public ApiFactoryBaseException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    type = null;
    details = null;
    location = null;
    code = null;
    moreInfo = null;


  }

  /**
   * Constructor with arguments.
   * @param message - message of the exception.
   * @param cause - cause of the exception.
   */
  public ApiFactoryBaseException(String message, Throwable cause) {
    super(message, cause);
    type = null;
    details = null;
    location = null;
    code = null;
    moreInfo = null;

  }

  /**
   * constructor with arguments.
   * @param message - message of the exception.
   */
  public ApiFactoryBaseException(String message) {
    super(message);
    type = null;
    details = null;
    location = null;
    code = null;
    moreInfo = null;

  }

  /**
   *
   * @param cause - cause of the exception.
   */
  public ApiFactoryBaseException(Throwable cause) {
    super(cause);
    type = null;
    details = null;
    location = null;
    code = null;
    moreInfo = null;

  }

  /**
   * Constructor with arguments.
   * @param type - type of the error.
   * @param code - code of the error.
   * @param details - details of the error.
   * @param location - error variable.
   * @param moreInfo - more info about the error.
   */
  public ApiFactoryBaseException(String type, String code, String details, String location,
      String moreInfo) {
    this.type = type;
    this.code = code;
    this.details = details;
    this.location = location;
    this.moreInfo = moreInfo;
  }

}
