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

package com.citibanamex.api.productname.util;

import com.citibanamex.api.productname.model.ProductEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class load excel file data.
 *
 * @author abraham.hernandez
 *
 */
@Component
public class ProductNameLoadFile {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameLoadFile.class);

  /**
   * extBankProperties to have access properties.
   */
  @Autowired
  private ProductNameProperties productNameProperties;

  /**
   *
   * @return List with BanksInformation.
   * @throws ParseException - This exception will be thrown if there is a problem parsing a date.
   * @throws IOException - This exception will thrown if there is a problem reading the file.
   */
  public List<ProductEntity> getDataFromFile() {

    List<ProductEntity> listProduct = new ArrayList<ProductEntity>();

    boolean titleFlag = false;
    String titleNameProduct = null;


    LOG.info("Recovering data from file {}", productNameProperties.getProductinfoCatalog());
    Iterator<Row> rowIterator = getFile(productNameProperties.getProductinfoCatalog()).iterator();

    while (rowIterator.hasNext()) {

      ProductEntity product = new ProductEntity();
      Row row = rowIterator.next();
      Cell cell3 = row.getCell(ProductNameConstant.CELL_PRODUCT);
      if (null == cell3) {
        break;
      }
      titleNameProduct = ProductNameConstant.TITLE_PRODUCT;

      if (!titleFlag && cell3.getStringCellValue().equals(titleNameProduct)) {
        titleFlag = true;
      } else {

        Cell cell4 = row.getCell(ProductNameConstant.CELL_INSTRUMENT);
        String productNo = Long.toString((long) cell3.getNumericCellValue());
        String instrumentNo = Long.toString((long) cell4.getNumericCellValue());
        Cell cell8 = row.getCell(ProductNameConstant.CELL_LEJEND);
        String productInfo = cell8.getStringCellValue();
        product.setProductNo(productNo);
        product.setInstrumentNo(instrumentNo);
        product.setProductInfo(productInfo);


        listProduct.add(product);
      }
    }
    LOG.info("Data was recovered successfully from file: {}",
        productNameProperties.getProductinfoCatalog());

    return listProduct;
  }


  /**
   * This method return a XSSFSheet object with an excel file data.
   *
   * @return XSSFSheet
   * @throws IOException - Exception will be thrown if there is a problem reading the file.
   */
  public XSSFSheet getFile(String fileName) {
    Resource resource = null;
    XSSFWorkbook xssfWorkbook = null;
    XSSFSheet xssfSheet = null;
    try {
      resource = new ClassPathResource(fileName);
      xssfWorkbook = new XSSFWorkbook(resource.getInputStream());
      xssfSheet = xssfWorkbook.getSheetAt(0);
    } catch (IOException exc) {
      LOG.error("There was a problem loading file, message {}, cause {}, trace {}",
          exc.getMessage(), exc.getCause(), ToolsUtil.getStackTrace(exc));

    }
    return xssfSheet;
  }



}
