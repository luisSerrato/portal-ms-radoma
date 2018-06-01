package com.citibanamex.mafcs.customercatalog.service.impl;

import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.errorhandling.ErrorResolver;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.ValidationException;
import com.citibanamex.mafcs.customercatalog.service.LineOfBusinessService;
import com.citibanamex.mafcs.customercatalog.util.C080HeraFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class LineOfBusinessServiceImplTest {

  
  @Configuration
  static class LineOfBusinessServiceImplTestConfiguration {
      
      @Bean
      public LineOfBusinessService lineOfBusinessService() {
          return new LineOfBusinessServiceImpl();
      }
      
      @Bean
      public C080HeraFormatter heraFormatter(){
        return new C080HeraFormatter();
      }
  }
  
  
  
  
  @Autowired
  private LineOfBusinessService lineOfBusinessService;
 
  @MockBean
  private C080Client c080Client;
  
  private Map<String, Object> mapResponse;
  
  @Before
  public void setUp(){
     mapResponse = new HashMap<>();
     
     mapResponse.put("success", true);
     mapResponse.put("datos", "{\"datos\":[[\"2150\",\"ABARROTES Y ULTRAMARINOS, "
         + "COMERCIO MINORISTA\"],[\"2085\",\"ABARROTES, COMERCIO MAYORISTA\"],[\"876\",\"ABEJA / "
         + "MIEL DE, ENVASADO\"],[\"3005\","
         + "\"ABOGADOS, DESPACHO\"]],\"campos\":{\"DESCRIP\":1,\"PALABRA\":0}}");
     
     Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);


  }
  
  @Test
  public void testGetLineofBusiness(){    
    lineOfBusinessService.getLineofBusiness(new String("test"));
  }
  
  @Test
  public void testGetLineofBusinessFilterEmpty(){
    lineOfBusinessService.getLineofBusiness(new String(""));
  }
    
  @Test
  public void testGetLineofBusinessCamposEmpty(){ 
    try{ 
      Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(new Object());
      lineOfBusinessService.getLineofBusiness(new String("test"));
    }catch (ValidationException ex) {
      ErrorResolver resolver = new ErrorResolver();
      resolver.resolveValidationException(null, ex);
    }
  }

  @Test
  public void testGetLineofBusinessDataNotFound(){ 
    
    try{
      mapResponse = new HashMap<>();
      
      Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);
      lineOfBusinessService.getLineofBusiness(new String("test"));
    }catch (Exception ex) {
      ErrorResolver resolver = new ErrorResolver();
      resolver.resolveException(null, ex);
    }
    
  }
  
  @Test(expected = ValidationException.class)
  public void testGetLineofBusinessSuccessFlag(){ 
    
      mapResponse = new HashMap<>();
      
      mapResponse.put("success", false);
      
      Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);
      lineOfBusinessService.getLineofBusiness(new String("test"));    
  }
  
  
}
