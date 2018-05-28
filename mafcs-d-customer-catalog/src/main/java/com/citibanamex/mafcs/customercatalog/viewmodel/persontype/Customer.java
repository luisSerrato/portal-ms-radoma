package com.citibanamex.mafcs.customercatalog.viewmodel.persontype;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author rp90642
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
public class Customer {

	/**
	 * Description = Residency Status Detail Name
	 * Example = FISICA NACIONAL EN EL PAIS
	 * Required = True
	 */
	@ApiModelProperty(value = "residencyStatusDetail", required = true)
	private String residencyStatusDetail;
	/**
	 * Description = Residency Status Detail Code
	 * Example = 1
	 * Required = True
	 */
	@ApiModelProperty(value = "residencyStatusDetailCode", required = true)
	private int residencyStatusDetailCode;

	public Customer(){

	}

	public Customer(String residencyStatusDetail, int residencyStatusDetailCode) {
		super();
		this.residencyStatusDetail = residencyStatusDetail;
		this.residencyStatusDetailCode = residencyStatusDetailCode;
	}


	/**
	 * Description = Get value Residency Status Detail Name
	 * Example = FISICA NACIONAL EN EL PAIS
	 * Required = True
	 * 
	 * @return the value as String of Residency Status Detail
	 */
	public String getResidencyStatusDetail() {
		return residencyStatusDetail;
	}

	/**
	 * Description = Get value Residency Status Detail Name
	 * Example = FISICA NACIONAL EN EL PAIS
	 * Required = True
	 * 
	 * @param 	residencyStatusDetail
	 */
	public void setResidencyStatusDetail(String residencyStatusDetail) {
		this.residencyStatusDetail = residencyStatusDetail;
	}

	/**
	 * Description = Get value Residency Status Detail Code
	 * Example = 1
	 * Required = True
	 * 
	 * @return the value as String of Residency Status Detail Code
	 */
	public int getResidencyStatusDetailCode() {
		return residencyStatusDetailCode;
	}

	/**
	 * Description = Get value Residency Status Detail Code
	 * Example = FISICA NACIONAL EN EL PAIS
	 * Required = True
	 * 
	 * @param 	residencyStatusDetailCode
	 */
	public void setResidencyStatusDetailCode(int residencyStatusDetailCode) {
		this.residencyStatusDetailCode = residencyStatusDetailCode;
	}

}