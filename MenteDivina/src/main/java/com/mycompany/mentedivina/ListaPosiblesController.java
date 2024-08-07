/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.Tipo;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class ListaPosiblesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static ArrayList<Juego> lista;
    @FXML
    private VBox posibles;
    @FXML
    private Label labelAnimalObjeto;
    @FXML
    private ImageView inicio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicio.setOnMouseClicked(e -> {
            Stage s = (Stage) inicio.getScene().getWindow();
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 486);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        String text = JuegoController.modoJuego.equals("animal") ? "e":"";
        labelAnimalObjeto.setText("Posibles " + JuegoController.modoJuego + text+"s");
        for (Juego juego : lista) { 
            Label label = new Label(juego.getNombre());
            HBox hBox = new HBox(label);
            hBox.setAlignment(Pos.CENTER);
            hBox.setPadding(new Insets(10, 10, 10, 10));
            hBox.setSpacing(10);
            hBox.setCursor(Cursor.HAND);
            hBox.setOnMouseClicked(e ->{
                InfoJuegoController.juego=juego;
                try {
                    App.abrirNuevaVentana("infoJuego", 466, 360);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            posibles.getChildren().add(hBox);
        }
        
    }

}
