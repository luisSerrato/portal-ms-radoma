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

import com.citibanamex.api.productname.controller.ProductNameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;



/**
 * This class Implements ResponseErrorHandler.
 *
 * @author abraham.hernandez
 *
 */
public class RestServiceErrorHandler implements ResponseErrorHandler {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameController.class);

  /**
   * Checks if there is an error in external API response.
   *
   * @param response - External API response
   * @throws IOException - will be thrown if something is wrong
   */
  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {


    return response.getStatusCode() != HttpStatus.OK ? true : false;

  }

  /**
   * this method is executed if method above return true.
   *
   * @param response - External API response.
   * @throws IOException - will be thrown if something is wrong
   */
  @Override
  public void handleError(ClientHttpResponse response) throws IOException {

    LOG.error("There was an error calling external API, Error: {}", response.getRawStatusCode());
    throw new RestClientException("");

  }

}
