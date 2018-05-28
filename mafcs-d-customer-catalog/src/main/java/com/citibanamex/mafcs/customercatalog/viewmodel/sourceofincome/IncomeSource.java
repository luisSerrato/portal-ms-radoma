package com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author rp90642
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class IncomeSource {

	/**
	 * Description = income source name
	 * Example = 
	 * Required = True
	 */
	@ApiModelProperty(value = "incomeSource", required = true)
	private String incomeSource;
	/**
	 * Description = income source code
	 * Example = 
	 * Required = True
	 */
	@ApiModelProperty(value = "incomeSourceCode", required = true)
	private int incomeSourceCode;

	public IncomeSource(){

	}
	
	public IncomeSource(String incomeSource, int incomeSourceCode) {
		super();
		this.incomeSource = incomeSource;
		this.incomeSourceCode = incomeSourceCode;
	}

	/**
	 * Description =  get income source name
	 * Example = 
	 * Required = True
	 * 
	 * @return 	the value as String of incomeSource
	 */
	public String getIncomeSource() {
		return incomeSource;
	}

	/**
	 * Description = set income source name
	 * Example = 
	 * Required = True
	 * 
	 * @param 	incomeSource
	 */
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	/**
	 * Description = get income source code
	 * Example = 
	 * Required = True
	 * 
	 * @return 	the value as int of incomeSource
	 */
	public int getIncomeSourceCode() {
		return incomeSourceCode;
	}

	/**
	 * Description = set income source code
	 * Example = 
	 * Required = True
	 * 
	 * @param incomeSourceCode
	 */
	public void setIncomeSourceCode(int incomeSourceCode) {
		this.incomeSourceCode = incomeSourceCode;
	}
	
}