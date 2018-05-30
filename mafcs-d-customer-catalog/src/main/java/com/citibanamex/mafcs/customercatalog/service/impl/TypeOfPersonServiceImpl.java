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

package com.citibanamex.mafcs.customercatalog.service.impl;

import com.citibanamex.itmt.ccutil.utils.Utils;
import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.c080client.SqlRequest;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.service.TypeOfPersonService;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.Customer;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.PersonTypeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("typeOfPersonService")
public class TypeOfPersonServiceImpl implements TypeOfPersonService {

  private static final Logger LOG = LoggerFactory.getLogger(TypeOfPersonServiceImpl.class);

  @Autowired
  private C080Client c080Client;

  @SuppressWarnings("unchecked")
  @Override
  public PersonTypeResponse getTypeOfPerson() {

    LOG.info("CustomerCatalog-TypeOfPersonService");
    LOG.debug("QueryExecuted: " + Constants.SQL_HERA_TypeOfPerson_BY_ID);

    Object responseC080 = getDataFromC080(Constants.SQL_HERA_TypeOfPerson_BY_ID);
    HashMap<String, Object> campos = new HashMap<>();
    List<Object> personTypeAux = new ArrayList<>();
    List<Customer> personTypeList = new ArrayList<Customer>();

    Util.resultC080CamposMasDatos(responseC080, campos, personTypeAux);

    int persona = (int) campos.get("PERSONA");
    int dlarga = (int) campos.get("D_LARGA");

    for (Object customer : personTypeAux) {

      List<Object> customerDetails = (ArrayList<Object>) customer;
      String residencyStatusDetailCode = (String) customerDetails.get(persona);
      String residencyStatusDetail = (String) customerDetails.get(dlarga);
      personTypeList
          .add(new Customer(residencyStatusDetail, Integer.parseInt(residencyStatusDetailCode)));
    }

    PersonTypeResponse response = new PersonTypeResponse();
    response.setCustomer(personTypeList);
    return response;
  }

  private Object getDataFromC080(String sql) {

    SqlRequest sqlRequest = new SqlRequest();
    sqlRequest.setSql(sql);
    long t0 = System.currentTimeMillis();
    Object responseDescripcion = c080Client.getInformationC080(sqlRequest);
    LOG.info(
        "Time elapsed c080Client.getInformationC080: " + (System.currentTimeMillis() - t0) + " ms");
    LOG.info("responseDescripcion: " + Utils.getJson(responseDescripcion));
    if (responseDescripcion == null) {
      throw new CcC080CustomerClientException("System C080 back unavailable");
    }

    return responseDescripcion;
  }

}
