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
import org.springframework.web.bind.annotation.RestController;

import com.citibanamex.mafcs.customercatalog.service.SourceOfIncomeService;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.SourceOfIncomeResponse;

@RestController
@Api(tags = "CustomerSourceOfIncome")
@RequestMapping("/api/private/v1")
public class SourceOfIncomeController {
	
	@Autowired
	private SourceOfIncomeService sourceOfIncomeService;
	
	private static final Logger LOG = LoggerFactory.getLogger(SourceOfIncomeController.class);
	
	public SourceOfIncomeController(){
		// This is a constructor
	}

	/**
	 * Description = TThis API is used to get the type of person
	 * HTTP_Method = GET
	 * Request_URI = /private/v1/consumer-services/catalogs/customers/income/source
	 */
	@ApiOperation(value = "Retrieve the Source Of Income", notes = "This API is used to get source of income of the customer", produces="application/json")
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Ok", response = SourceOfIncomeResponse.class, responseContainer="Object"),
			@ApiResponse(code = 400, message="Bad request"),
			@ApiResponse(code = 500, message="Internal server error")
	})
	@RequestMapping(value="/consumer-services/catalogs/customers/income/source", method = RequestMethod.GET)
	public SourceOfIncomeResponse SourceOfIncome(
			@RequestHeader (name = "client-id", required = false) String clientId,
			@RequestHeader (name = "Authorization", required = false) String authorization,
			@RequestHeader (name = "uuid", required = false) String uuid){	
		
		LOG.info("client-id {}, Authorization {}, uuid {}", 
				clientId, authorization, uuid);
		
		long t0 = System.currentTimeMillis();
		SourceOfIncomeResponse response = sourceOfIncomeService.getSourceOfIncome();
		LOG.info("Time total elapsed RetrieveSourceOfIncome: " + (System.currentTimeMillis() - t0) + " ms");
		return response;
	}
}
