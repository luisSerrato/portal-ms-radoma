package com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jp34651
 * @version 1.0
 * @created 23-Jan-2018 5:01:55 PM
 */
public class SourceOfIncomeResponse {

	@ApiModelProperty(required = true)
	public List<IncomeSource> income;

	public SourceOfIncomeResponse(){
		this.setIncome(new ArrayList<>());
	}
	
	public List<IncomeSource> getIncome() {
		return income;
	}

	public void setIncome(List<IncomeSource> income) {
		this.income = income;
	}

	public void finalize() throws Throwable {

	}

}