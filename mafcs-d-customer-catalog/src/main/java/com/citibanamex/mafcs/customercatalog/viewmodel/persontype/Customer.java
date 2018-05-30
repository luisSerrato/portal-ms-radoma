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

package com.citibanamex.mafcs.customercatalog.viewmodel.persontype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rp90642.
 * @version 1.0.
 * @created 23-Jan-2018 5:01:54 PM.
 */
@Getter
@Setter
public class Customer {

  /**
   * Description = Residency Status Detail Name Example = FISICA NACIONAL EN EL PAIS 
   * Required = True.
   */
  @ApiModelProperty(value = "residencyStatusDetail", required = true)
  private String residencyStatusDetail;
  /**
   * Description = Residency Status Detail Code Example = 1 Required = True.
   */
  @ApiModelProperty(value = "residencyStatusDetailCode", required = true)
  private int residencyStatusDetailCode;

  public Customer() {

  }

  /**
   * To init customer.
   * @param residencyStatusDetail.
   * @param residencyStatusDetailCode.
   */
  public Customer(String residencyStatusDetail, int residencyStatusDetailCode) {
    super();
    this.residencyStatusDetail = residencyStatusDetail;
    this.residencyStatusDetailCode = residencyStatusDetailCode;
  }

}
