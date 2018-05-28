package com.citibanamex.mafcs.customercatalog.service.impl;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import com.citibanamex.itmt.ccutil.commons.Result;
//import com.citibanamex.itmt.ccutil.constants.ConstantsMessages;
import com.citibanamex.itmt.ccutil.utils.Utils;
import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.c080client.SqlRequest;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
//import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.citibanamex.mafcs.customercatalog.service.TypeOfPersonService;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.Customer;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.PersonTypeResponse;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;

@Service("typeOfPersonService")
public class TypeOfPersonServiceImpl implements TypeOfPersonService{

	private static final Logger logger = LoggerFactory.getLogger(TypeOfPersonServiceImpl.class);
	
	@Autowired
	private C080Client c080Client;
	
	@SuppressWarnings("unchecked")
	@Override
	public PersonTypeResponse getTypeOfPerson() {
		
		logger.info("CustomerCatalog-TypeOfPersonService");
		logger.debug("QueryExecuted: " + Constants.SQL_HERA_TypeOfPerson_BY_ID);
		
		Object responseC080 = getDataFromC080(Constants.SQL_HERA_TypeOfPerson_BY_ID);		
		HashMap<String, Object> campos = new HashMap<>();
		List<Object> personTypeAux = new ArrayList<>();	
		List<Customer> personTypeList = new ArrayList<Customer>();
		
		Util.resultC080CamposMasDatos(responseC080, campos, personTypeAux);	
		
		int persona = (int) campos.get("PERSONA");
		int d_larga = (int) campos.get("D_LARGA");	
		
		for (Object customer : personTypeAux) {
			
			List<Object> customerDetails = (ArrayList<Object>) customer;
			String residencyStatusDetailCode = (String) customerDetails.get(persona);
			String residencyStatusDetail = (String) customerDetails.get(d_larga);
			personTypeList.add(new Customer(residencyStatusDetail,Integer.valueOf(residencyStatusDetailCode)));
		}
		
		PersonTypeResponse response = new PersonTypeResponse();
		response.setCustomer(personTypeList);
		return response;
	}
	
	private Object getDataFromC080(String sql) {
		
		SqlRequest sqlRequest = new SqlRequest();
		sqlRequest.setSql(sql);		
		long t0 = System.currentTimeMillis();
		Object responseDescripcion = c080Client.getInformationC080(sqlRequest);
		logger.info("Time elapsed c080Client.getInformationC080: " + (System.currentTimeMillis() - t0) + " ms");
		logger.info("responseDescripcion: " + Utils.getJson(responseDescripcion));
		if (responseDescripcion == null) {
			throw new CcC080CustomerClientException("System C080 back unavailable");
		}
		
		return responseDescripcion;
	}

}
