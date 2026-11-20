/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Tapia
 */
public class NodoBST {
    public String expresionOriginal;
    public Notacion notacionOrigen;
    public NodoExpr arbolExpresion;
    public double valor;
 
    public NodoBST izquierdo;
    public NodoBST derecho;
 
    public NodoBST(String expresionOriginal, Notacion notacionOrigen, NodoExpr arbolExpresion, double valor) {
        this.expresionOriginal = expresionOriginal;
        this.notacionOrigen = notacionOrigen;
        this.arbolExpresion = arbolExpresion;
        this.valor = valor;
    }
 
    // texto corto para mostrar en el dibujo del árbol: valor + expresión
    public String etiquetaCorta() {
        return String.format("%s  (%s)", formatearValor(), expresionOriginal);
    }
 
    private String formatearValor() {
        return (valor == Math.floor(valor)) ? String.valueOf((long) valor) : String.valueOf(valor);
    }
}
