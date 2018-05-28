package com.citibanamex.mafcs.customercatalog.viewmodel.profession;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author jp34651
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class ProfessionResponse {

	@ApiModelProperty(required = true)
	public List<Profession> profession;

	public ProfessionResponse(){

	}

	public List<Profession> getProfession() {
		return profession;
	}

	public void setProfession(List<Profession> profession) {
		this.profession = profession;
	}

	public void finalize() throws Throwable {

	}

}