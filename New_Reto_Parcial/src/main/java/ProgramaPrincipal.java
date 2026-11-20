/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Juan Tapia
 */
public class ProgramaPrincipal {
    
    // la entrada del arreglo: el texto de la expresión + su notación.
    private record Entrada(String texto, Notacion notacion) {
        String[] tokens() { return texto.split(" "); }
    }
 
    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));          // Fuerza salida en UTF-8 para que tildes y líneas de árbol (├── └──)
                                                                                           // Fuerza salida en UTF-8 para que tildes y líneas de árbol (├── └──)
        Entrada[] entradas = {                                                             // regional del sistema operativo donde se ejecute.
            new Entrada("5 6 +", Notacion.POSFIJA),
            new Entrada("( 9 - ( 3 + 2 ) ) * 2", Notacion.INFIJA),
            new Entrada("+ 4 * 3 2", Notacion.PREFIJA),
            new Entrada("7 3 2 + - 5 3 * +", Notacion.POSFIJA),
            new Entrada("2 ^ 2 ^ 3", Notacion.INFIJA),
            new Entrada("- 15 6", Notacion.PREFIJA),
        };
 
        ArbolBST abbe = new ArbolBST();
 
        titulo("PASO 1 — NORMALIZACIÓN Y EVALUACIÓN");
        for (Entrada e : entradas) {
            System.out.println("\n► Expresión: \"" + e.texto() + "\"   [" + e.notacion() + "]");
 
            List<String> posfija;
            double valor;
 
            switch (e.notacion()) {
                case POSFIJA -> {
                    posfija = Arrays.asList(e.tokens());
                    valor = Evaluador.evaluarPosfija(posfija, true);
                }
                case PREFIJA -> {
                    valor = Evaluador.evaluarPrefija(Arrays.asList(e.tokens()), true);
                    NodoExpr arbolTemporal = construirDesdePrefija(e.tokens());
                    posfija = Arrays.asList(ImpresorArbol.postorden(arbolTemporal).split(" "));
                }
                case INFIJA -> {
                    posfija = ConvertidorInfijo.aPosfija(e.tokens());
                    valor = Evaluador.evaluarPosfija(posfija, true);
                }
                default -> throw new IllegalStateException();
            }
 
            NodoExpr arbolExpresion = GeneradorArbol.construir(posfija);
            System.out.println("  Valor final: " + fmt(valor));
 
            NodoBST nodo = new NodoBST(e.texto(), e.notacion(), arbolExpresion, valor);
            abbe.insertar(nodo);
        }
 
        titulo("PASO 2 — ABBE CONSTRUIDO");
        abbe.imprimirArbol();
 
        titulo("PASO 3 — CONSULTAS CRUZADAS");
 
        System.out.println("\n► Nodo MÁXIMO");
        NodoBST maximo = abbe.buscarMaximo();
        System.out.println("  " + maximo.etiquetaCorta());
        System.out.println("  Preorden : " + ImpresorArbol.preorden(maximo.arbolExpresion));
        System.out.println("  Inorden  : " + ImpresorArbol.inorden(maximo.arbolExpresion));
        System.out.println("  Postorden: " + ImpresorArbol.postorden(maximo.arbolExpresion));
 
        System.out.println("\n► Nodo MÍNIMO");
        NodoBST minimo = abbe.buscarMinimo();
        System.out.println("  " + minimo.etiquetaCorta());
        System.out.println("  Inorden de su árbol de expresión: " + ImpresorArbol.inorden(minimo.arbolExpresion));
        System.out.println("  Infija con paréntesis: " + ImpresorArbol.infijaConParentesis(minimo.arbolExpresion));
 
        System.out.println("\n► Recorrido inorden COMPLETO del ABBE (ascendente)");
        for (NodoBST n : abbe.recorridoInorden()) {
            String posfijaEquivalente = ImpresorArbol.postorden(n.arbolExpresion);
            System.out.printf("  %-6s  \"%-22s\"  posfija = %s%n", fmt(n.valor), n.expresionOriginal, posfijaEquivalente);
        }
 
        titulo("PASO 4 — ELIMINACIÓN DE LA RAÍZ ORIGINAL");
        double valorRaizOriginal = abbe.getRaiz().valor;
        System.out.println("Eliminando raíz: " + abbe.getRaiz().etiquetaCorta());
        abbe.eliminar(valorRaizOriginal);
        System.out.println("\nABBE resultante:");
        abbe.imprimirArbol();
    }
 
    /** Construye un árbol de expresión directamente desde tokens PREFIJA (recorridos al revés). */
    private static NodoExpr construirDesdePrefija(String[] tokensPrefija) {
        PilaDinamica<NodoExpr> pila = new PilaDinamica<>();
        for (int i = tokensPrefija.length - 1; i >= 0; i--) {
            NodoExpr nodo = new NodoExpr(tokensPrefija[i]);
            if (nodo.esOperador()) {
                nodo.izquierdo = pila.desapilar();
                nodo.derecho = pila.desapilar();
            }
            pila.apilar(nodo);
        }
        return pila.desapilar();
    }
 
    private static String fmt(double d) {
        return (d == Math.floor(d)) ? String.valueOf((long) d) : String.valueOf(d);
    }
 
    private static void titulo(String texto) {
        String linea = "=".repeat(texto.length() + 4);
        System.out.println("\n" + linea);
        System.out.println("  " + texto);
        System.out.println(linea);
    }
}
