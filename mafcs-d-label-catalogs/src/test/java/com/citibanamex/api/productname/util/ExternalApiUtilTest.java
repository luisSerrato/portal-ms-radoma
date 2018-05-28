package com.citibanamex.api.productname.util;

import com.citibanamex.api.productname.controller.ProductNameControllerTest;

import com.citibanamex.api.productname.model.BinInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


/**
 * This class implements a method that test a external API call.
 *
 * @author abraham.hernandez
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ExternalApiUtilTest {


  /**
   * productNameProperties object to have access properties file.
   */
  @Autowired
  ProductNameProperties productNamesProperties;

  /**
   * RestTemplate object.
   */
  @Autowired
  RestTemplate restTemplate;

  @MockBean
  RestTemplate restTemp;
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductNameControllerTest.class);



  /**
   * Headers required to external api call.
   */
  private HttpHeaders headers;

  /**
   * ExternalApiUtil object to have access ExternalApiUtil methods.
   */
  @Autowired
  private ExternalApiUtil externalApiUtil;

  /**
   * External API Url.
   */
  private String externalApiUrl;


  

  /**
   *
   */
  @Test
  public void testFailRetrieveExternalApiFails() {

      try {

        when(restTemp.exchange(
            Matchers.anyString(),
            Matchers.any(HttpMethod.class),
            Matchers.<HttpEntity<?>> any(),
            Matchers.<Class<BinInfo>> any())).thenThrow(new RestClientException(""));
        externalApiUtil.getBinInfo("123456789");
        Assert.fail();

      } catch(Exception exc) {

      }
  }

  /**
   *
   */
  @Test
  public void testFailRetrieveExternalApiSuccess() {

    BinInfo binInf = new BinInfo();
    binInf.setBin("123456789");
    binInf.setInstrumentNo("22");
    binInf.setProductNo("3333");

    ResponseEntity<BinInfo> responseEntity = new ResponseEntity<BinInfo>(binInf, HttpStatus.OK);

        when(restTemp.exchange(
            Matchers.anyString(),
            Matchers.any(HttpMethod.class),
            Matchers.<HttpEntity<?>> any(),
            Matchers.<Class<BinInfo>> any())).thenReturn(responseEntity);

        BinInfo binInfo = externalApiUtil.getBinInfo("123456789");
        assertThat(binInfo.getBin()).isEqualTo(responseEntity.getBody().getBin());
  }


}
