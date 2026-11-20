/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.List;
/**
 *
 * @author Juan Tapia
 */
public class GeneradorArbol {
    private static boolean esOperador(String token) {
        return token.length() == 1 && "+-*/^".indexOf(token.charAt(0)) >= 0;
    }
 
    public static NodoExpr construir(List<String> tokensPosfija) {
        PilaDinamica<NodoExpr> pila = new PilaDinamica<>();
 
        for (String token : tokensPosfija) {
            NodoExpr nodo = new NodoExpr(token);
            if (esOperador(token)) {
                nodo.derecho = pila.desapilar();           // el último apilado es el operando derecho    ejemplo 3 -> [3]   
                nodo.izquierdo = pila.desapilar();         // el anterior es el operando izquierdo                4 -> [3,4]
            }
            pila.apilar(nodo);
        }
        return pila.desapilar();
    }
}
