/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Kevin
 */
public class Nodo {
    Juego juego;
    Nodo siguiente;
    Nodo anterior;

    public Nodo(Juego juego) {
        this.juego = juego;
        this.siguiente = null;
        this.anterior = null;
    }
}
