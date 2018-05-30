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

package com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome;

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
public class IncomeSource {

  /**
   * Description = income source name Example = Required = True.
   */
  @ApiModelProperty(value = "incomeSource", required = true)
  private String incomeSource;
  /**
   * Description = income source code Example = Required = True.
   */
  @ApiModelProperty(value = "incomeSourceCode", required = true)
  private int incomeSourceCode;

  public IncomeSource() {

  }

  /**
   * To init IncomeSource.
   * @param incomeSource.
   * @param incomeSourceCode.
   */
  public IncomeSource(String incomeSource, int incomeSourceCode) {
    super();
    this.incomeSource = incomeSource;
    this.incomeSourceCode = incomeSourceCode;
  }

}
