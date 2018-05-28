package com.citibanamex.api.productname.util;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.citibanamex.api.productname.model.BinInfo;



@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CatalogUtilTest {


  @Autowired
  private CatalogDataUtil catalogDataUtil;

  @Test
  public void getProductInfoTest() throws IOException {

    BinInfo binInfo = new BinInfo();
    binInfo.setBin("123456789");
    binInfo.setInstrumentNo("1");
    binInfo.setProductNo("3036");
    String productInfo =catalogDataUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo());
    String productInfoTest = productInfo.substring(0,24);
    assertThat(productInfoTest).isEqualTo("00|MXN|SBA NOMINA SEGURA");

  }

  @Test
  public void getProductNameTest () throws IOException {

    BinInfo binInfo = new BinInfo();
    binInfo.setBin("123456789");
    binInfo.setInstrumentNo("1");
    binInfo.setProductNo("3036");
    String productInfo = catalogDataUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo());
    String productName = catalogDataUtil.getProductName(productInfo);
    assertThat(productName).isEqualTo("SBA NOMINA SEGURA                       ");
  }

  @Test
  public void productInfoTestFails() throws IOException {
    BinInfo binInfo = new BinInfo();
    binInfo.setBin("123456789");
    binInfo.setInstrumentNo("7");
    binInfo.setProductNo("9");
    String productInfo =catalogDataUtil.getProductInfo(binInfo.getProductNo(), binInfo.getInstrumentNo());

    assertThat(productInfo).isEqualTo("");
  }


}
