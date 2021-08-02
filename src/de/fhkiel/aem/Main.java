package de.fhkiel.aem;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Gib eine Rechnung (Bsp. X+X) ein oder \"-help\".");
            input = scanner.next();
            if(input.equals("-help")) {
                Utility.help();
            }

        } while (!input.equals("off"));

        scanner.close();
        System.out.println("Taschenrechner beendet.");
    }
}

