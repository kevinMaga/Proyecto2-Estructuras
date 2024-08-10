/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import static com.mycompany.mentedivina.ListaPosiblesController.lista;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.Juego;

public class PosiblesController implements Initializable {

    public static ArrayList<Juego> lista;
    @FXML
    private Label LBLPosibles;
    @FXML
    private FlowPane APLista;
    @FXML
    private Button BTNInicio;
    @FXML
    private Label LBL1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar las fuentes personalizadas
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 28);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 18);
        BTNInicio.setFont(font2);
        LBL1.setFont(font);
        LBLPosibles.setFont(font);
        
        BTNInicio.setOnMouseClicked(e -> {
            Stage s = (Stage) BTNInicio.getScene().getWindow();
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 486);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        
        String text = JuegoController.modoJuego.equals("animal") ? "e":"";
        LBLPosibles.setText(JuegoController.modoJuego + text + "s");

        // Crear un GridPane para organizar los AnchorPanes
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        int column = 0;
        int row = 0;

        for (Juego juego : lista) { 
            // Crear el Label para el nombre del animal
            Label nombre = new Label(juego.getNombre());
            nombre.setFont(font2);
            nombre.setAlignment(Pos.CENTER);

            // Cargar la imagen correspondiente
            ImageView imageView = new ImageView();
            
            try (FileInputStream imageStream = new FileInputStream(juego.getRutaImagen())) {
                Image image = new Image(imageStream, 80,80,true,true);
                imageView.setImage(image);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            VBox contenedor = new VBox();
            
            VBox contenedorImagen = new VBox();
            VBox contenedorNombre= new VBox();
            
            contenedorImagen.setPadding(new Insets(15,15,15,15));
            contenedorNombre.setPadding(new Insets(15,15,15,15));
            contenedorNombre.setAlignment(Pos.CENTER);
            contenedorImagen.setAlignment(Pos.CENTER);
            
            
            contenedorNombre.getChildren().add(nombre);
            contenedorImagen.getChildren().add(imageView);
            contenedor.setStyle("-fx-background-color: white;"
            + "-fx-background-radius: 15;");
            contenedor.getChildren().addAll(contenedorNombre,contenedorImagen);
            
            contenedor.setOnMouseClicked(e ->{
                InfoJuegoController.juego=juego;
                try {
                    App.abrirNuevaVentana("infoJuego", 466, 494);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            APLista.getChildren().add(contenedor);   
        }

        
    }
}
