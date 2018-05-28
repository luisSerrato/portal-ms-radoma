package com.citibanamex.api.productname.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import com.citibanamex.api.productname.exceptions.ApiFactoryBaseException;
import com.citibanamex.api.productname.model.RetrieveProductResponse;
import com.citibanamex.api.productname.service.ProductNameService;
import com.citibanamex.api.productname.util.ExternalApiUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ProductNameControllerTest {


  /**
   * RestTemplate object to have access exchange method.
   */
  @Autowired
  RestTemplate restTemplate;

  /**
   * object to have access to have access controller methods.
   */
  @Autowired
  ProductNameController productNameController;

  /**
   * object to have access ProductNameService methods.
   */
  @MockBean
  ProductNameService productNameService;

  /**
   * object to have access ExternalAPiUtil methods.
   */
  @MockBean
  ExternalApiUtil externalApiUtil;

  /**
   * object to have access ProductService methods.
   */
  @Autowired
  ProductNameService productService;

  /*
   * Test scenario if authorization field is empty.
   * 
   */
  @Ignore
  public void testRetrieveProductNameWithAuthorizationFieldEmpty() {
    try {
      productNameController.retrieveProductName("", "uuid", "accept", "acceptLanguage", "clientId",
          "123456789");
      Assert.fail();
    } catch (ApiFactoryBaseException e) {

    }
  }

  /*
   * Test scenario if UUID field is empty.
   * 
   */
  @Test
  public void testRetrieveProductNameWithUuidFieldEmpty() {
    try {
      productNameController.retrieveProductName("authorization", "", "accept", "acceptLanguage",
          "clientId", "123456789");
      Assert.fail();
    } catch (ApiFactoryBaseException e) {

    }
  }

  /*
   * Test scenario if Accept field is empty.
   * 
   */

  @Test
  public void testRetrieveProductNameWithAcceptFieldEmpty() {
    try {
      productNameController.retrieveProductName("authorization", "uuid", "", "acceptLanguage",
          "clientId", "123456789");
      Assert.fail();
    } catch (ApiFactoryBaseException e) {

    }
  }

  /*
   * Test scenario if Accept Language field is empty.
   * 
   */
  @Test
  public void testRetrieveProductNameWithAcceptLanguageFieldEmpty() {
    try {
      productNameController.retrieveProductName("authorization", "uuid", "accept", "", "clientId",
          "123456789");
      Assert.fail();
    } catch (ApiFactoryBaseException e) {

    }
  }


  /*
   * Test positive scenario to get Product Name.
   * 
   */
  @Test
  public void testRetrieveProductName() {
    try {

      given(productNameService.retrieveProductName("123456789")).willReturn("Product111");

      ResponseEntity<?> responseEntity = productNameController.retrieveProductName("authorization",
          "uuid", "accept", "acceptLanguage", "clientid", "123456789");

      RetrieveProductResponse retrieveProductResponse =
          (RetrieveProductResponse) responseEntity.getBody();

      assertThat(retrieveProductResponse.getBankAccount().getProductName()).isEqualTo("Product111");
      assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

    } catch (Exception e) {

    }
  }

  /**
   * This method is a test fail external api.
   * 
   * @throws ResourceAccessException
   * @throws IOException
   * @throws ApiFactoryBaseException
   */
  @Test
  public void testFailRetrieveProductName()
      throws ResourceAccessException, IOException, ApiFactoryBaseException {

    try {
      Mockito.when(productNameService.retrieveProductName("123456789"))
          .thenThrow(new ResourceAccessException(""));
      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "123456789");
      Assert.fail();
    } catch (Exception ex) {

    }

  }

  /**
   * This method is a test fail external api.
   * 
   * @throws ResourceAccessException
   * @throws IOException
   * @throws ApiFactoryBaseException
   */
  @Test
  public void testFailsRetrieveProductName()
      throws ResourceAccessException, IOException, ApiFactoryBaseException {

    try {
      Mockito.when(productNameService.retrieveProductName("123456789"))
          .thenThrow(new ResourceAccessException(""));
      ResponseEntity<?> response = productNameController.retrieveProductName("authorization",
          "uuid", "accept", "acceptLanguage", "clientId", "123456789");
      assertEquals(String.valueOf(response.getStatusCode()), "500");
    } catch (Exception ex) {

    }

  }

  @Test
  public void testRetrieveProductNameFail() {
    try {
      Mockito.when(productNameService.retrieveProductName("123456789"))
          .thenThrow(new IOException());
      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "123456789");
      Assert.fail();
    } catch (Exception exception) {

    }
  }

  @Test
  public void testRetrieveProductNameFailHttpClientErrorException() {
    try {
      Mockito.when(productNameService.retrieveProductName("123456789"))
          .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, ""));
      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "123456789");
      Assert.fail();
    } catch (Exception exception) {

    }
  }

  @Test
  public void testRetrieveProductNameWithEmptyBin() {

    try {
      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "");

      Assert.fail();

    } catch (Exception exception) {

    }


  }

  @Test
  public void testRetrieveProductNameWithNullBin() {

    try {
      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", null);

      Assert.fail();

    } catch (Exception exception) {

    }
  }

  @Test
  public void testRetrieveProductNameWithNoNumericBin() {
    try {

      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "12345678E");
    } catch (Exception exception) {

    }
  }

  @Test
  public void testRetrieveProductNameWithInvalidNumericBin() {
    try {

      productNameController.retrieveProductName("authorization", "uuid", "accept", "acceptLanguage",
          "clientId", "12345");
    } catch (Exception exception) {

    }
  }



}
