package com.citibanamex.mafcs.customercatalog.errorhandling;

import io.swagger.annotations.ApiModelProperty;

public class ErrorResponse {

	@ApiModelProperty(required = true)
	private String type;
	@ApiModelProperty(required = true)
	private String code;
	@ApiModelProperty(required = true)
	private String details;
	@ApiModelProperty(required = true)
	private String location;
	@ApiModelProperty(required = true)
	private String moreInfo;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	
}
