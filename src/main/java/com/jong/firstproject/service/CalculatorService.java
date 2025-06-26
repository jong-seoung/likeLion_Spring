package com.jong.firstproject.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public double calculator(double num1, double num2, String operation) {
        switch (operation) {
            case "add":
                return num1 + num2;
            case "subtract":
                return num1 - num2;
            case "multiply":
                return num1 * num2;
            case "divide":
                if (num2 == 0) {
                    throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("허용 되지 않은 연산자입니다." + operation);
        }
    }
}
