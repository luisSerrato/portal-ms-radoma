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

package com.citibanamex.api.productname.controller;

import com.citibanamex.api.productname.exceptions.ApiFactoryBaseException;
import com.citibanamex.api.productname.exceptions.ApiFactoryExternalApiException;
import com.citibanamex.api.productname.exceptions.ApiFactoryServerError;
import com.citibanamex.api.productname.exceptions.ProductNameException;
import com.citibanamex.api.productname.model.BankAccount;
import com.citibanamex.api.productname.model.RetrieveProductRequest;
import com.citibanamex.api.productname.model.RetrieveProductResponse;
import com.citibanamex.api.productname.service.ProductNameService;
import com.citibanamex.api.productname.util.ProductNameProperties;
import com.citibanamex.api.productname.util.ToolsUtil;
import com.citibanamex.api.productname.validation.ProductNameValidator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Date;


/**
 * this class is a main controller where request is executed.
 *
 * @author
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ProductNameController {

  /**
   * Logger declaration.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameController.class);

  /**
   * productNameService object to access methods that get product name.
   */
  @Autowired
  ProductNameService productNameService;

  /**
   * productNameProperties object to have access to properties files.
   */
  @Autowired
  ProductNameProperties productNamesProperties;



  /**
   * this method retrieve product name from an excel file.
   *
   * @param authorization - authorization for request
   * @param uuid - uuid request
   * @param accept - accept header
   * @param acceptLanguage - language request accept
   * @param clientId - clientId request
   * @param bankIdentificationNumber - bank Identification number required for the request.
   * @return ResponseEntity
   * @throws ApiFactoryBaseException - This exception will be thrown if there is a problem at
   *         request process.
   */
  @GetMapping(value = "/consumerServices/productServices/labelCatalogs/{bankIdentificationNumber}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RetrieveProductResponse> retrieveProductName(
      @RequestHeader(value = "Authorization", required = false) final String authorization,
      @RequestHeader(value = "uuid") String uuid,
      @RequestHeader(value = "Accept") final String accept,
      @RequestHeader(value = "Accept-Language") final String acceptLanguage,
      @RequestHeader(value = "client_id", required = false) final String clientId,
      @PathVariable(value = "bankIdentificationNumber") final String bankIdentificationNumber)
      throws ApiFactoryBaseException {

    LOG.debug(
        "In ProductNameController..." + new Date().getTime());/* @author Abraham Hernandez RADOMA */
    LOG.info("-----------Headers---------");/* @author Abraham Hernandez RADOMA */
    LOG.info("Authorization: {} , Accept: {} , Accept-Language: {} , client_id: {} ", authorization,
        uuid, accept, acceptLanguage, clientId);/* @author Abraham Hernandez RADOMA */


    String productName = null;
    /*
     * Creation of retrieveProductRequest object due to object was injected with autowired and the
     * class was annotated with component but it is a pojo
     *
     * @author abraham.hernandez
     */
    RetrieveProductRequest retrieveProductRequest = new RetrieveProductRequest();

    retrieveProductRequest.setAuthorization(authorization);
    retrieveProductRequest.setAccept(accept);
    retrieveProductRequest.setUuid(uuid);
    retrieveProductRequest.setAcceptLanguage(acceptLanguage);
    retrieveProductRequest.setClientId(clientId);

    LOG.info("client_id : {}", retrieveProductRequest.getClientId());

    LOG.info("Input BankIdentificationNumber : {}", bankIdentificationNumber);

    ProductNameValidator.isValidHeader(retrieveProductRequest, productNamesProperties);

    if (StringUtils.isEmpty(bankIdentificationNumber)) {
      /* @author Abraham Hernandez RADOMA */
      ProductNameValidator.logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNamesProperties.getMessageBinEmpty(), "", "");
      throw new ProductNameException(productNamesProperties.getResponseMessageError(),
          HttpStatus.BAD_REQUEST.toString(), productNamesProperties.getMessageBinEmpty(), "", "");
    }

    if (!ProductNameValidator.isNumeric(bankIdentificationNumber)) {
      /* @author Abraham Hernandez RADOMA */
      ProductNameValidator.logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNamesProperties.getMessageBinNumeric(), "", "");
      throw new ProductNameException(productNamesProperties.getResponseMessageError(),
          HttpStatus.BAD_REQUEST.toString(), productNamesProperties.getMessageBinNumeric(), "", "");
    }

    if (!ProductNameValidator.isValidBinLength(bankIdentificationNumber)) {
      /* @author Abraham Hernandez RADOMA */
      ProductNameValidator.logBadRequestException(HttpStatus.BAD_REQUEST.toString(),
          productNamesProperties.getMessageBinLength(), "", "");
      throw new ProductNameException(productNamesProperties.getResponseMessageError(),
          HttpStatus.BAD_REQUEST.toString(), productNamesProperties.getMessageBinLength(), "", "");
    }

    try {

      LOG.info("Getting product name from service");/* @author Abraham Hernandez RADOMA */
      LOG.debug("input for service: {}", ToolsUtil
          .getObjectAsJson(bankIdentificationNumber));/* @author Abraham Hernandez RADOMA */
      productName = productNameService.retrieveProductName(bankIdentificationNumber);

    } catch (HttpClientErrorException exception) {
      LOG.error("message: {}, cause: {}, trace: {}", exception.getMessage(), exception.getCause(),
          ToolsUtil.getStackTrace(exception));
      // This is called if product lookup service from parameter catalog
      // microservice does not have any data for bin.
      productName = "";

    } catch (RestClientException exception) {

      LOG.error("*****FallbackError*****");
      LOG.error(
          "There was a problem calling external service, error {} message: {}, "
              + "cause: {}, trace: {}",
          HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage(), exception.getCause(),
          ToolsUtil.getStackTrace(exception));/* @author Abraham Hernandez RADOMA */
      throw new ApiFactoryExternalApiException(productNamesProperties.getResponseMessageError(),
          HttpStatus.INTERNAL_SERVER_ERROR.toString(),
          productNamesProperties.getMessageExternalServerErrorDescription(),
          productNamesProperties.getResponseFallbackErrorDescription(),
          productNamesProperties.getMessageEmptyString());

    } catch (Exception exception) {

      LOG.error("*****APIFactoryServerError*****");
      LOG.error("There was an internal server error error: {}, message: {}, cause: {}, trace: {}",
          HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception.getCause(),
          ToolsUtil.getStackTrace(exception));/* @author Abraham Hernandez RADOMA */
      throw new ApiFactoryServerError(productNamesProperties.getResponseMessageError(),
          HttpStatus.INTERNAL_SERVER_ERROR.toString(),
          productNamesProperties.getMessageInternalServerError(),
          productNamesProperties.getMessageInternalServerErrorDescription(),
          productNamesProperties.getMessageEmptyString());
    }

    if ("".equals(productName)) {
      LOG.error("{}, error: {}", productNamesProperties.getMessageInvalidBin(),
          HttpStatus.BAD_REQUEST.toString());/* @author Abraham Hernandez RADOMA */
      LOG.error("ProductNameException will be thrown due to productName is empty...");
      throw new ProductNameException(productNamesProperties.getResponseMessageError(),
          HttpStatus.BAD_REQUEST.toString(), productNamesProperties.getMessageInvalidBin(), "", "");
    }

    LOG.info("productName recovered");
    LOG.debug("Output from service {}", productName);
    BankAccount bankAccount = new BankAccount();
    bankAccount.setProductName(productName);
    RetrieveProductResponse retrieveProductResponse = new RetrieveProductResponse();
    retrieveProductResponse.setBankAccount(bankAccount);

    LOG.info("retrieveProductName API request was executed successfully");
    LOG.debug("retreiveProductName API Response: {}",
        ToolsUtil.getObjectAsJson(retrieveProductResponse));

    LOG.info("Exiting ProductNameController..." + new Date().getTime());
    return new ResponseEntity<>(retrieveProductResponse, HttpStatus.OK);

  }
}
