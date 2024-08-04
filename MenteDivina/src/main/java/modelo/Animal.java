/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javafx.scene.image.Image;

/**
 *
 * @author Justin Roldan
 */
public class Animal {
    private String nombre;
    private Image imagen;

    public Animal(String nombre, Image imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
}
