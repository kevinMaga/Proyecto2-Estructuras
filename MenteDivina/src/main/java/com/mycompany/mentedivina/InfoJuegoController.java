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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import modelo.Juego;
import modelo.ListaCircularDoble;

public class InfoJuegoController implements Initializable {

    @FXML
    private ImageView imagenJuego;
    @FXML
    private Label nombreJuego;
    @FXML
    private Label LBLDescripcion;
    @FXML
    private Button BTNRegresar;
    @FXML
    private Button BTNSeguir;

    public static ListaCircularDoble listaJuegos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 18);

        actualizarVista();

        LBLDescripcion.setFont(font3);
        BTNRegresar.setFont(font3);
        BTNSeguir.setFont(font3);

        BTNRegresar.setOnMouseClicked(e -> {
            listaJuegos.getAnterior();
            actualizarVista();
        });

        BTNSeguir.setOnMouseClicked(e -> {
            listaJuegos.getSiguiente();
            actualizarVista();
        });
    }

    private void actualizarVista() {
        Juego juego = listaJuegos.getActual();
        try (FileInputStream fis = new FileInputStream(App.pathImages+juego.getNombreImagen())) {
            Image image = new Image(fis, 250, 250, true, true);
            imagenJuego.setImage(image);
        } catch (IOException e) {
           Image image =null;
            // Cargar una imagen por defecto si no se encuentra la imagen original
            try (FileInputStream defaultImageStream = new FileInputStream(App.pathImages + "noFotos.png")) {
                image = new Image(defaultImageStream, 250, 250, true, true);
                imagenJuego.setImage(image);
            } catch (IOException e1) {
                e1.printStackTrace();   
            }
        }
        nombreJuego.setText(juego.getNombre());
        LBLDescripcion.setWrapText(true);

        String tipo = PaginaPrincipalJuegoController.modoJuego; // "Animal" u "Objeto"
        String nombreArchivo = InicioController.idioma.equals("es") ? "descripcion" + tipo + ".txt" : "descripcion" + tipo + "traducido.txt";

        String descripcion = getDescripcionFromFile(nombreArchivo, juego.getNombre());
        LBLDescripcion.setText(descripcion.isEmpty() ? "No existe descripción de ese " + tipo.toLowerCase() : descripcion);
    }

    private String getDescripcionFromFile(String nombreArchivo, String nombreJuego) {
        ArrayList<String> lineas = ManejoArchivos.leerArchivo(nombreArchivo);
        String descripcion = "";
        for (String linea : lineas) {
            String[] partes = linea.split(",");
            if (partes[0].trim().equals(nombreJuego)) {
                descripcion = partes[1].trim();
                break;
            }
        }
        return descripcion;
    }
}
