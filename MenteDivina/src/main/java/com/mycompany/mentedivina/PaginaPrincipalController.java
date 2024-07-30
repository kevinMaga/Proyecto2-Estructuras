/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.BinaryTree;
import modelo.NodeBinaryTree;

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
    private ComboBox cmbPreguntas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboBox();
        estilos();
        btnAnimal.setOnAction(e->{
            JuegoController.modoJuego="animal";
        });
        btnCosa.setOnAction(e->{
            JuegoController.modoJuego="cosa";
        });
        cmbPreguntas.setOnAction(e->{
            JuegoController.cantidadPreguntas=(int) cmbPreguntas.getValue();
        });
    }
    
    private void llenarComboBox(){
        for(int i=1;i<21;i++){
            cmbPreguntas.getItems().add(i);
        }
    }
    
    private void estilos(){
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
