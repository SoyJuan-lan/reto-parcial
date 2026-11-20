/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayDeque;
import java.util.Iterator;
 
/**
 *
 * @author Juan Tapia
 */
public class PilaDinamica <T> implements Iterable<T> {      //Pila genérica propia (envuelve un ArrayDeque) usada por el evaluador
 
    private final ArrayDeque<T> datos = new ArrayDeque<>();
 
    public void apilar(T valor) {
        datos.push(valor);
    }
 
    public T desapilar() {
        return datos.pop();
    }
 
    public T tope() {
        return datos.peek();
    }
 
    public boolean estaVacia() {
        return datos.isEmpty();
    }
 
    public int tamano() {
        return datos.size();
    }
 
    @Override
    public Iterator<T> iterator() {
        return datos.iterator();
    }
 
    /** Representación tipo [abajo, ..., arriba], útil para trazas legibles. */
    @Override
    public String toString() {
        Object[] arr = datos.toArray();
        StringBuilder sb = new StringBuilder("[");
        for (int i = arr.length - 1; i >= 0; i--) {
            sb.append(arr[i]);
            if (i > 0) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}

