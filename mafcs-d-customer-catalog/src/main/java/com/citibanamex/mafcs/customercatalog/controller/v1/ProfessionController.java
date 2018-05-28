package com.citibanamex.mafcs.customercatalog.controller.v1;


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

import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;

@RestController
@Api(tags = "CustomerProfession")
@RequestMapping("/api/private/v1")
public class ProfessionController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProfessionController.class);
	
	@Autowired
	private ProfessionService professionService;
	
	public ProfessionController(){
		// This is a constructor
	}

	/**
	 * Description = This API is used to get the profession of the customer
	 * HTTP_Method = GET
	 * Request_URI = /private/v1/consumer-services/catalogs/customers/profession
	 * 
	 * @param professionFilter
	 */
	@ApiOperation(value = "Retrieve the Profession", notes = "This API is used to get the profession of the customer.", produces="application/json")
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Ok", response = ProfessionResponse.class, responseContainer="Object"),
			@ApiResponse(code = 400, message="Bad request"),
			@ApiResponse(code = 402, message="Not Found"),
			@ApiResponse(code = 500, message="Internal server error")
	})
	@RequestMapping(value="/consumer-services/catalogs/customers/profession", method = RequestMethod.GET)
	public ProfessionResponse getProfession(
			@RequestHeader (name = "client-id", required = false) String clientId,
			@RequestHeader (name = "Authorization", required = false) String authorization,
			@RequestHeader (name = "uuid", required = false) String uuid,
			@RequestParam (value = "professionFilter", defaultValue="", required = false) String professionFilter){		

		LOG.info("lineOfBusinessFilter request param {} " + professionFilter);
		
		long t0 = System.currentTimeMillis();		
		ProfessionResponse response = professionService.getProfession(Util.validateFilter(professionFilter, "professionFilter"));
		LOG.info("Time total elapsed RetrieveProfession: " + (System.currentTimeMillis() - t0) + " ms");
		return response;
	}
}
