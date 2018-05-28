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

package com.citibanamex.api.productname.service.impl;

import com.citibanamex.api.productname.exceptions.ApiFactoryFallBackException;
import com.citibanamex.api.productname.model.BinInfo;
import com.citibanamex.api.productname.service.ProductNameService;
import com.citibanamex.api.productname.util.CatalogDataUtil;
import com.citibanamex.api.productname.util.ExternalApiUtil;
import com.citibanamex.api.productname.util.ToolsUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;



/**
 * this class implements methods which get a product name.
 *
 * @author
 *
 */
@Service
public class ProductNameServiceImpl implements ProductNameService {

  /**
   * CatalogDataUtil object to access methods.
   */
  @Autowired
  CatalogDataUtil catalogDataUtil;

  /**
   * ExternalAPIUtil to have access methods that gets Product information from a file.
   */
  @Autowired
  ExternalApiUtil externalApiUtil;

  /**
   * Logger declaration.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameServiceImpl.class);



  /**
   * This method retrieves a product name.
   *
   * @param inputBin - Bank Identification Number
   * @return String
   * @throws IOException - Exception that can be thrown if a problem occurs when the file is read
   * @throws ResourceAccessException - This exception will be thrown if call to external service
   *         fails
   * @throws ApiFactoryFallBackException - APIFactoryFallBackException
   */
  @HystrixCommand(fallbackMethod = "fallbackRetrieveProductName")
  public String retrieveProductName(String inputBin)
      throws ApiFactoryFallBackException {

    LOG.info("Bank Identification Number input parameter for looking for bankInfo: {}", inputBin);
    BinInfo binInfo = null;
    String bankInfo = null;
    String productName = null;

    try {
      binInfo = externalApiUtil.getBinInfo(inputBin);
      LOG.debug("bin Information {}", ToolsUtil.getObjectAsJson(binInfo));
    } catch (Exception ex) {
      /* LOGS created by Abraham Hernandez, RADOMA */
      LOG.error("There was a problem calling the external Api");
      LOG.error("message: {}, cause: {}, trace: {}", ex.getMessage(), ex.getCause(),
          ToolsUtil.getStackTrace(ex));
      throw ex;
    }

    if (null != binInfo && null != binInfo.getProductNo() && null != binInfo.getInstrumentNo()) {
      bankInfo = catalogDataUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo());
    }

    /* LOG created by Abraham Hernandez for RADOMA */
    LOG.info("Product Information: {}", bankInfo);

    if (null != bankInfo && !bankInfo.isEmpty()) {
      productName = catalogDataUtil.getProductName(bankInfo);
    }

    if (null != productName) {
      LOG.info("productName recovered {}", productName);
      return productName.trim();

    }

    productName = "";

    return productName;
  }

  /**
   * This method will be executed if a problem is present in method above.
   *
   * @param inputBin - Bank Identification Number parameter
   * @return String
   * @throws IOException - Exception that can be thrown if a problem occurs when the file is read
   * @throws ResourceAccessException - This exception will be thrown if call to external service
   *         fails
   * @throws ApiFactoryFallBackException - APIFactoryFallBackException
   */
  public String fallbackRetrieveProductName(String inputBin)
      throws ApiFactoryFallBackException {

    LOG.error("executing fallbackRetrieveProductName");
    LOG.error("An APIFactoryFallBackException exception will be thrown");
    throw new ApiFactoryFallBackException();
  }



}
