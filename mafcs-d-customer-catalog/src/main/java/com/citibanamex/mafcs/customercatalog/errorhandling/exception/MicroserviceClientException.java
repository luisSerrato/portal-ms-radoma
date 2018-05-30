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

public class MicroserviceClientException extends RuntimeException {
 
  private static final long serialVersionUID = -1243243559832908014L;

  public MicroserviceClientException() {
    super();
  }

  public MicroserviceClientException(String message) {
    super(message);
  }

  public MicroserviceClientException(Throwable throwable) {
    super(throwable);
  }
}
