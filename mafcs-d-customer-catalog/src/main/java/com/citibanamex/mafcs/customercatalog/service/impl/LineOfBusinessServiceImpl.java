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
import com.citibanamex.mafcs.customercatalog.service.LineOfBusinessService;
import com.citibanamex.mafcs.customercatalog.util.C080HeraFormatter;
import com.citibanamex.mafcs.customercatalog.util.Constants;
import com.citibanamex.mafcs.customercatalog.util.Util;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusiness;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusinessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("LineOfBusinessService")
public class LineOfBusinessServiceImpl implements LineOfBusinessService {

  
  @Autowired
  private C080HeraFormatter heraFormatter;

  private static final Logger LOG = LoggerFactory.getLogger(LineOfBusinessServiceImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public LineofBusinessResponse getLineofBusiness(String lineOfBusinessFilter) {

    LOG.info("CustomerCatalog-LineOfBusinessService");
    String sql;
    if ("".equals(lineOfBusinessFilter)) {
      sql = Constants.SQL_HERA_LINEOFBUSINESS_GET_ALL;
    } else {
      sql = "SELECT TOP 5 * FROM (" + Constants.SQL_HERA_LINEOFBUSINESS_BY_DESC
          + ") X where X.DESCRIP LIKE '%" + lineOfBusinessFilter + "%' OR X.DESCRIP LIKE '%"
          + lineOfBusinessFilter + "%' ORDER BY 2";
    }
    LOG.debug("QueryExecuted: " + sql);
    Object responseC080 = heraFormatter.getDataFromC080(sql);

    HashMap<String, Object> campos = new HashMap<>();
    List<Object> lineOfBusinessAux = new ArrayList<>();
    Util.resultC080CamposMasDatos(responseC080, campos, lineOfBusinessAux);

    if (lineOfBusinessAux.isEmpty()) {
      throw new DataNotFoundException("Data Not Found");
    }

    int palabra = (int) campos.get("PALABRA");
    int descrip = (int) campos.get("DESCRIP");

    List<LineofBusiness> lineOfBusinessList = new ArrayList<LineofBusiness>();

    for (Object lineOfBusiness : lineOfBusinessAux) {

      List<Object> lineOfBusinessDetails = (ArrayList<Object>) lineOfBusiness;
      String occupationSector = (String) lineOfBusinessDetails.get(descrip);
      String occupationSectorCode =
          heraFormatter.getCiLineOfBusiness((String) lineOfBusinessDetails.get(palabra));
      lineOfBusinessList
          .add(new LineofBusiness(occupationSector, Integer.parseInt(occupationSectorCode)));
    }
    LineofBusinessResponse response = new LineofBusinessResponse();
    response.setLineofBusiness(lineOfBusinessList);
    return response;
  }


}
