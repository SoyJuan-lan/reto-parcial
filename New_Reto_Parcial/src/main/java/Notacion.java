/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Tapia
 */
public enum Notacion {
    INFIJA,
    PREFIJA,
    POSFIJA;
 
    /** Nombre corto, alineado a 7 caracteres, útil para imprimir tablas parejas. */
    public String etiqueta() {
        return String.format("%-7s", this.name());
    }
}
