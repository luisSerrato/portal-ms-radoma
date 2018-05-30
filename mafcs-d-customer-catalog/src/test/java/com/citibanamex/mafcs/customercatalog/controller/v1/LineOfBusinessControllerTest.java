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
import com.citibanamex.mafcs.customercatalog.service.LineOfBusinessService;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusiness;
import com.citibanamex.mafcs.customercatalog.viewmodel.lineofbusiness.LineofBusinessResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProfessionController.class)
@ContextConfiguration(classes = MockServletContext.class)
public class LineOfBusinessControllerTest {

  @InjectMocks
  private LineOfBusinessController lineOfBusinessController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LineOfBusinessService lineOfBusinessService;

  private LineofBusinessResponse lineOfBusinessResponse;

  @Before
  public void setUp() {

    lineOfBusinessResponse = new LineofBusinessResponse();
    LineofBusiness profession = new LineofBusiness();
    List<LineofBusiness> lineofBusinessList = new ArrayList<LineofBusiness>();
    mockMvc = MockMvcBuilders.standaloneSetup(lineOfBusinessController).build();
    MockitoAnnotations.initMocks(this);
    profession.setOccupationSector("AVIONES, COMERCIO MAYORISTA");
    profession.setOccupationSectorCode(4);
    lineofBusinessList.add(profession);
    lineOfBusinessResponse.setLineofBusiness(lineofBusinessList);
  }

  @Test
  public void testTypeOfPersonResponseAll() throws Exception {

    String professionFilter = " ";
    Mockito.when(lineOfBusinessService.getLineofBusiness(Mockito.any()))
        .thenReturn(lineOfBusinessResponse);
    this.mockMvc.perform(MockMvcRequestBuilders
        .get("/api/private/v1/consumer-services/catalogs/customers/occupation/sector")
        .param("professionFilter", professionFilter).contentType(MediaType.APPLICATION_JSON_UTF8)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

  }

  @Test
  public void testTypeOfPersonResponseFind() throws Exception {

    String professionFilter = "emp";
    Mockito.when(lineOfBusinessService.getLineofBusiness(Mockito.any()))
        .thenReturn(lineOfBusinessResponse);
    this.mockMvc.perform(MockMvcRequestBuilders
        .get("/api/private/v1/consumer-services/catalogs/customers/occupation/sector")
        .param("professionFilter", professionFilter).contentType(MediaType.APPLICATION_JSON_UTF8)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

  }

  @After
  public void cleanUp() {

  }
}
