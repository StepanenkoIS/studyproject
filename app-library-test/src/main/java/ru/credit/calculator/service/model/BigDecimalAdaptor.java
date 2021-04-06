package ru.credit.calculator.service.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class BigDecimalAdaptor extends XmlAdapter<String, BigDecimal> {


  @Override
  public BigDecimal unmarshal(String str) throws Exception {
    try {
      return new BigDecimal(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public String marshal(BigDecimal decimal) throws Exception {
    if (decimal != null){
      return decimal.toString();
    }
    else {
      return null;
    }
  }
}
