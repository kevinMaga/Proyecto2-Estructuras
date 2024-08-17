package com.mycompany.mentedivina;

import com.goxr3plus.speech.translator.GoogleTranslate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.BinaryTree;
import modelo.NodeBinaryTree;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import modelo.Juego;
import modelo.Tipo;

/**
 * FXML Controller class
 *
 * @author Kevin Magallanes
 */
public class InicioController implements Initializable {

    public static ArrayList<String> preguntasAnimal;
    public static ArrayList<String> preguntasObjeto;
    public static Map<Juego, ArrayList<String>> respuestasAnimal;
    public static Map<Juego, ArrayList<String>> respuestasObjeto;
    public static MediaPlayer musicaInicio;
    public static String idioma;
    @FXML
    private Button BtnJugar;
    @FXML
    private Label LBL1;
    @FXML
    private Label LBL2;
    @FXML
    private Label LBL3;
    @FXML
    private ComboBox comboBoxIdioma;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ManejoArchivos.borrarContenidoArchivo("juegosAnadidos.txt");
        idioma = "es";
        preguntasAnimal = ManejoArchivos.leerArchivo("preguntasAnimal.txt");
        preguntasObjeto = ManejoArchivos.leerArchivo("preguntasObjeto.txt");
        respuestasAnimal = formarMapaRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
        respuestasObjeto = formarMapaRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
        comboBoxIdioma.getItems().addAll("Español", "English", "Français", "Português");
        comboBoxIdioma.setValue("Español");        musicaInicio = reproducirSonido("menu1.mp3");
        InicioController.reproducirSonido("menu1.mp3");

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 24);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"), 18);
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"), 16);

        LBL1.setFont(font2);
        LBL2.setFont(font3);
        LBL3.setFont(font2);

        if (font != null) {
            BtnJugar.setFont(font);
        } else {
            System.out.println("No se pudo cargar la fuente.");
        }
        BtnJugar.setOnAction(e -> {
            Stage s = (Stage) BtnJugar.getScene().getWindow();
            
            
            //Traducion archivos. Es Pesado se espera un momento
            if (!comboBoxIdioma.getValue().equals("Español")){
                try{
                    idioma = GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString());
                    App.abrirNuevaVentana("PantallaEsperar", 314, 248);
                }catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }else {
                preguntasAnimal = ManejoArchivos.leerArchivo("preguntasAnimal.txt");
                preguntasObjeto = ManejoArchivos.leerArchivo("preguntasObjeto.txt");
                respuestasAnimal = formarMapaRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
                respuestasObjeto = formarMapaRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
                try{
                    App.abrirNuevaVentana("paginaPrincipal", 424, 520);
                }catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }
            
            s.close();
            
        });
        comboBoxIdioma.setOnAction(e -> {
                try {
                    LBL1.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL1.getText()));
                    LBL2.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL2.getText()));
                    LBL3.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL3.getText()));
                    BtnJugar.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), BtnJugar.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        });
    }

    

    public static MediaPlayer reproducirSonido(String nombre) {
        String ubicacion = App.pathMusic + nombre;
        Media media = new Media(new File(ubicacion).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }

    

    public static Map<Juego, ArrayList<String>> formarMapaRespuestas(String nombreArchivo, Tipo t) {
        Map<Juego, ArrayList<String>> resultado = new HashMap<>();
        ArrayList<String> lineas = ManejoArchivos.leerArchivo(nombreArchivo);
        for (String linea : lineas) {
            String[] lista = linea.split(";");
            String[] info = lista[0].split(",");
            ArrayList<String> respuestas = new ArrayList<>();
            for (int i = 1; i < lista.length; i++) {
                respuestas.add(lista[i]);
            }
            Juego j = new Juego(info[0], info[1], t);
            resultado.put(j, respuestas);
        }
        return resultado;
    }
}
