package de.fhkiel.aem;

public class Utility {
    public static void help() {
        StringBuilder sb = new StringBuilder();
        sb.append("Geben Sie \"X+X\" ein, um zwei Zahlen zu addieren.\n");
        sb.append("Geben Sie \"X-X\" ein, um zwei Zahlen zu subtrahieren.\n");
        sb.append("Geben Sie \"X/X\" ein, um zwei Zahlen zu dividieren.\n");
        sb.append("Geben Sie \"X*X\" ein, um zwei Zahlen zu multiplizieren.\n");
        sb.append("Geben Sie \"off\" ein, um den Rechner zu beenden.\n");
        System.out.println(sb);
    }
}
