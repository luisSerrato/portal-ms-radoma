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

package com.citibanamex.api.productname.util;

import com.citibanamex.api.productname.model.BinInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 * This class implements a method that get a bin information from an external API.
 *
 * @author
 *
 */
@Component
public class ExternalApiUtil {

  /**
   * logger declaration.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ExternalApiUtil.class);

  /**
   * productNamesProperties object to have access to properties files.
   */
  @Autowired
  private ProductNameProperties productNamesProperties;

  /**
   * productNameService object to have access methods that get a product name.
   */
  @Autowired
  private RestTemplate restTemplate;

  /**
   * This method gets a bin Information from an external API.
   *
   * @param inputBin - Bank Identification Number parameter.
   * @return BinInfo
   */
  public BinInfo getBinInfo(final String inputBin) {

    ResponseEntity<BinInfo> serviceResponse = null;
    BinInfo binInfo = null;
    HttpHeaders headers = new HttpHeaders();
    headers.set("authorization", "authorization");
    headers.set("client_id", "client_id");
    headers.set("uuid", "uuid");
    headers.set("accept-language", "accept-language");
    headers.setContentType(MediaType.APPLICATION_JSON);
    String externalApiUrl = null;

    externalApiUrl = productNamesProperties.getExternalHttpPrefix()
        + productNamesProperties.getExternalApiServername()
        + productNamesProperties.getExternalApiUrl() + inputBin;
    /* LOG, created by Abraham Hernandez, RADOMA */
    LOG.info("Getting BinInfo from external API with Url: {}", externalApiUrl);
    /* LOG, created by Abraham Hernandez, RADOMA */
    LOG.debug("Input for external API: {}", inputBin);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    try {
      serviceResponse =
          restTemplate.exchange(externalApiUrl, HttpMethod.GET, entity, BinInfo.class);
    } catch (RestClientException exception) {
      /* LOG, created by Abraham Hernandez, RADOMA */
      LOG.error("Error connecting to external API: {}, message: {}, cause: {} trace: {}",
          externalApiUrl, exception.getMessage(), exception.getCause(),
          ToolsUtil.getStackTrace(exception));
      throw exception;
    }

    if (null != serviceResponse && null != serviceResponse.getBody()
        && null != serviceResponse.getBody().getProductNo()) {
      /* LOG, created by Abraham Hernandez, RADOMA */
      LOG.debug("response from external API: {}",
          ToolsUtil.getObjectAsJson(serviceResponse.getBody()));

      /* LOG, created by Abraham Hernandez, RADOMA */
      LOG.info("external API call was executed successfully");
      binInfo = new BinInfo();
      binInfo.setBin(serviceResponse.getBody().getBin());
      binInfo.setProductNo(serviceResponse.getBody().getProductNo());
      binInfo.setInstrumentNo(serviceResponse.getBody().getInstrumentNo());
    }

    return binInfo;
  }

}
