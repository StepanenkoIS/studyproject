package ru.credit.calculator.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Objects;

@XmlType(propOrder = {"index", "date", "payment", "repayment", "repaymentRate", "outstandingBalance"})
@XmlRootElement(name = "Credit")
@XmlAccessorType(XmlAccessType.FIELD)
public class Credit {

  private int index; //номер платежа
  private String date;  //дата платежа
  private BigDecimal payment; //сумма платежа
  private BigDecimal repayment; //сумма на погашение основного долга
  private BigDecimal repaymentRate; //сумма на погашение процентов
  private BigDecimal outstandingBalance; //остаток задолженности

  public int getIndex() {
    return index;
  }

  public BigDecimal getPayment() {
    return payment;
  }

  public BigDecimal getRepayment() {
    return repayment;
  }

  public BigDecimal getRepaymentRate() {
    return repaymentRate;
  }

  public BigDecimal getOutstandingBalance() {
    return outstandingBalance;
  }

  public String getDate() {
    return date;
  }

  public Credit setDate(String date) {
    this.date = date;
    return this;
  }

  public Credit setIndex(int index) {
    this.index = index;
    return this;
  }

  public Credit setPayment(BigDecimal payment) {
    this.payment = payment.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public Credit setRepayment(BigDecimal repayment) {
    this.repayment = repayment.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public Credit setRepaymentRate(BigDecimal repaymentRate) {
    this.repaymentRate = repaymentRate.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  public Credit setOutstandingBalance(BigDecimal outstandingBalance) {
    this.outstandingBalance = outstandingBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Credit credit = (Credit) o;
    return index == credit.index &&
        Objects.equals(date, credit.date) &&
        Objects.equals(payment, credit.payment) &&
        Objects.equals(repayment, credit.repayment) &&
        Objects.equals(repaymentRate, credit.repaymentRate) &&
        Objects.equals(outstandingBalance, credit.outstandingBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, date, payment, repayment, repaymentRate, outstandingBalance);
  }

  @Override
  public String toString() {
    return "Credit{" +
        "номер платежа=" + index +
        ", сумма платежа=" + payment +
        ", сумма на погашение основного долга=" + repayment +
        ", сумма на погашение процентов=" + repaymentRate +
        ", остаток задолженности=" + outstandingBalance +
        ", дата платежа=" + date +
        '}';
  }
}
