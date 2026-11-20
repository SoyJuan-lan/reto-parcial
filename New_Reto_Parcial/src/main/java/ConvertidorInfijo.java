/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Juan Tapia
 */
public class ConvertidorInfijo {
    private static int precedencia(String token) {
        switch (token) {
            case "^": return 3;
            case "*": case "/": return 2;
            case "+": case "-": return 1;
            default: return -1;
        }
    }
 
    private static boolean esAsociativoDerecha(String token) {
        return token.equals("^");
    }
 
    private static boolean esOperador(String token) {
        return precedencia(token) != -1;
    }
 
    // tokensInfija expresión infija ya separada en tokens (números, operadores, paréntesis)
    public static List<String> aPosfija(String[] tokensInfija) {
        List<String> salida = new ArrayList<>();
        PilaDinamica<String> operadores = new PilaDinamica<>();
 
        for (String token : tokensInfija) {
            if (token.equals("(")) {
                operadores.apilar(token);
 
            } else if (token.equals(")")) {
                while (!operadores.estaVacia() && !operadores.tope().equals("(")) {
                    salida.add(operadores.desapilar());
                }
                if (!operadores.estaVacia()) {
                    operadores.desapilar();             // descarta el "(" que hace pareja en el trabajo manuel no esta asi pero lo quiero especificar aqui
                }
 
            } else if (esOperador(token)) {
                while (!operadores.estaVacia() && esOperador(operadores.tope())) {
                    String cima = operadores.tope();
                    boolean debeSalir = precedencia(cima) > precedencia(token)
                            || (precedencia(cima) == precedencia(token) && !esAsociativoDerecha(token));
                    if (!debeSalir) break;
                    salida.add(operadores.desapilar());
                }
                operadores.apilar(token);
 
            } else {
                                      // token numérico: va directo a la salida
                salida.add(token);
            }
        }
 
        while (!operadores.estaVacia()) {
            salida.add(operadores.desapilar());
        }
        return salida;
    }
}
