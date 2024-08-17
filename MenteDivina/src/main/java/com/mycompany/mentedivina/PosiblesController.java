/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;


import com.goxr3plus.speech.translator.GoogleTranslate;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.ListaCircularDoble;

public class PosiblesController implements Initializable {

    public static ArrayList<Juego> lista;
    private ListaCircularDoble listaJuegos;

    @FXML
    private Label LBLPosibles;
    @FXML
    private FlowPane APLista;
    @FXML
    private Label LBL1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 28);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 18);
        LBL1.setFont(font);
        LBLPosibles.setFont(font);

        String text = PaginaPrincipalJuegoController.modoJuego.equals("animal") ? "e" : "";
        LBLPosibles.setText(PaginaPrincipalJuegoController.modoJuego + text + "s");

        // Crear la lista circular doblemente enlazada
        listaJuegos = new ListaCircularDoble(lista);
        for (Juego juego : lista) {
            VBox contenedor = crearContenedorParaJuego(font2, juego);
            contenedor.setOnMouseClicked(e -> {
                // Establecer el juego actual en la lista circular
                listaJuegos.setActual(juego);

                // Abrir la ventana de información del juego
                InfoJuegoController.listaJuegos = listaJuegos;
                try {
                    App.abrirNuevaVentana("infoJuego", 466, 494);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            APLista.getChildren().add(contenedor);
        }
        if (InicioController.idioma != "es") {
            try {
                LBL1.setText(GoogleTranslate.translate("es", InicioController.idioma, LBL1.getText()));
                LBLPosibles.setText(GoogleTranslate.translate("es", InicioController.idioma, LBLPosibles.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static VBox crearContenedorParaJuego(Font font2,Juego juego) {
        Label nombre = new Label(juego.getNombre());
        nombre.setFont(font2);
        nombre.setAlignment(Pos.CENTER);

        // Cargar la imagen correspondiente
        ImageView imageView = new ImageView();

        try (FileInputStream imageStream = new FileInputStream(App.pathImages+juego.getNombreImagen())) {
            Image image = new Image(imageStream, 80, 80, true, true);
            imageView.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        VBox contenedor = new VBox();
        VBox contenedorImagen = new VBox();
        VBox contenedorNombre = new VBox();

        contenedorImagen.setPadding(new Insets(15, 15, 15, 15));
        contenedorNombre.setPadding(new Insets(15, 15, 15, 15));
        contenedorNombre.setAlignment(Pos.CENTER);
        contenedorImagen.setAlignment(Pos.CENTER);

        contenedorNombre.getChildren().add(nombre);
        contenedorImagen.getChildren().add(imageView);
        contenedor.setStyle("-fx-background-color: white;"
                + "-fx-background-radius: 15;");
        contenedor.getChildren().addAll(contenedorNombre, contenedorImagen);
        return contenedor;
    }
}

