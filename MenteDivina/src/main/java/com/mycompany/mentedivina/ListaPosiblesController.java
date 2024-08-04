/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */

public class ListaPosiblesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static ArrayList<String> lista;
    @FXML
    private VBox posibles;
    @FXML
    private Label labelAnimalObjeto;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelAnimalObjeto.setText("Posibles "+JuegoController.modoJuego+"s");
        for (String animalOObjeto : lista) {
            Label label = new Label(animalOObjeto);
            HBox hBox = new HBox(label);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(10, 10, 10, 10));
            hBox.setSpacing(10);
            posibles.getChildren().add(hBox);
        }
    }    
    
}
