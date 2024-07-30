/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class VentanaPensarController implements Initializable {

    @FXML
    private Label secuencia;

    @FXML
    private Label lblAnimalObjeto;
    
    private static boolean validacion=false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblAnimalObjeto.setText(JuegoController.modoJuego);
        if(!validacion){
            validacion = true;
            Thread t = new Thread(() -> {
                for (int i = 10; i >= 0; i--) {
                    final int count = i;
                    Platform.runLater(() -> secuencia.setText(String.valueOf(count)));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(()->{
                    Stage s = (Stage) secuencia.getScene().getWindow();
                    s.close();
                    try {
                        App.abrirNuevaVentana("juego", 110, 110);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            });
            t.start();
        }
    }
}

