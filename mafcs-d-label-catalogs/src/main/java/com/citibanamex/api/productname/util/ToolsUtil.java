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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * this class implements formating methods.
 *
 * @author abraham.hernandez
 *
 */

public class ToolsUtil {


  private static final Logger LOG = LoggerFactory.getLogger(ToolsUtil.class);


  /**
   * private constructor.
   */
  private ToolsUtil() {}

  /**
   * this method get an object on Json format.
   *
   * @author abraham.hernandez
   * @param object - object that will be formated
   * @return String
   */
  public static <T> String getObjectAsJson(T object) {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (Exception ex) {
      LOG.error("Exception parsing object message: {}, trace: {}", ex.getMessage(),
          getStackTrace(ex));
    }
    return null;
  }

  /**
   * This method get stackTrace of an exception.
   *
   * @param ex - exception which is going to be shown
   * @return String
   */
  public static String getStackTrace(Throwable ex) {

    String result = null;

    StringWriter sw = new StringWriter();

    ex.printStackTrace(new PrintWriter(sw));

    result = sw.toString();

    return result;

  }

}
