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
import com.citibanamex.mafcs.customercatalog.service.TypeOfPersonService;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.Customer;
import com.citibanamex.mafcs.customercatalog.viewmodel.persontype.PersonTypeResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TypeOfPersonController.class)
@ContextConfiguration(classes = MockServletContext.class)
public class TypeOfPersonControllerTest {

  @InjectMocks
  private TypeOfPersonController typeOfPersonController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TypeOfPersonService typeOfPersonService;

  private PersonTypeResponse personTypeResponse;

  @Before
  public void setUp() {

    Customer customer = new Customer();
    List<Customer> personType = new ArrayList<Customer>();
    personTypeResponse = new PersonTypeResponse();

    mockMvc = MockMvcBuilders.standaloneSetup(typeOfPersonController).build();
    MockitoAnnotations.initMocks(this);

    customer.setResidencyStatusDetail("FISICA NACIONAL EN EL PAIS");
    customer.setResidencyStatusDetailCode(1);
    personType.add(customer);
    personTypeResponse.setCustomer(personType);
  }

  @Test
  public void testTypeOfPersonResponse() throws Exception {

    Mockito.when(typeOfPersonService.getTypeOfPerson()).thenReturn(personTypeResponse);
    this.mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/private/v1/consumer-services/catalogs/customers/resident/status"))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

  }

  @After
  public void cleanUp() {

  }
}
