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
public class ArbolBST {
    private NodoBST raiz;
 
    public NodoBST getRaiz() {
        return raiz;
    }
 
    // ---------------------------------------------------------------
    // INSERCIÓN
    // ---------------------------------------------------------------
    // Inserta un nodo según su valor numérico.
  
    public void insertar(NodoBST nuevo) {
        raiz = insertarDesde(raiz, nuevo);
    }
 
    private NodoBST insertarDesde(NodoBST actual, NodoBST nuevo) {
        if (actual == null) return nuevo;
        if (nuevo.valor < actual.valor) {
            actual.izquierdo = insertarDesde(actual.izquierdo, nuevo);
        } else {
            actual.derecho = insertarDesde(actual.derecho, nuevo);
        }
        return actual;
    }
 
    // ---------------------------------------------------------------
    // BÚSQUEDAS
    // ---------------------------------------------------------------
 
    public NodoBST buscarMinimo() {
        return buscarMinimoDesde(raiz);
    }
    private NodoBST buscarMinimoDesde(NodoBST n) {
        if (n == null) return null;
        while (n.izquierdo != null) n = n.izquierdo;
        return n;
    }
 
    public NodoBST buscarMaximo() {
        return buscarMaximoDesde(raiz);
    }
    private NodoBST buscarMaximoDesde(NodoBST n) {
        if (n == null) return null;
        while (n.derecho != null) n = n.derecho;
        return n;
    }
    // ---------------------------------------------------------------
    // ELIMINACIÓN
    // ---------------------------------------------------------------
 
    // Elimina el nodo con el valor dado, aplicando el algoritmo estándar de BST
    public void eliminar(double valor) {
        raiz = eliminarDesde(raiz, valor);
    }
 
    private NodoBST eliminarDesde(NodoBST n, double valor) {
        if (n == null) return null;
 
        if (valor < n.valor) {
            n.izquierdo = eliminarDesde(n.izquierdo, valor);
        } else if (valor > n.valor) {
            n.derecho = eliminarDesde(n.derecho, valor);
        } else {
            // --- Nodo encontrado: 3 casos clásicos de eliminación en BST ---
            if (n.izquierdo == null) return n.derecho;            // 0 o 1 hijo (derecho)
            if (n.derecho == null) return n.izquierdo;            // 1 hijo (izquierdo)
 
            NodoBST sucesor = buscarMinimoDesde(n.derecho);                     // 2 hijos: se reemplaza el CONTENIDO por el del sucesor inorden
            n.expresionOriginal = sucesor.expresionOriginal;                    // (el mínimo del subárbol derecho). Se copian TODOS los campos,
            n.notacionOrigen = sucesor.notacionOrigen;                          // incluido arbolExpresion, para que valor y expresión nunca
            n.arbolExpresion = sucesor.arbolExpresion;                          // queden desincronizados entre sí dentro del mismo nodo.
            n.valor = sucesor.valor;
            n.derecho = eliminarDesde(n.derecho, sucesor.valor);
        }
        return n;
    }
    // ---------------------------------------------------------------
    // RECORRIDO
    // ---------------------------------------------------------------
 
    // Devuelve los nodos en orden ascendente de valor (recorrido inorden del BST)
    public List<NodoBST> recorridoInorden() {
        List<NodoBST> resultado = new ArrayList<>();
        inordenDesde(raiz, resultado);
        return resultado;
    }
    private void inordenDesde(NodoBST n, List<NodoBST> acumulado) {
        if (n == null) return;
        inordenDesde(n.izquierdo, acumulado);
        acumulado.add(n);
        inordenDesde(n.derecho, acumulado);
    }
    // ---------------------------------------------------------------
    // VISUALIZACIÓN — reutiliza ImpresorArbol pasándole lambdas
    // ---------------------------------------------------------------
 
    public void imprimirArbol() {
        ImpresorArbol.imprimirArbol(raiz, NodoBST::etiquetaCorta, n -> n.izquierdo, n -> n.derecho);
    }
}
