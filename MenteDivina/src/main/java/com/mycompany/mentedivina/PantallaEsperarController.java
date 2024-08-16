/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import com.goxr3plus.speech.translator.GoogleTranslate;
import static com.mycompany.mentedivina.InicioController.formarMapaRespuestas;
import static com.mycompany.mentedivina.InicioController.idioma;
import static com.mycompany.mentedivina.InicioController.preguntasAnimal;
import static com.mycompany.mentedivina.InicioController.preguntasObjeto;
import static com.mycompany.mentedivina.InicioController.respuestasAnimal;
import static com.mycompany.mentedivina.InicioController.respuestasObjeto;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Tipo;

/**
 * FXML Controller class
 *
 * @author Cesar
 */
public class PantallaEsperarController implements Initializable {

    @FXML
    private Label LBL1;
    @FXML
    private Label LBL2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       if(InicioController.idioma!="es"){
           try {
                LBL1.setText(GoogleTranslate.translate("es",InicioController.idioma,LBL1.getText()));
                LBL2.setText(GoogleTranslate.translate("es",InicioController.idioma,LBL2.getText()));
           } catch (IOException ex) {
               ex.printStackTrace();
           }
         
            Thread t = new Thread(() -> {
                try {
                    traducirArchivos();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                preguntasAnimal = ManejoArchivos.leerArchivo("preguntasAnimalTraducido.txt");
                preguntasObjeto = ManejoArchivos.leerArchivo("preguntasObjetoTraducido.txt");
                respuestasAnimal = formarMapaRespuestas("respuestasAnimalTraducido.txt", Tipo.ANIMAL);
                respuestasObjeto = formarMapaRespuestas("respuestasObjetoTraducido.txt", Tipo.OBJETO);
            Platform.runLater(() -> {
                
                Stage s = (Stage) LBL1.getScene().getWindow();
                s.close();
                try {
                    App.abrirNuevaVentana("paginaPrincipal", 416, 520);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        });
        t.start();
        }
    }    
    private void traducirArchivo(String archivoEntrada, String archivoSalida, String esRespuesta) throws IOException {
        ArrayList<String> lineas = ManejoArchivos.leerArchivo(archivoEntrada);
        for (String linea : lineas) {
            String translatedLine="";
            switch (esRespuesta) {
                case "respuesta":
                    String[] parts = linea.split(",");
                    String entidad = parts[0];
                    String datos = parts[1];
                    String translatedText = GoogleTranslate.translate("es", idioma, entidad);
                    translatedLine = translatedText + "," + datos;
                    break;
                case "descripcion":
                    String nombre = linea.split(",")[0];
                    String descripcion = linea.split(",")[1];
                    translatedLine = GoogleTranslate.translate("es", idioma, nombre)+","+GoogleTranslate.translate("es", idioma, descripcion);
                    break;
                case "pregunta":
                    translatedLine = GoogleTranslate.translate("es", idioma, linea);
                    break;
                default:
                    break;
            }
            ManejoArchivos.escribirEnArchivo(archivoSalida, translatedLine);
        }
    }

    private void traducirArchivos() throws IOException {
        // Borrar contenido de archivos traducidos antes de empezar la traducción
        ManejoArchivos.borrarContenidoArchivo("respuestasAnimalTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("respuestasObjetoTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("preguntasAnimalTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("preguntasObjetoTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("descripcionObjetoTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("descripcionAnimalTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("agregarAnimalTraducido.txt");
        ManejoArchivos.borrarContenidoArchivo("agregarObjetoTraducido.txt");

        // Traducción de respuestas de animales
        traducirArchivo("respuestasAnimal.txt", "respuestasAnimalTraducido.txt","respuesta");

        // Traducción de respuestas de objetos
        traducirArchivo("respuestasObjeto.txt", "respuestasObjetoTraducido.txt", "respuesta");

        // Traducción de preguntas de animales
        traducirArchivo("preguntasAnimal.txt", "preguntasAnimalTraducido.txt", "pregunta");

        // Traducción de preguntas de objetos
        traducirArchivo("preguntasObjeto.txt", "preguntasObjetoTraducido.txt", "pregunta");
        
        //Traducción de descripción de animales
        traducirArchivo("descripcionAnimal.txt","descripcionAnimalTraducido.txt","descripcion");
        
        //Traducción de descripción de objetos
        traducirArchivo("descripcionObjeto.txt","descripcionObjetoTraducido.txt","descripcion");
        
        //Traducción de agregarAnimal
        traducirArchivo("agregarAnimal.txt","agregarAnimalTraducido.txt","respuesta");
        
        //Traducción de agregarObjeto
        traducirArchivo("agregarObjeto.txt","agregarObjetoTraducido.txt","respuesta");
    }
}
