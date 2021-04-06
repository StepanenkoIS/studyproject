package ru.is.studyproject.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.credit.calculator.model.Credit;
import ru.credit.calculator.model.InitialParameters;

import java.util.List;

import static ru.credit.calculator.application.Calculator.calculateSchedule;

@Component
public class SimpleRunCalculator implements RunCalculator{

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    @Override
    @Cacheable("parameters")
    public List<Credit> runCalculator (InitialParameters parameters) {
        logger.info("Получены параметры для рассчета: " + parameters.toString());

        return calculateSchedule(parameters);
    }

}
