package com.citibanamex.mafcs.customercatalog.service.impl;

import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
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
public class ProfessionServiceImplTest {

  @Configuration
  static class ProfessionServiceImplTestConfiguration {
      
      @Bean
      public ProfessionService professionService() {
          return new ProfessionServiceImpl();
      }
      
      @Bean
      public C080HeraFormatter heraFormatter(){
        return new C080HeraFormatter();
      }
  }
  
  
  @Autowired
  private ProfessionService professionService;
  
  @MockBean
  private C080Client c080Client;
  
  private Map<String, Object> mapResponse;

  
  @Before
  public void setUp(){
    
    mapResponse = new HashMap<>();
    
    mapResponse.put("success", true);
    mapResponse.put("datos", "{\"datos\":[[\"65\",\"ALTO FUNCIONARIO MILITAR\"],"
        + "[\"66\",\"AMA DE CASA\"],[\"95\",\"ARRENDAMIENTO DUEÑO\"],"
        + "[\"100\",\"ARTISTA\"],[\"345\",\"CHOFER EMPLEADO SECTOR PRIVADO\"]],"
        + "\"campos\":{\"DESCRIP\":1,\"PALABRA\":0}}");

    Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);
  }
  
  @Test
  public void testGetProfession(){           
    professionService.getProfession("test");
  }
  
  @Test
  public void testGetProfessionFilterEmpty(){
    professionService.getProfession("");
  }
  
  @Test(expected = CcC080CustomerClientException.class)
  public void testGetProfessionCcResponseNull(){
    Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(null);
    professionService.getProfession("test");
  }
}
