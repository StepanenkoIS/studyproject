package ru.credit.calculator.service.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

public class YearMonthAdaptor extends XmlAdapter<String, YearMonth> {

  @Override
  public YearMonth unmarshal(String str) throws Exception {
    try {
      return YearMonth.parse(str);
    } catch (DateTimeParseException exception) {
      System.out.println("date format error");
      return YearMonth.now();
    }
  }

  @Override
  public String marshal(YearMonth date) throws Exception {
    return date.toString();
  }
}
