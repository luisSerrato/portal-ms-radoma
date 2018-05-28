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

package com.citibanamex.mafcs.customercatalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.citibanamex.mafcs.customercatalog.errorhandling.exception.MicroserviceClientException;

import feign.codec.ErrorDecoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwagger2
public class MafcsDCustomerCatalogApplication {
	
	private final Logger LOG = LoggerFactory.getLogger(MafcsDCustomerCatalogApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(MafcsDCustomerCatalogApplication.class, args);
	}
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.citibanamex.mafcs.customercatalog.controller.v1"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("mafcs-d-customer-catalog")
				.description("This is a service that returns customer information.").version("1.0")
				.contact(new Contact("Alejandro Luna Vega", "https://www.anzen.com.mx/", "aluna@anzen.com.mx")).build();
	}
	
	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			LOG.info("errorDecoder.response: {}", response.toString());

			return new MicroserviceClientException();
		};
	} 
	
}
