package com.citibanamex.mafcs.customercatalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableSwagger2
public class MafcsDCustomerCatalogApplication {
	
	private final Logger LOGGER = LoggerFactory.getLogger(MafcsDCustomerCatalogApplication.class);
	

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
			LOGGER.info("errorDecoder.response: {}", response.toString());

			return new MicroserviceClientException();
		};
	} 
	
}
