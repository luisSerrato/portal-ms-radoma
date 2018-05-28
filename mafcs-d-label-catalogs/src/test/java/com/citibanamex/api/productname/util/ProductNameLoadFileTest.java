package com.citibanamex.api.productname.util;

import static org.junit.Assert.assertEquals;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ProductNameLoadFileTest {

  @Autowired
  private ProductNameLoadFile productNameLoadFile;

  @Test
  public void getFileTestFail() {

    XSSFSheet sheet =  productNameLoadFile.getFile("anypath");
    assertEquals(null,sheet);


  }

}
