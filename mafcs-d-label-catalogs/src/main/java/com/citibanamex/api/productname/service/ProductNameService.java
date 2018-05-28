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

package com.citibanamex.api.productname.service;

import com.citibanamex.api.productname.exceptions.ApiFactoryFallBackException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;



/**
 * This interface defines methods to be implemented by ProductNameServiceImpl.
 *
 * @author
 *
 */
public interface ProductNameService {

  /**
   * This method gets a product name.
   *
   * @param inputBin - Bank Identification Number parameter
   * @return String
   * @throws IOException - This exception will be thrown if something is wrong when a file is read.
   * @throws ResourceAccessException - This exception will be thrown if call to external service
   *         fails.
   * @throws ApiFactoryFallBackException - APIFactoryFallBackException
   */
  String retrieveProductName(String inputBin)
      throws ApiFactoryFallBackException;


}
