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

package com.citibanamex.api.productname.model.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Error class that will contain error request information.
 * @author
 *
 */

@Getter
@Setter
public class BaseErrorResponse {
  private String type;
  private String code;
  private String details;
  private String location;
  private String moreInfo;

  /**
   * Constructor with arguments.
   * @param type - type of the error.
   * @param code - code of the error.
   * @param details - details of the error.
   * @param location - error variable.
   * @param moreInfo - more info about the error.
   */
  public BaseErrorResponse(String code, String details, String location, String moreInfo,
      String type) {
    this.code = code;
    this.details = details;
    this.location = location;
    this.moreInfo = moreInfo;
    this.type = type;
  }

  /**
   * Default constructor.
   */
  public BaseErrorResponse() {}

}
