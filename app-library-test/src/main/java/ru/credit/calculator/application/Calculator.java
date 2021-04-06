package ru.credit.calculator.application;

import ru.credit.calculator.model.Credit;
import ru.credit.calculator.model.InitialParameters;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

  public static List<Credit> calculateSchedule(InitialParameters parameters) {
    if ("differentiated".equals(parameters.getModelCredit())) {
      return calculationDifferentiated(parameters);
    }
    if ("authentic".equals(parameters.getModelCredit())) {
      return calculationAuthentic(parameters);
    } else {
      return null;
    }
  }

  /*
DP = (S/n) + OD * ( i * ЧдП / ЧдГ )
  Где
DP- дифференцированный платеж
S — первоначальная сумма кредита
OD — остаток долга по кредиту на дату расчета ДП
i — годовая процентная ставка в абсолютном выражении (0.07 для 7%)
k — количество процентных периодов в году
n — количество процентных периодов во всем сроке кредита
ЧдП — число дней в платежном периоде. Обычно это разность дат между двумя платежами по графику.
Обычно она находится в диапазоне 28-31 день в зависимости от числа дней в месяце.
Число дней между двумя платежами — это период, за который начисляются проценты.
ЧдГ — число дней в текущем году(может быть 365 или 366 в зависимости от того, високосный год или нет).
Первое слагаемое S/n — это сумма, которая идет в погашение основного долга по кредиту каждый процентный период (как правило, месяц).
  */
  private static List<Credit> calculationDifferentiated(InitialParameters parameters) {
    List<Credit> credits = new ArrayList<Credit>();

    BigDecimal loanSum = parameters.getLoanSum(); //остаток кредита в данном месяце
    BigDecimal repayment = loanSum.divide(parameters.getNumberOfPeriods(), 2, BigDecimal.ROUND_HALF_UP); //возврат основного долга
    YearMonth month = parameters.getDate();

    for (int i = 1; i < parameters.getNumberOfPeriods().intValue() + 1; i++) {
      month = month.plusMonths(1);

      Credit credit = new Credit()
          .setIndex(i)
          .setRepayment(repayment)
          .setOutstandingBalance(loanSum)
          .setDate(month.toString());

      // начисленные проценты
      BigDecimal accruedInterest = loanSum.multiply(
          (parameters.getInterestRate().divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP))
              .multiply(new BigDecimal(month.lengthOfMonth()))
              .divide(new BigDecimal(month.lengthOfYear()), 10, BigDecimal.ROUND_HALF_UP));
      loanSum = loanSum.subtract(repayment);

      if (i == (parameters.getNumberOfPeriods().intValue())) {
        credit.setRepaymentRate(accruedInterest)
            .setPayment(accruedInterest.add(repayment).add(loanSum))
            .setRepayment(calculationLastRepayment(credits, parameters.getLoanSum()));
      } else {
        credit.setRepaymentRate(accruedInterest).setPayment(accruedInterest.add(repayment));
      }
      credits.add(credit);
    }
    return credits;
  }

  /*
  https://temabiz.com/finterminy/ap-formula-i-raschet-annuitetnogo-platezha.html

  Формула расчета аннуитетных платежей:
  P = S * (i + (i / ((1 + i)^n - 1))), где
  P – ежемесячный платёж по аннуитетному кредиту (аннуитетный платёж, не изменяется в течение всего периода погашения кредита);
  S – сумма кредита;
  i – ежемесячная процентная ставка (рассчитывается по следующей формуле: годовая процентная ставка/100/12);
  n – срок, на который берётся кредит (указывается количество месяцев).

  Расчёт процентов по аннуитетным платежам:
  In = Sn * i, где
  In – сумма в аннуитетном платеже, которая идёт на погашение процентов по кредиту;
  Sn – сумма оставшейся задолженности по кредиту (остаток по кредиту);
  i – ежемесячная процентная ставка;

  Расчёт доли тела кредита в аннуитетных платежах:
  S = P - In, где
  S – сумма в аннуитетном платеже, которая идёт на погашение тела кредита;
  P – ежемесячный аннуитетный платёж;
  In – сумма в аннуитетном платеже, которая идёт на погашение процентов по кредиту.

  Рассчет долга на конец месяца:
  Sn2 = Sn1 - S, где
  Sn2 – долг на конец месяца по аннуитетному кредиту;
  Sn1 – сумма текущей задолженности по кредиту;
  S – сумма в аннуитетном платеже, которая идёт на погашение тела кредита.

  */

  private static List<Credit> calculationAuthentic(InitialParameters parameters) {
    List<Credit> credits = new ArrayList<Credit>();

    double payment; //сумма платежа
    double repayment; //сумма на погашение основного долга
    double repaymentRate; //сумма на погашение процентов
    double outstandingBalance; //остаток задолженности
    double loanSum = parameters.getLoanSum().doubleValue(); //первоначальная сумма кредита
    double rateMonth = parameters.getInterestRate().doubleValue() / 100 / 12;
    double numberOfPeriods = parameters.getNumberOfPeriods().doubleValue(); //количество пеиодов (месяцев)
    YearMonth month = parameters.getDate();

    payment = loanSum * (rateMonth + (rateMonth / (Math.pow((1 + rateMonth), numberOfPeriods) - 1)));
    repayment = loanSum;

    for (int i = 1; i < numberOfPeriods + 1; i++) {
      Credit credit = new Credit().setOutstandingBalance(new BigDecimal((repayment)));
      month = month.plusMonths(1);
      repaymentRate = repayment * rateMonth;
      repayment = repayment - payment + repaymentRate;

      if (i == (parameters.getNumberOfPeriods().intValue())) {
        credit.setRepayment(calculationLastRepayment(credits, parameters.getLoanSum()));
      } else {
        credit.setRepayment(new BigDecimal(payment - repaymentRate));
      }
      credits.add(credit.setIndex(i)
          .setDate(month.toString())
          .setPayment(new BigDecimal(payment))
          .setRepaymentRate(new BigDecimal(repaymentRate)));

    }
    return credits;

  }

  //расчет Repayment - последнего платежа
  private static BigDecimal calculationLastRepayment(List<Credit> credits, BigDecimal loanSum) {
    BigDecimal decimal = new BigDecimal("0.00").setScale(2, BigDecimal.ROUND_HALF_UP);
    for (Credit credit : credits) {
      decimal = decimal.add(credit.getRepayment());
    }
    return loanSum.subtract(decimal);
  }
}
