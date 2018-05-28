/*
 * Copyright (C) 2018 by Citigroup. All rights reserved. Citigroup claims copyright in this computer
 * program as an unpublished work, one or more versions of which were first used to provide services
 * to customers on the dates indicated in the foregoing notice. Claim of copyright does not imply
 * waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of Citigroup. Use, examination,
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of all or any part
 * of this software program are strictly prohibited except by express written agreement with
 * Citigroup.
 */
package com.citibanamex.mafcs.customercatalog.errorhandling;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	
	
}
