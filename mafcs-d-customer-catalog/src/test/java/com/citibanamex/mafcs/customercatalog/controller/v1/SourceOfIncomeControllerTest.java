package com.citibanamex.mafcs.customercatalog.controller.v1;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.citibanamex.mafcs.customercatalog.service.SourceOfIncomeService;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.IncomeSource;
import com.citibanamex.mafcs.customercatalog.viewmodel.sourceofincome.SourceOfIncomeResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SourceOfIncomeController.class)
@ContextConfiguration(classes = MockServletContext.class)
public class SourceOfIncomeControllerTest {

  @InjectMocks
  private SourceOfIncomeController sourceOfIncomeController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SourceOfIncomeService sourceOfIncomeService;

  private SourceOfIncomeResponse sourceOfIncomeResponse;

  @Before
  public void setUp() {

    sourceOfIncomeResponse = new SourceOfIncomeResponse();
    IncomeSource incomeSource = new IncomeSource();
    List<IncomeSource> income = new ArrayList<IncomeSource>();

    mockMvc = MockMvcBuilders.standaloneSetup(sourceOfIncomeController).build();
    MockitoAnnotations.initMocks(this);

    incomeSource.setIncomeSource("COMISIONISTA / VENDEDOR");
    incomeSource.setIncomeSourceCode(8);
    income.add(incomeSource);
    sourceOfIncomeResponse.setIncome(income);
  }

  @Test
  public void testTypeOfPersonResponse() throws Exception {

    Mockito.when(sourceOfIncomeService.getSourceOfIncome()).thenReturn(sourceOfIncomeResponse);
    this.mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/private/v1/consumer-services/catalogs/customers/income/source"))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

  }

  @After
  public void cleanUp() {

  }
}
