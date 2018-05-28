package com.citibanamex.api.productname.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import com.citibanamex.api.productname.controller.ProductNameController;
import com.citibanamex.api.productname.exceptions.ApiFactoryFallBackException;
import com.citibanamex.api.productname.model.BinInfo;
import com.citibanamex.api.productname.service.ProductNameService;
import com.citibanamex.api.productname.util.CatalogDataUtil;
import com.citibanamex.api.productname.util.ExternalApiUtil;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ProductNameServiceImplTest {

  /**
   * property to have access controller method.
   */
  @Autowired
  ProductNameController productController;

  /**
   * object to have access CatalogUtil methods.
   */
  @MockBean
  CatalogDataUtil catalogCacheUtil;

  /**
   * object to have access ProductNameService methods.
   */
  @Autowired
  ProductNameService productNameService;

  /**
   * object to have access ExternalApi methods.
   */
  @MockBean
  ExternalApiUtil externalApi;

  /**
   * this method is a test of service class.
   *
   * @throws ResourceAccessException - It will be thrown if external API call is failed.
   * @throws ApiFactoryFallBackException - - It will be thrown if external API call is failed.
   */


  @Test
  public void testRetrieveProductNameWithProductNameAsResult()
      throws ResourceAccessException, ApiFactoryFallBackException {



      BinInfo binInfo = new BinInfo();
      binInfo.setBin("123456789");
      binInfo.setInstrumentNo("22");
      binInfo.setProductNo("3333");
      given(externalApi.getBinInfo("123456789")).willReturn(binInfo);
      given(catalogCacheUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo()))
          .willReturn("01|ABC|ProductName111|ProductName111|ABC|ABC");
      given(catalogCacheUtil.getProductName("01|ABC|ProductName111|ProductName111|ABC|ABC"))
          .willReturn("ProductName111");

      String productName = productNameService.retrieveProductName("123456789");

      assertThat(productName).isEqualTo("ProductName111");

  }

  @Test
  public void testRetrieveProductNameFail()
      throws ResourceAccessException, ApiFactoryFallBackException, IOException {

    try {
      given(externalApi.getBinInfo("123456789")).willThrow(new RestClientException(""));

      productNameService.retrieveProductName("123456789");

      Assert.fail();

    } catch (Exception exception) {

    }
  }

  @Test
  public void testRetrieveProductNameFailIOException()
      throws ResourceAccessException, ApiFactoryFallBackException, IOException {

    BinInfo binInfo = new BinInfo();
    binInfo.setBin("123456789");
    binInfo.setInstrumentNo("22");
    binInfo.setProductNo("3333");

    try {

      given(externalApi.getBinInfo("123456789")).willReturn(binInfo);
      given(catalogCacheUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo()))
      .willThrow(new IOException());
      productNameService.retrieveProductName("123456789");
      Assert.fail();

    } catch (Exception exception) {

    }
  }
  
  @Test
  public void testFallBack()  {
    ProductNameServiceImpl productNameServiceImpl = new ProductNameServiceImpl();
    
    try {
      productNameServiceImpl.fallbackRetrieveProductName("");
    } catch (ApiFactoryFallBackException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }



}
