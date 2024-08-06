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
import javafx.scene.text.Font;
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
    @FXML
    private Label LBL1;
    @FXML
    private Label LBL2;
    @FXML
    private Label LBL3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 36);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"),22);
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 19);
        Font font4 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"),19);
        Font font5 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 24);
        
        LBL1.setFont(font);
        LBL2.setFont(font2);
        btnAnimal.setFont(font3);
        btnCosa.setFont(font3);
        LBL3.setFont(font4);
        BTNEmpezar.setFont(font5);
        
        
        
        
        
        InicioController.mediaPlayer.stop();
        InicioController.reproducirSonido("menu2.mp3");
        estilos();
        btnAnimal.setOnAction(e -> {
            JuegoController.modoJuego = "animal";
            efectoSeleccionButton(btnAnimal, btnCosa, "#f7405c", "#f5687d");
            try {
                App.abrirNuevaVentana("maxPreguntas", 177, 201);
            } catch (IOException ex) {
                ex.printStackTrace();
            }       
        });
        btnCosa.setOnAction(e -> {
            JuegoController.modoJuego = "objeto";
            efectoSeleccionButton(btnCosa, btnAnimal, "#f7405c", "#f5687d");
            try {
                App.abrirNuevaVentana("maxPreguntas", 177, 201);
            } catch (IOException ex) {
                ex.printStackTrace();
            }     
        });

        BTNEmpezar.setOnMouseClicked(e -> {
            String textoPreguntas = txtPreguntas.getText();
            boolean esNumeroValido = textoPreguntas != null && esNumeroEntero(textoPreguntas);
            boolean modoJuegoSeleccionado = JuegoController.modoJuego != null;
            if (!modoJuegoSeleccionado && !esNumeroValido) {
                mostrarAlerta("Llene todos los campos", "Por favor, seleccione el modo de juego, y llene el campo de número de preguntas con un número válido");
            } else if (!modoJuegoSeleccionado) {
                mostrarAlerta("Llene todos los campos", "Por favor, seleccione el modo de juego");
            } else if (!esNumeroValido) {
                mostrarAlerta("Número de preguntas", "Por favor, ingrese un número entero válido en el campo de número de preguntas");
            } else {
                int numeroPreguntas = Integer.valueOf(textoPreguntas);
                boolean numeroPreguntasAdecuado = false;
                int maximoPreguntas =0;
                if (JuegoController.modoJuego.equals("animal")) {
                    numeroPreguntasAdecuado = numeroPreguntas >= 2 && numeroPreguntas <= InicioController.preguntasAnimal.size();
                    maximoPreguntas=InicioController.preguntasAnimal.size();
                } else if (JuegoController.modoJuego.equals("objeto")) {
                    numeroPreguntasAdecuado = numeroPreguntas >= 2 && numeroPreguntas <= InicioController.preguntasObjeto.size();
                    maximoPreguntas=InicioController.preguntasObjeto.size();
                }

                if (!numeroPreguntasAdecuado) {
                    mostrarAlerta("Número de preguntas", "Por favor, ingrese un número de preguntas adecuado. Recuerde que el máximo de preguntas es "+maximoPreguntas);
                } else {
                    JuegoController.cantidadPreguntas = numeroPreguntas;
                    Stage s = (Stage) txtPreguntas.getScene().getWindow();
                    s.close();
                    try {
                        App.abrirNuevaVentana("ventanaPensar", 260, 163);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void efectoSeleccionButton(Button btn1, Button btn2, String color1, String color2) {
        btn2.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Luckiest Guy';"
        );

        btn1.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%," + color1 + "," + color2 + ");"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Luckiest Guy';"
        );
    }

    private boolean esNumeroEntero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setContentText(mensaje);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
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
                + "-fx-font-family: 'Luckiest Guy';"
        );

        btnCosa.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Luckiest Guy';"
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
                + "-fx-font-family: 'Luckiest Guy';"
        );
    }
}
