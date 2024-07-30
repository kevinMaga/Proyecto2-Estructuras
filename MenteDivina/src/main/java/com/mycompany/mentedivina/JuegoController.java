/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class JuegoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static String modoJuego;
    public static Integer cantidadPreguntas;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> preguntas =ManejoArchivos.leerArchivoPreguntas(modoJuego);
        Map<String,ArrayList<String>> respuestas= ManejoArchivos.leerArchivoRespuestas(modoJuego);
        if(modoJuego.equals("animal")){
        }else if(modoJuego.equals("cosa")){
        }
    }    
    
}
