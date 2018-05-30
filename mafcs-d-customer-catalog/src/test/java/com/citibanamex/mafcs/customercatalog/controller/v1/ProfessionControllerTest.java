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
import com.citibanamex.mafcs.customercatalog.service.ProfessionService;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.Profession;
import com.citibanamex.mafcs.customercatalog.viewmodel.profession.ProfessionResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProfessionController.class)
@ContextConfiguration(classes = MockServletContext.class)
public class ProfessionControllerTest {

  @InjectMocks
  private ProfessionController professionController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProfessionService professionService;

  private ProfessionResponse professionResponse;

  @Before
  public void setUp() {

    professionResponse = new ProfessionResponse();
    Profession profession = new Profession();
    List<Profession> professionList = new ArrayList<Profession>();
    mockMvc = MockMvcBuilders.standaloneSetup(professionController).build();
    MockitoAnnotations.initMocks(this);
    profession.setProfessionName("CHOFER EMPLEADO SECTOR PRIVADO");
    profession.setProfessionCode(29);
    professionList.add(profession);
    professionResponse.setProfession(professionList);
  }

  @Test
  public void testTypeOfPersonResponseAll() throws Exception {

    String professionFilter = " ";
    Mockito.when(professionService.getProfession(Mockito.any())).thenReturn(professionResponse);
    this.mockMvc.perform(MockMvcRequestBuilders
        .get("/api/private/v1/consumer-services/catalogs/customers/profession")
        .param("professionFilter", professionFilter).contentType(MediaType.APPLICATION_JSON_UTF8)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

  }

  @Test
  public void testTypeOfPersonResponseFind() throws Exception {

    String professionFilter = "emp";
    Mockito.when(professionService.getProfession(Mockito.any())).thenReturn(professionResponse);
    this.mockMvc.perform(MockMvcRequestBuilders
        .get("/api/private/v1/consumer-services/catalogs/customers/profession")
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
