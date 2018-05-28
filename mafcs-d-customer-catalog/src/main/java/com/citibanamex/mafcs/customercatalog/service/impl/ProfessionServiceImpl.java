package com.citibanamex.mafcs.customercatalog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citibanamex.itmt.ccutil.utils.Utils;
import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.c080client.SqlRequest;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.DataNotFoundException;
import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.Profession;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;

@Service("professionService")
public class ProfessionServiceImpl implements ProfessionService{

	private static final Logger logger = LoggerFactory.getLogger(ProfessionService.class);
	
	@Autowired
	private C080Client c080Client;
	
	@Autowired
	private HeraFormatter heraFormatter;
	
	@SuppressWarnings("unchecked")
	@Override
	public ProfessionResponse getProfession(String professionFilter) {
		
		logger.info("CustomerCatalog-ProfessionService");
		String sql;
		HashMap<String, Object> campos = new HashMap<>();
		List<Object> professionAux = new ArrayList<>();		
		List<Profession> professionList = new ArrayList<Profession>();
		
		if(professionFilter.equals("")){
			sql = Constants.SQL_HERA_Profession_GET_ALL;
		}else{
			sql = "SELECT TOP 5 * FROM ( " + Constants.SQL_HERA_Profession_BY_DESC + " ) X WHERE X.DESCRIP LIKE '%"+professionFilter+"%' OR X.DESCRIP LIKE '%"+professionFilter+"%' ORDER BY 2";
		}		
		
		logger.debug("QueryExecuted: " + sql);
		Object responseC080 = getDataFromC080(sql);		
		Util.resultC080CamposMasDatos(responseC080, campos, professionAux);	
		
		if (professionAux.isEmpty()) {
			throw new DataNotFoundException("Data Not Found");
		}
		
		int palabra = (int) campos.get("PALABRA");
		int descrip = (int) campos.get("DESCRIP");		

		for (Object profession : professionAux) {
			
			List<Object> professionDetails = (ArrayList<Object>) profession;
			String professionName = (String) professionDetails.get(descrip);
			String professionCode = heraFormatter.getCIProfession((String) professionDetails.get(palabra));
			professionList.add(new Profession(Integer.valueOf(professionCode),professionName));
		}
		
		ProfessionResponse response = new ProfessionResponse();
		response.setProfession(professionList);
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
