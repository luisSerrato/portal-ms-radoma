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

package com.citibanamex.api.productname.validation;


import com.citibanamex.api.productname.exceptions.ApiFactoryBadRequestException;
import com.citibanamex.api.productname.exceptions.ApiFactoryBaseException;
import com.citibanamex.api.productname.model.RetrieveProductRequest;
import com.citibanamex.api.productname.util.ProductNameProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;


/**
 *
 * @author this class implements methods that validates headers.
 *
 */

public class ProductNameValidator {


  /**
   * Logger declaration.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameValidator.class);

  /**
   * private constructor.
   */
  private ProductNameValidator() {}

  /**
   * This method validates that a String is a number.
   *
   * @param inputParam - String which is going to be validated.
   * @return boolean.
   */
  public static boolean isNumeric(final String inputParam) {
    boolean result = false;
    Pattern pattern = Pattern.compile("[0-9]+");
    result = pattern.matcher(inputParam).matches();
    return result;
  }

  /**
   * This method validates if bin has the correct length.
   *
   * @param bin - input parameter (Bank Identification Number)
   * @return boolean
   */
  public static boolean isValidBinLength(final String bin) {

    return bin.length() >= 6 && bin.length() <= 9;

  }

  /**
   * This method validates headers.
   *
   * @param productRequest - object which contains header.
   * @return boolean
   * @throws ApiFactoryBaseException - This is an exception that will be thrown if headers are
   *         missing or they have a wrong format.
   */
  public static boolean isValidHeader(final RetrieveProductRequest productRequest,
      final ProductNameProperties productNameProperties) throws ApiFactoryBaseException {

    /*
     * log by
     *
     * @author abraham.hernandez RADOMA
     */
    LOG.info("validating headers...");
    boolean result = false;
    result = isValidUuidHeader(productRequest.getUuid(), productNameProperties);
    result = isValidAcceptLanguageHeader(productRequest.getAcceptLanguage(), productNameProperties);
    result = isValidAcceptHeader(productRequest.getAccept(), productNameProperties);
    /*
     * log by
     *
     * @author abraham.hernandez RADOMA
     */
    LOG.info("Headers validated successfully...");

    return result;
  }

  /**
   * This method validates uuid header.
   *
   * @param uuid - String uuid which is going to be validated.
   * @return boolean
   * @throws ApiFactoryBadRequestException - This exception will be thrown if uuid header is missing
   *         or it has a wrong format.
   */
  public static boolean isValidUuidHeader(final String uuid,
      final ProductNameProperties productNameProperties) throws ApiFactoryBadRequestException {

    if (StringUtils.isEmpty(uuid)) {
      /*
       * log by
       *
       * @author abraham.hernandez RADOMA
       */
      logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNameProperties.getMessageUuidDescription(), productNameProperties.getMessageUuid(),
          productNameProperties.getMessageEmptyString());

      badRequestException(productNameProperties.getMessageUuidDescription(),
          productNameProperties.getMessageUuid(), productNameProperties.getMessageEmptyString(),
          productNameProperties);
    }
    return true;
  }

  /**
   * this method validates Accept header.
   *
   * @param accept - String accept which is going to be validated.
   * @return boolean
   * @throws ApiFactoryBadRequestException - This exception will be thrown if accept header is
   *         missing or it has a wrong format.
   */
  public static boolean isValidAcceptHeader(final String accept,
      final ProductNameProperties productNameProperties) throws ApiFactoryBadRequestException {

    if (StringUtils.isEmpty(accept)) {
      /*
       * log by
       *
       * @author abraham.hernandez RADOMA
       */
      logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNameProperties.getMessageAcceptDescription(),
          productNameProperties.getMessageAccept(), productNameProperties.getMessageEmptyString());

      badRequestException(productNameProperties.getMessageAcceptDescription(),
          productNameProperties.getMessageAccept(), productNameProperties.getMessageEmptyString(),
          productNameProperties);
    }
    return true;
  }

  /**
   * this method validates Accept Language header.
   *
   * @param acceptLanguage - String accpetLanguage which is going to be validated.
   * @return boolean
   * @throws ApiFactoryBadRequestException - This exception will be thrown if authorization header
   *         is missing or it has a wrong format.
   */
  public static boolean isValidAcceptLanguageHeader(final String acceptLanguage,
      final ProductNameProperties productNameProperties) throws ApiFactoryBadRequestException {

    if (StringUtils.isEmpty(acceptLanguage)) {
      /*
       * log by
       *
       * @author abraham.hernandez RADOMA
       */
      logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNameProperties.getMessageAcceptLanguageDescription(),
          productNameProperties.getMessageAcceptLanguage(),
          productNameProperties.getMessageEmptyString());


      badRequestException(productNameProperties.getMessageAcceptLanguageDescription(),
          productNameProperties.getMessageAcceptLanguage(),
          productNameProperties.getMessageEmptyString(), productNameProperties);
    }
    return true;
  }

  /**
   * this method throws APIFactoryBadRequestException.
   *
   * @param details - details
   * @param location - location
   * @param moreInfo - more Information about the problem.
   * @throws ApiFactoryBadRequestException - This exception will be thrown in this method
   */
  private static void badRequestException(final String details, final String location,
      final String moreInfo, final ProductNameProperties productNameProperties)
      throws ApiFactoryBadRequestException {
    LOG.error("*****APIFactoryUnauthorizedException*****");
    throw new ApiFactoryBadRequestException(productNameProperties.getResponseMessageError(),
        HttpStatus.BAD_REQUEST.toString(), details, location, moreInfo);
  }

  /**
   * Logs when there was a bad request and the exception was thrown.
   *
   * @param error error string
   * @param details details of the exception
   * @param location location of the exception
   * @param moreInfo additional info
   */
  public static void logBadRequestException(final String error, final String details,
      final String location, final String moreInfo) {
    LOG.error("BadRequestException thrown: error {} details {} location {} moreInfo {} ", error,
        details, location, moreInfo);
  }

}
