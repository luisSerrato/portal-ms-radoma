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

package com.citibanamex.api;

import com.citibanamex.api.productname.exceptions.RestServiceErrorHandler;
import com.citibanamex.api.productname.model.ProductEntity;
import com.citibanamex.api.productname.util.ProductNameLoadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;


/**
 * This class set up spring application context.
 *
 * @author
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class CitibanamexApiProductnameApplication {

  /**
   * Object to have access methods that gets information from excel file.
   */
  @Autowired
  ProductNameLoadFile productNameLoadFile;

  /**
   * main method which set up application context.
   *
   * @param args - arguments which can be set by console.
   */
  public static void main(String[] args) {
    SpringApplication.run(CitibanamexApiProductnameApplication.class, args);
  }

  /**
   * To handle 404 Not Found Exception.
   * @return - DispatcherServlet
   */
  @Bean
  DispatcherServlet dispatcherServlet() {
    DispatcherServlet ds = new DispatcherServlet();
    ds.setThrowExceptionIfNoHandlerFound(true);
    return ds;
  }

  /**
   * Method returns and set ErrorHandler to restTemplate.
   * @param builder - builder.
   * @return - RestTemplate.
   */
  @Bean
  RestTemplate restTemplate(RestTemplateBuilder builder) {

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RestServiceErrorHandler());
    return restTemplate;
  }

  /**
   * Bean that obtains and returns an object with file information.
   * @return ProductFile.
   */
  @Bean
  List<ProductEntity> productFile() {


    return productNameLoadFile.getDataFromFile();


  }
}
