package com.citibanamex.mafcs.customercatalog.service.impl;

import com.citibanamex.mafcs.customercatalog.c080client.C080Client;
import com.citibanamex.mafcs.customercatalog.errorhandling.ErrorResolver;
import com.citibanamex.mafcs.customercatalog.errorhandling.exception.CcC080CustomerClientException;
import com.citibanamex.mafcs.customercatalog.service.SourceOfIncomeService;
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
public class SourceOfIncomeServiceImplTest {
  
  @Configuration
  static class SourceOfIncomeServiceImplTestConfiguration {
      
      @Bean
      public SourceOfIncomeService sourceOfIncomeService() {
          return new SourceOfIncomeServiceImpl();
      }
      
      @Bean
      public C080HeraFormatter heraFormatter(){
        return new C080HeraFormatter();
      }
  }
  
  @Autowired
  private SourceOfIncomeService sourceOfIncomeService;
  
  @MockBean
  private C080Client c080Client;
  
  private Map<String, Object> mapResponse;
  
  @Test
  public void testGetSourceOfIncome(){
    
    mapResponse = new HashMap<>();

    mapResponse.put("success", true);
    mapResponse.put("datos", "{\"datos\":[[\"13\",\"COMISIONISTA / VENDEDOR\"],"
        + "[\"1\",\"EMPLEADO\"],[\"6\",\"EMPLEADOS SECTOR PRIVADO\"],"
        + "[\"2\",\"NEGOCIO PROPIO / DUEÑO\"],[\"4\",\"PROFESIONISTAS INDEPENDIENTES\"]],"
        + "\"campos\":{\"OCUPACI\":0,\"D_LARGA\":1}}");
    
    Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(mapResponse);
    sourceOfIncomeService.getSourceOfIncome();
    
  }
  
  @Test
  public void testGetSourceOfIncomeCcException(){    
    try{
      Mockito.when(c080Client.getInformationC080(Mockito.any())).thenReturn(null);
      sourceOfIncomeService.getSourceOfIncome();
    }catch (CcC080CustomerClientException ex) {
      ErrorResolver resolver = new ErrorResolver();
      resolver.resolveCcC080AddressClientException(null, ex);
    }
    
  }

}
