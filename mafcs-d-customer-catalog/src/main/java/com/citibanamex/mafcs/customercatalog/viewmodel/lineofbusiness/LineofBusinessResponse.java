package com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author jp34651
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class LineofBusinessResponse {

	@ApiModelProperty(required = true)
	public List<LineofBusiness> lineofBusiness;

	public LineofBusinessResponse(){

	}

	public List<LineofBusiness> getLineofBusiness() {
		return lineofBusiness;
	}

	public void setLineofBusiness(List<LineofBusiness> lineofBusiness) {
		this.lineofBusiness = lineofBusiness;
	}

	public void finalize() throws Throwable {

	}

}