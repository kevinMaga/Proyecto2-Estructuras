/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class PaginaPrincipalController implements Initializable {

    @FXML
    private Button btnAnimal;

    @FXML
    private Button btnCosa;

    @FXML
    private Label BTNEmpezar;

    @FXML
    private TextField txtPreguntas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estilos();
        btnAnimal.setOnAction(e -> {
            JuegoController.modoJuego = "animal";
        });
        btnCosa.setOnAction(e -> {
            JuegoController.modoJuego = "objeto";
        });

        BTNEmpezar.setOnMouseClicked(e -> {
            boolean numeroPreguntasBien = txtPreguntas.getText() != null && esNumeroEntero(txtPreguntas.getText()) && 
                    Integer.valueOf(txtPreguntas.getText())<=20 && Integer.valueOf(txtPreguntas.getText())>=5;
            boolean modoJuegoSeleccionado = JuegoController.modoJuego != null;
            if (numeroPreguntasBien && modoJuegoSeleccionado) {
                JuegoController.cantidadPreguntas = Integer.valueOf(txtPreguntas.getText());
                Stage s =(Stage)txtPreguntas.getScene().getWindow();
                s.close();
                try {
                    App.abrirNuevaVentana("ventanaPensar", 315, 245);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Llene todos los campos");
                a.setContentText("Por favor, llene el número de preguntas con un número entero y seleccione el modo de juego. "
                        + "Recuerde también que el número de preguntas como máximo puede ser 20");
                a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                a.showAndWait();
            }

        });
    }

    private boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void estilos() {
        btnAnimal.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Bernard MT Condensed';"
        );

        btnCosa.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Bernard MT Condensed';"
        );

        BTNEmpezar.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #AD60FF, #993AFF);"
                + "-fx-background-radius: 20; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 18px;"
                + "-fx-padding: 12 25 12 25;"
                + "-fx-font-weight: bold;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 7);"
                + "-fx-font-family: 'Bernard MT Condensed';"
        );
    }
}
