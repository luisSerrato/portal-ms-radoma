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

package com.citibanamex.mafcs.customercatalog.errorhandling.exception;

public class FilterFormatException extends RuntimeException {

  private static final long serialVersionUID = -6304743683507356758L;
  private String varName;

  public FilterFormatException() {}

  public FilterFormatException(String message) {
    super(message);
  }

  public FilterFormatException(String message, String varName) {
    super(message);
    this.varName = varName;
  }

  public FilterFormatException(Throwable ex) {
    super(ex);
  }

  public String getVarName() {
    return varName;
  }

  public void setVarName(String varName) {
    this.varName = varName;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
