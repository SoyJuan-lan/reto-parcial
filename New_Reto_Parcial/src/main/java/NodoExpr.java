/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Tapia
 */
public class NodoExpr {
    private static final String OPERADORES = "+-*/^";
 
    public final String simbolo;
    public NodoExpr izquierdo;
    public NodoExpr derecho;
 
    public NodoExpr(String simbolo) {
        this.simbolo = simbolo;
    }
 
    // true si este nodo guarda un operador (y por lo tanto es un nodo interno)
    public boolean esOperador() {
        return simbolo.length() == 1 && OPERADORES.indexOf(simbolo.charAt(0)) >= 0;
    }
 
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
 
    @Override
    public String toString() {
        return simbolo;
    }
}
