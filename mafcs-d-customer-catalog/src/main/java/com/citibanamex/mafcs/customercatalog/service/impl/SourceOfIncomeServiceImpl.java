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
import com.citibanamex.mafcs.customercatalog.service.SourceOfIncomeService;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.IncomeSource;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.SourceOfIncomeResponse;

@Service("sourceOfIncomeService")
public class SourceOfIncomeServiceImpl implements SourceOfIncomeService {

	private static final Logger logger = LoggerFactory.getLogger(SourceOfIncomeService.class);
	
	@Autowired
	private C080Client c080Client;
	
	@Autowired
	private HeraFormatter heraFormatter;
	
	@SuppressWarnings("unchecked")
	@Override
	public SourceOfIncomeResponse getSourceOfIncome() {
		
		logger.info("CustomerCatalog-SourceOfIncomeService");
		logger.debug("QueryExecuted: " + Constants.SQL_HERA_SourceOfIncome_BY_ID);
		Object responseC080 = getDataFromC080(Constants.SQL_HERA_SourceOfIncome_BY_ID);
		HashMap<String, Object> campos = new HashMap<>();
		List<Object> sourceOfIncomeAux = new ArrayList<>();	
		List<IncomeSource> sourceIncomeList = new ArrayList<IncomeSource>();
			
		Util.resultC080CamposMasDatos(responseC080, campos, sourceOfIncomeAux);
		
		int ocupaci = (int) campos.get("OCUPACI");
		int d_larga = (int) campos.get("D_LARGA");		

		for (Object source : sourceOfIncomeAux) {
			
			List<Object> customerDetails = (ArrayList<Object>) source;
			String incomeSource = (String) customerDetails.get(d_larga);
			String incomeSourceCode = heraFormatter.getCISourceOfIncome((String) customerDetails.get(ocupaci));
			sourceIncomeList.add(new IncomeSource(incomeSource,Integer.valueOf(incomeSourceCode)));
		}
		SourceOfIncomeResponse response = new SourceOfIncomeResponse();
		response.setIncome(sourceIncomeList);
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