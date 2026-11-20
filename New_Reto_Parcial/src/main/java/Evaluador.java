/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.List;
/**
 *
 * @author Juan Tapia
 */
public class Evaluador {
     private static double aplicar(double izq, double der, String operador) {
        switch (operador) {
            case "+": return izq + der;
            case "-": return izq - der;
            case "*": return izq * der;
            case "/": return izq / der;
            case "^": return Math.pow(izq, der);
            default: throw new IllegalArgumentException("Operador no soportado: " + operador);
        }
    }
 
    private static boolean esOperador(String token) {
        return token.length() == 1 && "+-*/^".indexOf(token.charAt(0)) >= 0;
    }
 
    // Evalúa una lista de tokens en posfija (se recorre de izquierda a derecha)
    public static double evaluarPosfija(List<String> tokens, boolean verbose) {
        PilaDinamica<Double> pila = new PilaDinamica<>();
        if (verbose) imprimirEncabezadoTraza();
 
        for (String token : tokens) {
            String accion;
            if (esOperador(token)) {
                double der = pila.desapilar();
                double izq = pila.desapilar();
                double resultado = aplicar(izq, der, token);
                pila.apilar(resultado);
                accion = String.format("pop %s, pop %s -> %s%s%s", fmt(der), fmt(izq), fmt(izq), token, fmt(der));
            } else {
                pila.apilar(Double.parseDouble(token));
                accion = "push";
            }
            if (verbose) imprimirFilaTraza(token, accion, pila.toString());
        }
        if (verbose) imprimirPieTraza();
        return pila.tope();
    }
 
    // Evalúa una lista de tokens en prefija (se recorre de derecha a izquierda)
    public static double evaluarPrefija(List<String> tokens, boolean verbose) {
        PilaDinamica<Double> pila = new PilaDinamica<>();
        if (verbose) imprimirEncabezadoTraza();
 
        for (int i = tokens.size() - 1; i >= 0; i--) {
            String token = tokens.get(i);
            String accion;
            if (esOperador(token)) {
                double izq = pila.desapilar(); // el que se apiló último queda como operando izquierdo
                double der = pila.desapilar();
                double resultado = aplicar(izq, der, token);
                pila.apilar(resultado);
                accion = String.format("pop %s, pop %s -> %s%s%s", fmt(izq), fmt(der), fmt(izq), token, fmt(der));
            } else {
                pila.apilar(Double.parseDouble(token));
                accion = "push";
            }
            if (verbose) imprimirFilaTraza(token, accion, pila.toString());
        }
        if (verbose) imprimirPieTraza();
        return pila.tope();
    }
 
    private static String fmt(double d) {
        return (d == Math.floor(d)) ? String.valueOf((long) d) : String.valueOf(d);
    }
 
    private static void imprimirEncabezadoTraza() {
        System.out.println("  ┌────────────┬────────────────────────────┬────────────────────┐");
        System.out.printf("  │ %-10s │ %-26s │ %-18s │%n", "Token", "Acción", "Pila");
        System.out.println("  ├────────────┼────────────────────────────┼────────────────────┤");
    }
 
    private static void imprimirFilaTraza(String token, String accion, String pila) {
        System.out.printf("  │ %-10s │ %-26s │ %-18s │%n", token, accion, pila);
    }
 
    private static void imprimirPieTraza() {
        System.out.println("  └────────────┴────────────────────────────┴────────────────────┘");
    }
}
