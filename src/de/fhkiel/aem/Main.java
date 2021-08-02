package de.fhkiel.aem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.out.println("hallo");
    }

    private static double parseInput(String input) {
        Pattern pattern = Pattern.compile(
                "(?<numone>-?[0-9]+[.,]?[0-9]+?\\s?)(?<sign>[+\\-*/])\\s?(?<numtwo>-?[0-9]+[.,]?[0-9]+?)");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new RuntimeException("Die Rechnung konnte nicht erkannt werden!");
        }

        String numOne = matcher.group("numone");
        String numTwo = matcher.group("numtwo");

        String sign = matcher.group("sign");
        double numOneParsed = Double.parseDouble(numOne);
        double numTwoParsed = Double.parseDouble(numTwo);

        double result;

        switch (sign) {
            case "-":
                result = subtraktion(numOneParsed, numTwoParsed);
                break;
            case "+":
                result = addition(numOneParsed, numTwoParsed);
                break;
            case "*":
                result = 0;
                break;
            case "/":
                result = 0;
                break;
            default:
                throw new RuntimeException("Die Rechnung konnte nicht erkannt werden!");
        }

        return result;
    }

    private static double subtraktion(double _minuend, double _subtrahend) {
        return (_minuend - _subtrahend);
    }

    private static double addition(double _summand_1, double _summand_2) {
        return (_summand_1 + _summand_2);

    }
}
