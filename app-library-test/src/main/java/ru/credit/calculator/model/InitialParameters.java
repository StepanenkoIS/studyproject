package ru.credit.calculator.model;

import ru.credit.calculator.service.model.YearMonthAdaptor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.Objects;


@XmlRootElement(name = "InitialParameters")
@XmlType(propOrder = {"modelCredit", "loanSum", "numberOfPeriods", "interestRate", "date"})
@XmlAccessorType(XmlAccessType.FIELD)
public class InitialParameters {

  private String modelCredit;
  private BigDecimal loanSum; //первоначальная сумма кредита
  private BigDecimal numberOfPeriods; //количество пеиодов (месяцев)
  private BigDecimal interestRate; //годовая процентная ставка
  @XmlJavaTypeAdapter(YearMonthAdaptor.class)
  private YearMonth date;

  public String getModelCredit() {
    return modelCredit;
  }

  public InitialParameters setModelCredit(String modelCredit) {
    this.modelCredit = modelCredit;
    return this;
  }

  public BigDecimal getLoanSum() {
    return loanSum;
  }

  public InitialParameters setLoanSum(Double loanSum) {
    BigDecimal decimal = new BigDecimal(loanSum);
    this.loanSum = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public BigDecimal getNumberOfPeriods() {
    return numberOfPeriods;
  }

  public InitialParameters setNumberOfPeriods(int numberOfPeriods) {
    BigDecimal decimal = new BigDecimal(numberOfPeriods);
    this.numberOfPeriods = decimal.setScale(0, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public InitialParameters setInterestRate(Double interestRate) {
    BigDecimal decimal = new BigDecimal(interestRate);
    this.interestRate = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public YearMonth getDate() {
    return date;
  }

  public InitialParameters setDate(YearMonth date) {
    this.date = date;
    return this;
  }


  @Override
  public String toString() {
    return "InitialParameters{" +
        "modelCredit='" + modelCredit + '\'' +
        ", loanSum=" + loanSum +
        ", numberOfPeriods=" + numberOfPeriods +
        ", interestRate=" + interestRate +
        ", date=" + date +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InitialParameters that = (InitialParameters) o;
    if (this.loanSum.compareTo(that.loanSum) == 0
        && this.numberOfPeriods.compareTo(that.numberOfPeriods) == 0
        && this.interestRate.compareTo(that.interestRate) == 0
        && Objects.equals(modelCredit, that.modelCredit)
        &&
        Objects.equals(date, that.date)) {
      return true;
    } else {
      return false;
    }

  }

  @Override
  public int hashCode() {
    return Objects.hash(modelCredit, loanSum, numberOfPeriods, interestRate, date);
  }
}
