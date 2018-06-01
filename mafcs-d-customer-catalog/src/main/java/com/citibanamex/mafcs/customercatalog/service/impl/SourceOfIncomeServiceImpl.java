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

import com.citibanamex.mafcs.customercatalog.service.SourceOfIncomeService;
import com.citibanamex.mafcs.customercatalog.util.C080HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.IncomeSource;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.SourceOfIncomeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("sourceOfIncomeService")
public class SourceOfIncomeServiceImpl implements SourceOfIncomeService {

  private static final Logger LOG = LoggerFactory.getLogger(SourceOfIncomeService.class);

  @Autowired
  private C080HeraFormatter heraFormatter;

  @SuppressWarnings("unchecked")
  @Override
  public SourceOfIncomeResponse getSourceOfIncome() {

    LOG.info("CustomerCatalog-SourceOfIncomeService");
    LOG.debug("QueryExecuted: " + Constants.SQL_HERA_SOURCEOFINCOME_BY_ID);
    Object responseC080 = heraFormatter.getDataFromC080(Constants.SQL_HERA_SOURCEOFINCOME_BY_ID);
    HashMap<String, Object> campos = new HashMap<>();
    List<Object> sourceOfIncomeAux = new ArrayList<>();
    List<IncomeSource> sourceIncomeList = new ArrayList<IncomeSource>();

    Util.resultC080CamposMasDatos(responseC080, campos, sourceOfIncomeAux);

    int ocupaci = (int) campos.get("OCUPACI");
    int dlarga = (int) campos.get("D_LARGA");

    for (Object source : sourceOfIncomeAux) {

      List<Object> customerDetails = (ArrayList<Object>) source;
      String incomeSource = (String) customerDetails.get(dlarga);
      String incomeSourceCode =
          heraFormatter.getCiSourceOfIncome((String) customerDetails.get(ocupaci));
      sourceIncomeList.add(new IncomeSource(incomeSource, Integer.parseInt(incomeSourceCode)));
    }
    SourceOfIncomeResponse response = new SourceOfIncomeResponse();
    response.setIncome(sourceIncomeList);
    return response;
  }

}
