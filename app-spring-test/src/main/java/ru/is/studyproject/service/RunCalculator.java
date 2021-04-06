package ru.is.studyproject.service;

import ru.credit.calculator.model.Credit;
import ru.credit.calculator.model.InitialParameters;

import java.util.List;

public interface RunCalculator {
    List<Credit> runCalculator(InitialParameters parameters);
}
