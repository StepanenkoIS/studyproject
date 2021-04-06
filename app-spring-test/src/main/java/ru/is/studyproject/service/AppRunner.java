package ru.is.studyproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.credit.calculator.model.Credit;
import ru.credit.calculator.model.InitialParameters;

import java.time.YearMonth;
import java.util.List;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final RunCalculator calculator;

    public AppRunner(RunCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Новый расчет кредита");
        InitialParameters parameters1 = new InitialParameters();
        parameters1.setModelCredit("authentic")
                .setLoanSum(10_000.00)
                .setNumberOfPeriods(5)
                .setInterestRate(12.8)
                .setDate(YearMonth.of(2021,8));
        print(calculator.runCalculator(parameters1));

        logger.info("Новый расчет кредита");
        InitialParameters parameters2 = new InitialParameters();
        parameters2.setModelCredit("authentic")
                .setLoanSum(10_000.00)
                .setNumberOfPeriods(5)
                .setInterestRate(12.8)
                .setDate(YearMonth.of(2021,8));
        print(calculator.runCalculator(parameters2));

        logger.info("Новый расчет кредита");
        InitialParameters parameters3 = new InitialParameters();
        parameters3.setModelCredit("differentiated")
                .setLoanSum(10_000.00)
                .setNumberOfPeriods(5)
                .setInterestRate(12.8)
                .setDate(YearMonth.of(2021,8));
        print(calculator.runCalculator(parameters3));
    }

    private void print(List<Credit> credits) {
        for (Credit credit:credits) {
            logger.info(credit.toString());
        }
    }

}