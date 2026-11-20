/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
/**
 *
 * @author Juan Tapia
 */
public class ImpresorArbol {
    
    // ---------- Recorridos ----------
    public static String preorden(NodoExpr n) {
        StringBuilder sb = new StringBuilder();
        preordenAux(n, sb);
        return sb.toString().trim();
    }
    private static void preordenAux(NodoExpr n, StringBuilder sb) {
        if (n == null) return;
        sb.append(n.simbolo).append(' ');
        preordenAux(n.izquierdo, sb);
        preordenAux(n.derecho, sb);
    }
 
    public static String inorden(NodoExpr n) {
        StringBuilder sb = new StringBuilder();
        inordenAux(n, sb);
        return sb.toString().trim();
    }
    private static void inordenAux(NodoExpr n, StringBuilder sb) {
        if (n == null) return;
        inordenAux(n.izquierdo, sb);
        sb.append(n.simbolo).append(' ');
        inordenAux(n.derecho, sb);
    }
 
    public static String postorden(NodoExpr n) {
        StringBuilder sb = new StringBuilder();
        postordenAux(n, sb);
        return sb.toString().trim();
    }
    private static void postordenAux(NodoExpr n, StringBuilder sb) {
        if (n == null) return;
        postordenAux(n.izquierdo, sb);
        postordenAux(n.derecho, sb);
        sb.append(n.simbolo).append(' ');
    }
 
    // Reconstruye la infija totalmente parentizada, recorriendo el árbol
    public static String infijaConParentesis(NodoExpr n) {
        if (n == null) return "";
        if (n.esHoja()) return n.simbolo;
        return "(" + infijaConParentesis(n.izquierdo) + " " + n.simbolo + " "
                + infijaConParentesis(n.derecho) + ")";
    }
 
    public static <T> void imprimirArbol(T raiz, Function<T, String> etiqueta,
                                          Function<T, T> izq, Function<T, T> der) {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }
        System.out.println(etiqueta.apply(raiz));
        imprimirHijos(raiz, "", etiqueta, izq, der);
    }
 
    private static <T> void imprimirHijos(T nodo, String prefijo, Function<T, String> etiqueta,
                                           Function<T, T> izq, Function<T, T> der) {
        List<T> hijos = new ArrayList<>();
        List<String> etiquetasRama = new ArrayList<>();
        T i = izq.apply(nodo), d = der.apply(nodo);
        if (i != null) { hijos.add(i); etiquetasRama.add("IZQ"); }
        if (d != null) { hijos.add(d); etiquetasRama.add("DER"); }
 
        for (int idx = 0; idx < hijos.size(); idx++) {
            boolean esUltimo = idx == hijos.size() - 1;
            String conector = esUltimo ? "└── " : "├── ";
            System.out.println(prefijo + conector + "[" + etiquetasRama.get(idx) + "] " + etiqueta.apply(hijos.get(idx)));
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
            imprimirHijos(hijos.get(idx), nuevoPrefijo, etiqueta, izq, der);
        }
    }
}
// desde el public imprimir arbol se realiza lo siguiente
// ---------- dibujo ascil genérico ----------
 
    //
     // imprime un árbol binario en consola con conectores │ ├── └──,
     // de forma similar a como los IDE muestran árboles de archivos.
     
     //  raiz      nodo raíz del árbol (de cualquier tipo T)
     //  etiqueta  función que devuelve el texto a mostrar de un nodo
     //  izq       función que devuelve el hijo izquierdo (o null)
     //  der       función que devuelve el hijo derecho (o null)
     