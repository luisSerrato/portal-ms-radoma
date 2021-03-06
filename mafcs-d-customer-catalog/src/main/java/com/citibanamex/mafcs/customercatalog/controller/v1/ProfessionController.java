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

import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "CustomerProfession")
@RequestMapping("/api/private/v1")
public class ProfessionController {

  private static final Logger LOG = LoggerFactory.getLogger(ProfessionController.class);

  @Autowired
  private ProfessionService professionService;

  public ProfessionController() {
    // This is a constructor
  }

  /**
   * Description = This API is used to get the profession of the customer HTTP_Method = GET.
   * Request_URI = /private/v1/consumer-services/catalogs/customers/profession.
   * @param professionFilter.
   */
  @ApiOperation(value = "Retrieve the Profession",
      notes = "This API is used to get the profession of the customer.",
      produces = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok", response = ProfessionResponse.class,
          responseContainer = "Object"),
      @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 402, message = "Not Found"),
      @ApiResponse(code = 500, message = "Internal server error")})
  @RequestMapping(value = "/consumer-services/catalogs/customers/profession",
      method = RequestMethod.GET)
  public ProfessionResponse getProfession(
      @RequestHeader(name = "client-id", required = false) String clientId,
      @RequestHeader(name = "Authorization", required = false) String authorization,
      @RequestHeader(name = "uuid", required = false) String uuid,
      @RequestParam(value = "professionFilter", defaultValue = "",
          required = false) String professionFilter) {

    LOG.info("client-id {}, Authorization {}, uuid {}, professionFilter {} ", clientId,
        authorization, uuid, professionFilter);

    if (StringUtils.isEmpty(uuid)) {
      throw new ValidationException("Header uuid may not be empty");
    }
    
    long t0 = System.currentTimeMillis();
    ProfessionResponse response =
        professionService.getProfession(Util.validateFilter(professionFilter, "professionFilter"));
    LOG.info("Time total elapsed RetrieveProfession: " + (System.currentTimeMillis() - t0) + " ms");
    return response;
  }
}
