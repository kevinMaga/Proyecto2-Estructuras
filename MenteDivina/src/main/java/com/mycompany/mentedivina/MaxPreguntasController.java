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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class MaxPreguntasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblPreguntasMax;

    @FXML
    private Label secuencia;

    private boolean validacion = false;
    @FXML
    private Label lblAnimalObjeto;
    @FXML
    private ImageView imgAnimalCosa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (JuegoController.modoJuego.equals("animal")) {
            lblPreguntasMax.setText(String.valueOf(InicioController.preguntasAnimal.size()));
        } else {
            lblPreguntasMax.setText(String.valueOf(InicioController.preguntasObjeto.size()));
        }

        Thread t = new Thread(() -> {
            for (int i = 3; i >= 1; i--) {
                final int count = i;
                Platform.runLater(() -> secuencia.setText(String.valueOf(count)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                Stage s = (Stage) secuencia.getScene().getWindow();
                s.close();
            });
        });
        t.start();
    }
}
