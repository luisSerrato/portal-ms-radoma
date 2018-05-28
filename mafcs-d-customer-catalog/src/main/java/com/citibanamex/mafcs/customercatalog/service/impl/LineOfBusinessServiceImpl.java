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
import com.citibanamex.mafcs.customercatalog.service.LineOfBusinessService;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusiness;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusinessResponse;

@Service("LineOfBusinessService")
public class LineOfBusinessServiceImpl implements LineOfBusinessService {
	
	@Autowired
	private C080Client c080Client;
	
	@Autowired
	private HeraFormatter heraFormatter;
	
	private static final Logger logger = LoggerFactory.getLogger(LineOfBusinessServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public LineofBusinessResponse getLineofBusiness(String lineOfBusinessFilter) {
		
		logger.info("CustomerCatalog-LineOfBusinessService");
		String sql;
		if(lineOfBusinessFilter.equals("")){
			sql = Constants.SQL_HERA_LineOfBussiness_GET_ALL;
		}else{
			sql = "SELECT TOP 5 * FROM (" + Constants.SQL_HERA_LineOfBussiness_BY_DESC + ") X where X.DESCRIP LIKE '%"+lineOfBusinessFilter+"%' OR X.DESCRIP LIKE '%"+lineOfBusinessFilter+"%' ORDER BY 2";
		}		
		logger.debug("QueryExecuted: " + sql);
		Object responseC080 = getDataFromC080(sql);
		
		HashMap<String, Object> campos = new HashMap<>();
		List<Object> lineOfBusinessAux = new ArrayList<>();		
		Util.resultC080CamposMasDatos(responseC080, campos, lineOfBusinessAux);
		
		if (lineOfBusinessAux.isEmpty()) {
			throw new DataNotFoundException("Data Not Found");
		}
		
		int palabra = (int) campos.get("PALABRA");
		int descrip = (int) campos.get("DESCRIP");
		
		List<LineofBusiness> lineOfBusinessList = new ArrayList<LineofBusiness>();

		for (Object lineOfBusiness : lineOfBusinessAux) {
			
			List<Object> lineOfBusinessDetails = (ArrayList<Object>) lineOfBusiness;
			String occupationSector = (String) lineOfBusinessDetails.get(descrip);
			String occupationSectorCode = heraFormatter.getCILineOfBusiness((String) lineOfBusinessDetails.get(palabra));
			lineOfBusinessList.add(new LineofBusiness(occupationSector, Integer.valueOf(occupationSectorCode)));
		}
		LineofBusinessResponse response = new LineofBusinessResponse();
		response.setLineofBusiness(lineOfBusinessList);
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
