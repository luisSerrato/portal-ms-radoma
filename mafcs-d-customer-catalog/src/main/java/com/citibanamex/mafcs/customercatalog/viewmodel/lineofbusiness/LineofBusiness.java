package com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @author rp90642
 * @version 1.0
 * @created 23-Jan-2018 5:01:54 PM
 */
@Getter
@Setter
public class LineofBusiness {

	/**
	 * Description = Occupation Sector Name
	 * Example = ABRIGOS, CONFECCIÓN
	 * Required = True
	 */
	@ApiModelProperty(value = "occupationSector", required = true)
	private String occupationSector;
	/**
	 * Description = Occupation Sector Code
	 * Example = 1
	 * Required = True
	 */
	@ApiModelProperty(value = "occupationSectorCode", required = true)
	private int occupationSectorCode;

	public LineofBusiness (){
		
	}
	
	public LineofBusiness(String occupationSector, int occupationSectorCode) {
		super();
		this.occupationSector = occupationSector;
		this.occupationSectorCode = occupationSectorCode;
	}

		
}