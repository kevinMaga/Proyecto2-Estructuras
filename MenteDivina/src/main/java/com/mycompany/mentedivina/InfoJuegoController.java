/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import modelo.Juego;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
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
    
    public static Juego juego;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 18);
        
        try (FileInputStream fis = new FileInputStream(juego.getRutaImagen())) {
            Image image = new Image(fis, 250, 250, true, true);
            imagenJuego.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        nombreJuego.setText(juego.getNombre());
        LBLDescripcion.setFont(font3);
        BTNRegresar.setFont(font3);
        BTNSeguir.setFont(font3);
        
        // Construir la ruta del archivo
        String tipo = JuegoController.modoJuego; // "Animal" o "Objeto"
        String nombreArchivo = "descripcion" + tipo + ".txt";
        String rutaArchivo = "/files/" + nombreArchivo;

        // Leer la descripción del archivo
        String descripcion = getDescripcionFromFile(rutaArchivo, juego.getNombre());
        LBLDescripcion.setText(descripcion);
    }
    
    private String getDescripcionFromFile(String rutaArchivo, String nombre) {
        String descripcion = "Descripción no encontrada";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource(rutaArchivo).getFile()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 2);
                if (partes.length == 2 && partes[0].trim().equalsIgnoreCase(nombre)) {
                    descripcion = partes[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return descripcion;
    }
}
