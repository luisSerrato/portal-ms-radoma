package com.citibanamex.mafcs.customercatalog.errorhandling;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.citibanamex.itmt.ccutil.constants.ErrorCatalog;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.BadRequestFeignException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.DataNotFoundException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.FilterFormatException;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.citibanamex.mafcs.customercatalog.util.Constants;

@ControllerAdvice
public class ErrorResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorResolver.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse resolveException(HttpServletRequest req, Exception e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.EXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());	
		
		return errorResponse;
	}
	
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse resolveNumberFormatException(HttpServletRequest req, Exception e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.EXCEPTION_ERROR_CODE);
		errorResponse.setDetails("FormatException");
		
		return errorResponse;
	}
		
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse resolveValidationException(HttpServletRequest req, Exception e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.VALIDATIONEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());

		return errorResponse;
	}
	
	@ExceptionHandler(BadRequestFeignException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse resolveBadRequestFeignException(HttpServletRequest req, Exception e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.BADREQUESTFEIGNEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());

		return errorResponse;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse resolveHttpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.INVALID.name());
		errorResponse.setCode(Constants.HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());

		return errorResponse;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse resolveMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.INVALID.name());
		errorResponse.setCode(Constants.HTTPMESSAGENOTREADABLEEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());		
		List<String> fields = new ArrayList<>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			fields.add(fieldError.getField());
		}		
		errorResponse.setLocation(fields.toString());
		
		return errorResponse;
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse resolveDataNotFoundExceptionException(HttpServletRequest req, DataNotFoundException e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.DATANOTFOUNDEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(ErrorCatalog.GENERIC_FAILURE_DESC);
		errorResponse.setLocation(Constants.MSGWITHOUTCOMMENTS);
		errorResponse.setMoreInfo(e.getMessage());

		return errorResponse;
	}

	@ExceptionHandler(CcC080CustomerClientException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse resolveCcC080AddressClientException(HttpServletRequest req, Exception e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.CCC080CUSTOMERCLIENTEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());

		return errorResponse;
	}
	
	@ExceptionHandler(FilterFormatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse resolverFilterFormatException(HttpServletRequest req, FilterFormatException e) {
		
		logger.error(e.getMessage(), e);
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setType(ErrorType.ERROR.name());
		errorResponse.setCode(Constants.VALIDATIONEXCEPTION_ERROR_CODE);
		errorResponse.setDetails(e.getMessage());
		errorResponse.setLocation(e.getVarName());
		errorResponse.setMoreInfo("May be [3-12] letters of the word to search");
		
		return errorResponse;
	}
}