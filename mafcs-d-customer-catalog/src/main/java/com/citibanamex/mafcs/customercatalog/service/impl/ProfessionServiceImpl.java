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

import com.citibanamex.mafcs.customercatalog.errorhandling.exception.DataNotFoundException;
import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
import com.citibanamex.mafcs.customercatalog.util.C080HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.Profession;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("professionService")
public class ProfessionServiceImpl implements ProfessionService {

  private static final Logger LOG = LoggerFactory.getLogger(ProfessionService.class);

  @Autowired
  private C080HeraFormatter heraFormatter;

  @SuppressWarnings("unchecked")
  @Override
  public ProfessionResponse getProfession(String professionFilter) {

    LOG.info("CustomerCatalog-ProfessionService");
    String sql;
    HashMap<String, Object> campos = new HashMap<>();
    List<Object> professionAux = new ArrayList<>();
    List<Profession> professionList = new ArrayList<Profession>();

    if ("".equals(professionFilter)) {
      sql = Constants.SQL_HERA_PROFESSION_GET_ALL;
    } else {
      sql = "SELECT TOP 5 * FROM ( " + Constants.SQL_HERA_PROFESSION_BY_DESC
          + " ) X WHERE X.DESCRIP LIKE '%" + professionFilter + "%' OR X.DESCRIP LIKE '%"
          + professionFilter + "%' ORDER BY 2";
    }

    LOG.debug("QueryExecuted: " + sql);
    Object responseC080 = heraFormatter.getDataFromC080(sql);
    Util.resultC080CamposMasDatos(responseC080, campos, professionAux);

    if (professionAux.isEmpty()) {
      throw new DataNotFoundException("Data Not Found");
    }

    int palabra = (int) campos.get("PALABRA");
    int descrip = (int) campos.get("DESCRIP");

    for (Object profession : professionAux) {

      List<Object> professionDetails = (ArrayList<Object>) profession;
      String professionName = (String) professionDetails.get(descrip);
      String professionCode =
          heraFormatter.getCiProfession((String) professionDetails.get(palabra));
      professionList.add(new Profession(Integer.parseInt(professionCode), professionName));
    }

    ProfessionResponse response = new ProfessionResponse();
    response.setProfession(professionList);
    return response;
  }

}
