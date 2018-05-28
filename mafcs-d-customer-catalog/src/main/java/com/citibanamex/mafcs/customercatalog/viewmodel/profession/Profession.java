package com.citibanamex.mafcs.customercatalog.viewmodel.profession;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author rp90642
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class Profession {

	/**
	 * Description = Profession Code
	 * Example = 43
	 * Required = True
	 */
	@ApiModelProperty(value = "professionCode", required = true)
	private int professionCode;
	/**
	 * Description = Profession Name
	 * Example = ARRENDAMIENTO DUEÑO
	 * Required = True
	 */
	@ApiModelProperty(value = "professionName", required = true)
	private String professionName;

	public Profession(){

	}

	public Profession(int professionCode, String professionName) {
		super();
		this.professionCode = professionCode;
		this.professionName = professionName;
	}

	/**
	 * Description = Get value of professionCode
	 * Example = 43
	 * Required = True
	 * 
	 * @return the value as int of professionCode
	 */	
	public int getProfessionCode() {
		return professionCode;
	}

	/**
	 * Description = Profession Code
	 * Example = 43
	 * Required = True
	 * 
	 * @param 	professionCode
	 */	
	public void setProfessionCode(int professionCode) {
		this.professionCode = professionCode;
	}

	/**
	 * Description = Profession Name
	 * Example = ARRENDAMIENTO DUEÑO
	 * Required = True
	 * 
	 * @return	the value as String of professionName
	 */
	public String getProfessionName() {
		return professionName;
	}

	/**
	 * Description = Profession Name
	 * Example = ARRENDAMIENTO DUEÑO
	 * Required = True
	 * 
	 * @param	professionName
	 */
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

}