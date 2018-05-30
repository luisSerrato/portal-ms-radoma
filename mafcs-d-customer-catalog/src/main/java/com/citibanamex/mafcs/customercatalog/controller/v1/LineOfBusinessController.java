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

package com.citibanamex.mafcs.customercatalog.controller.v1;

import com.citibanamex.mafcs.customercatalog.service.LineOfBusinessService;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusinessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "CustomerLineOfBusiness")
@RequestMapping("/api/private/v1")

public class LineOfBusinessController {

  @Autowired
  private LineOfBusinessService lineOfBusinessService;

  private static final Logger LOG = LoggerFactory.getLogger(LineOfBusinessController.class);

  public LineOfBusinessController() {
    // This is a constructor
  }

  /**
   * Description = This API is used to get the line of business of the customer HTTP_Method = GET.
   * Request_URI = /private/v1/consumer-services/catalogs/customers/occupation/sector. 
   * @param lineOfBusinessFilter.
   */
  @ApiOperation(value = "Retrieve the Line Of Business",
      notes = "This API is used to get the line of business of the customer.",
      produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = LineofBusinessResponse.class,
          responseContainer = "Object"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 402, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal server error")})
  @RequestMapping(value = "/consumer-services/catalogs/customers/occupation/sector",
      method = RequestMethod.GET)
  public LineofBusinessResponse getLineOfBusiness(
      @RequestHeader(name = "client-id", required = false) String clientId,
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestHeader(name = "uuid", required = false) String uuid,
      @RequestParam(value = "lineOfBusinessFilter", defaultValue = "",
          required = false) String lineOfBusinessFilter) {

    LOG.info("client-id {}, Authorization {}, uuid {}, lineOfBusinessFilter {} ", clientId,
        authorization, uuid, lineOfBusinessFilter);

    long t0 = System.currentTimeMillis();
    LineofBusinessResponse response = lineOfBusinessService
        .getLineofBusiness(Util.validateFilter(lineOfBusinessFilter, "lineOfBusinessFilter"));
    LOG.info(
        "Time total elapsed RetrieveLineBusiness: " + (System.currentTimeMillis() - t0) + " ms");
    return response;
  }
}
