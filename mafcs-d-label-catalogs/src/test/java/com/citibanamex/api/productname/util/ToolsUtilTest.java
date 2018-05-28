package com.citibanamex.api.productname.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ToolsUtilTest {

  @Test
  public void getObjectAsJsonTestFail() {

      String jsonString= ToolsUtil.getObjectAsJson(new Object());
      assertEquals(null,jsonString);
  }

}
