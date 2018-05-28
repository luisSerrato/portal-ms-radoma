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

import com.citibanamex.mafcs.customercatalog.service.TypeOfPersonService;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.PersonTypeResponse;

@RestController
@Api(tags = "TypeOfPerson")
@RequestMapping("/api/private/v1")
public class TypeOfPersonController {
	
	@Autowired
	private TypeOfPersonService typeOfPersonService;
	
	private static final Logger LOG = LoggerFactory.getLogger(TypeOfPersonController.class);
	
	public TypeOfPersonController(){
		// This is a constructor
	}

	/**
	 * Description = TThis API is used to get the type of person
	 * HTTP_Method = GET
	 * Request_URI = /private/v1/consumer-services/catalogs/customers/resident/status
	 */
	@ApiOperation(value = "Retrieve the Person Type", notes = "This API is used to get the type of person", produces="application/json")
	@ApiResponses(value={
			@ApiResponse(code = 200, message="Ok", response = PersonTypeResponse.class, responseContainer="Object"),
			@ApiResponse(code = 400, message="Bad request"),
			@ApiResponse(code = 500, message="Internal server error")
	})
	@RequestMapping(value="/consumer-services/catalogs/customers/resident/status", method = RequestMethod.GET)
	public PersonTypeResponse personType(
			@RequestHeader (name = "client-id", required = false) String clientId,
			@RequestHeader (name = "Authorization", required = false) String authorization,
			@RequestHeader (name = "uuid", required = false) String uuid){	
		
		long t0 = System.currentTimeMillis();
		PersonTypeResponse response = typeOfPersonService.getTypeOfPerson();
		LOG.info("Time total elapsed RetrievePersonType: " + (System.currentTimeMillis() - t0) + " ms");
		return response;
	}
}
