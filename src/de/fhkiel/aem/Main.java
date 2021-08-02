package de.fhkiel.aem;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Gib eine Rechnung (Bsp. X+X) ein oder \"-help\".");
            input = scanner.nextLine();
            if(input.equals("-help")) {
                Utility.help();
            }
            System.out.println("Ergebnis: "+ parseInput(input));
        } while (!input.equals("off"));

        scanner.close();
        System.out.println("Taschenrechner beendet.");
    }

    private static double parseInput(String input) {

        Pattern pattern = Pattern.compile("(?<numone>-?[0-9]*[.,]?[0-9]+?\\s?)(?<sign>[+\\-*\\/])\\s?(?<numtwo>-?[0-9]*[.,]?[0-9]+?)");

        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new RuntimeException("Die Rechnung konnte nicht erkannt werden!");
        }

        String numOne = matcher.group("numone");
        String sign = matcher.group("sign");
        String numTwo = matcher.group("numtwo");

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
                result = multiplizieren(numOneParsed, numTwoParsed);
                break;
            case "/":
                result = divisoin(numOneParsed, numTwoParsed);
                break;
            default:
                throw new RuntimeException("Die Rechnung konnte nicht erkannt werden!");
        }

        return result;
    }


    private static double divisoin (double first, double second){ return first/second; }

    private static double addition(double _summand_1, double _summand_2) { return (_summand_1 + _summand_2); }

    private static double subtraktion(double _minuend, double _subtrahend) {
        return (_minuend - _subtrahend);
    }

    private static double multiplizieren(double zahlEins, double zahlZwei) {
        return zahlEins * zahlZwei;
    }
}

