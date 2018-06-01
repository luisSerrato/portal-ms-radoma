package com.citibanamex.mafcs.customercatalog.service.impl;

import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.service.TypeOfPersonService;
import com.citibanamex.mafcs.customercatalog.util.C080HeraFormatter;
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
public class TypeOfPersonServiceImplTest {

  @Configuration
  static class TypeOfPersonServiceImplTestConfiguration {
      
      @Bean
      public TypeOfPersonService typeOfPersonService() {
          return new TypeOfPersonServiceImpl();
      }
      
      @Bean
      public C080HeraFormatter heraFormatter(){
        return new C080HeraFormatter();
      }
  }
  
  @Autowired
  private TypeOfPersonService typeOfPersonService;
  
  @MockBean
  private C080Client c080Client;
  
  private Map<String, Object> mapResponse;
 
  @Test
  public void testGetTypeOfPerson(){
    mapResponse = new HashMap<>();

    mapResponse.put("success", true);
    mapResponse.put("datos", "{\"datos\":[[\"3\",\"FISICA EMPRESARIAL NACIONAL EN EL PAIS\"],"
        + "[\"7\",\"FISICA EXTRANJERA RADICADA EN EL PAIS\"],[\"1\","
        + "\"FISICA NACIONAL EN EL PAIS\"]],\"campos\":{\"PERSONA\":0,\"D_LARGA\":1}}");
    
    Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);

    
    typeOfPersonService.getTypeOfPerson();
  }
  
}
