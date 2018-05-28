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

import com.citibanamex.api.productname.controller.ProductNameController;
import com.citibanamex.api.productname.model.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;



/**
 * This class implements a methods to gets Product information from a file.
 *
 * @author
 *
 */
@Component
public class CatalogDataUtil {

  /**
   * logger declaration.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameController.class);


  /**
   * productFile object which contains a list with an excel file information.
   */
  @Autowired
  private List<ProductEntity> products;

  /**
   * this method gets product information from a List which is gotten from a excel file.
   *
   * @param inputProductNo - Product No. parameter
   * @param inputInstrumentNo - Instrument No. parameter
   * @return String
   * @throws IOException - This exception will be thrown if a problem occurs while it is reading a
   *         file
   */
  public String getProductInfo(final String inputProductNo, final String inputInstrumentNo) {

    String productInfo = "";
    LOG.info("In CatalogDataUtil : getProductInfo()");
    /* Log created by Abraham Hernandez for RADOMA */
    LOG.debug(" Input productNo : {}", inputProductNo);
    /* Log created by Abraham Hernandez for RADOMA */
    LOG.debug("Input instrumentNo : {}", inputInstrumentNo);

    LOG.info("Searching for productInfo...");
    if (products != null && !products.isEmpty()) {
      for (ProductEntity product : products) {
        if (product.getProductNo().equals(inputProductNo)
            && product.getInstrumentNo().equals(inputInstrumentNo)) {
          LOG.info("Found matching for inputProductNo: {} and inputInstrumentNo: {}",
              inputProductNo, inputInstrumentNo);
          productInfo = product.getProductInfo();
          break;
        }
      }
    }

    if ("".equals(productInfo)) {
      LOG.info("Matching was not found for inputProductNo: {} and inputInstrumentNo: {}",
          inputProductNo, inputInstrumentNo);
    }
    /* Log created by Abraham Hernandez for RADOMA */
    LOG.info("PrductInfo search finished");

    LOG.info("End CatalogDataUtil : getProductInfo()");

    return productInfo;

  }

  /**
   * This method gets a part of a String.
   *
   * @param inputProductName - Product name parameter
   * @return String
   */
  public String getProductName(final String inputProductName) {

    LOG.info("Getting product name");
    int cntr = 0;
    String productName = null;
    StringTokenizer productNameTokenizer = new StringTokenizer(inputProductName, "|");
    while (productNameTokenizer.hasMoreElements()) {
      productName = (String) productNameTokenizer.nextElement();
      ++cntr;
      if (cntr == 3) {
        LOG.debug(productName);
        break;
      }
    }
    return productName;

  }
}
