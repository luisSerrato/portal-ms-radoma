package com.citibanamex.mafcs.customercatalog.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.citibanamex.itmt.ccutil.commons.Result;
import com.citibanamex.itmt.ccutil.constants.ConstantsMessages;
import com.citibanamex.itmt.ccutil.utils.Utils;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.FilterFormatException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
	@SuppressWarnings("unchecked")
	public static void resultC080CamposMasDatos(Object response, HashMap<String, Object> campos, List<Object> datos){
		
		Result result = responseMicroServicesToResult(response);
		if (!result.isSuccess()) {
			throw new ValidationException(result.getCodigo().toString());
		}
		campos.putAll((HashMap<String, Object>) result.getDatos().get(Constants.CONS_CAMPOS));
		datos.addAll((List<Object>) result.getDatos().get(Constants.CONS_DATOS));
	}
	
	private static Result responseMicroServicesToResult(Object response) {
		HashMap<String, Object> mapResponse = entityToMap(response, false);
		if (mapResponse != null) {
			Boolean successFlag = (Boolean) mapResponse.get("success");
			if (successFlag) {
				String datosResponse = (String) mapResponse.get(Constants.CONS_DATOS);
				HashMap<String, Object> mapDatosTemp = entityToMap(datosResponse, true);
				HashMap<String, Object> mapDatos = entityToMap(mapDatosTemp, false);
				return new Result(mapDatos);
			} else {
				Integer codigo = (mapResponse.get("codigo") != null) ? (Integer) mapResponse.get("codigo") : ConstantsMessages.ERROR_NO_SE_ENCONTRARON_DATOS;
				String mensaje = (String) mapResponse.get("mensaje");
				return new Result(successFlag, codigo, mensaje);
			}
		} else {
			throw new ValidationException(Constants.VALIDATION_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> entityToMap(Object response, boolean isString) {
		String responseString;
		if (isString) {
			responseString = (String) response;
		} else {
			responseString = Utils.getJson(response);
		}
		responseString = "{\"dat\" : " + responseString + "}";
		HashMap<String, Object> mapResponseString = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = mapper.readTree(responseString);
			mapResponseString = (HashMap<String, Object>) mapper.convertValue(root.at("/dat"), Map.class);
		} catch (IOException e) {
			logger.error("logger " , e);
			throw new ValidationException(Constants.VALIDATION_ERROR);
		}

		return mapResponseString;
	}
	
	public static String validateFilter(String filter, String varName){
		
		logger.debug(varName + ":" + filter);
		filter = filter.replaceAll(",{2,}", ",").replaceAll("\\s{2,}", " ").trim().toUpperCase();
		if(filter.equals("") || filter==null || filter.length()==0){
			return "";
		}
		if(filter.length()<3 || filter.length()>12){
			throw new FilterFormatException(Constants.FILTER_WRONG_FORMAT, varName);
		}
		if(!filter.matches("[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ,\\s]+")){
			throw new FilterFormatException(Constants.FILTER_WRONG_FORMAT, varName);
		}
		return filter;
	}
}
