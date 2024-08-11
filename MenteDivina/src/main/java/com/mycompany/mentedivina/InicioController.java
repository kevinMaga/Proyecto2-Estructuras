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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    public static Map<Juego, ArrayList<String>> respuestasAnimal ;
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
        idioma="es";
        preguntasAnimal = ManejoArchivos.leerArchivoPreguntas("preguntasAnimal.txt");
        preguntasObjeto = ManejoArchivos.leerArchivoPreguntas("preguntasObjeto.txt");
        respuestasAnimal = ManejoArchivos.leerArchivoRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
        respuestasObjeto = ManejoArchivos.leerArchivoRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
        comboBoxIdioma.getItems().addAll("Español", "English", "Français", "Português");
        musicaInicio = reproducirSonido("menu1.mp3");
        InicioController.reproducirSonido("menu1.mp3");

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 24);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"),18);
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/Baloo2-VariableFont_wght.ttf"),16);
        
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
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 486);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        comboBoxIdioma.setOnAction(e -> {  
            if(!comboBoxIdioma.getValue().equals("Español")){
                try {
                    idioma = GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                traducirArchivos();
                try{
                    LBL1.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL1.getText())); 
                    LBL2.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL2.getText()));
                    LBL3.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL3.getText()));
                    BtnJugar.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), BtnJugar.getText()));
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
                preguntasAnimal = ManejoArchivos.leerArchivoPreguntas("preguntasAnimalTraducido.txt");
                preguntasObjeto = ManejoArchivos.leerArchivoPreguntas("preguntasObjetoTraducido.txt");
                respuestasAnimal = ManejoArchivos.leerArchivoRespuestas("respuestasAnimalTraducido.txt", Tipo.ANIMAL);
                respuestasObjeto = ManejoArchivos.leerArchivoRespuestas("respuestasObjetoTraducido.txt", Tipo.OBJETO);
            } else {
                preguntasAnimal = ManejoArchivos.leerArchivoPreguntas("preguntasAnimal.txt");
                preguntasObjeto = ManejoArchivos.leerArchivoPreguntas("preguntasObjeto.txt");
                respuestasAnimal = ManejoArchivos.leerArchivoRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
                respuestasObjeto = ManejoArchivos.leerArchivoRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
            }
            
        });
    }

    public static BinaryTree<Object> crearArbol(int nivelActual, int preguntasUsuario, List<String> listaPreguntas) {
        BinaryTree<Object> arbol = new BinaryTree<>();

        if (nivelActual >= preguntasUsuario) {
            arbol.setRoot(new NodeBinaryTree<>(new ArrayList<Juego>()));
        } else {
            NodeBinaryTree<Object> nodo = new NodeBinaryTree<>(listaPreguntas.get(nivelActual));
            nodo.setLeft(crearArbol(nivelActual + 1, preguntasUsuario, listaPreguntas));
            nodo.setRight(crearArbol(nivelActual + 1, preguntasUsuario, listaPreguntas));
            arbol.setRoot(nodo);
        }

        return arbol;
    }

    public static BinaryTree<Object> buscarAgregarClave(int preguntasUsuario, ArrayList<String> preguntas, Map<Juego, ArrayList<String>> respuestas) {
        BinaryTree<Object> arbol = crearArbol(0, preguntasUsuario, preguntas);
        for (Juego juego : respuestas.keySet()) {
            ArrayList<String> rJuego = respuestas.get(juego);
            NodeBinaryTree<Object> nodoActual = arbol.getRoot();
            for (int i = 0; i < preguntasUsuario; i++) {
                String respuesta = rJuego.get(i);
                if (respuesta.equals("si")) {
                    if (nodoActual.getLeft() != null) {
                        nodoActual = nodoActual.getLeft().getRoot();
                    }
                } else {
                    if (nodoActual.getRight() != null) {
                        nodoActual = nodoActual.getRight().getRoot();
                    }
                }
            }
            ArrayList<Juego> juegosEnHoja = (ArrayList<Juego>) nodoActual.getContent();
            juegosEnHoja.add(juego);
        }
        return arbol;
    }

    public static MediaPlayer reproducirSonido(String nombre) {
        String ubicacion = App.pathMusic + nombre;
        Media media = new Media(new File(ubicacion).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }
    private void traducirArchivos(){
        //respuesta Animales
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(App.pathFiles+"respuestasAnimal.txt"), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathFiles+"respuestasAnimalTraducido.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Separar la línea en partes: nombre del animal, nombre de la imagen, y texto a traducir
                String[] parts = line.split(",");
                String animal = parts[0];
                String datos = parts[1];
                
                // Traducir solo la parte del texto
                String translatedText = GoogleTranslate.translate("es",idioma, animal);

                // Volver a ensamblar la línea traducida
                String translatedLine = translatedText + "," + datos;

                // Escribir la línea traducida en el nuevo archivo
                bw.write(translatedLine);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException exo) {
            exo.printStackTrace();
            System.out.println("Problema en traducir preguntas Animal.");
        }
        
        //respuestas Objeto
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(App.pathFiles+"respuestasObjeto.txt"), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathFiles+"respuestasObjetoTraducido.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Separar la línea en partes: nombre del animal, nombre de la imagen, y texto a traducir
                String[] parts = line.split(",");
                String cosa = parts[0];
                String datos = parts[1];
                
                // Traducir solo la parte del texto
                String translatedText = GoogleTranslate.translate("es",idioma, cosa);

                // Volver a ensamblar la línea traducida
                String translatedLine = translatedText + "," + datos;

                // Escribir la línea traducida en el nuevo archivo
                bw.write(translatedLine);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException exo) {
            exo.printStackTrace();
            System.out.println("Problema en traducir preguntas Objeto.");
        }
        
        //preguntas Animales
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(App.pathFiles+"preguntasAnimal.txt"), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathFiles+"preguntasAnimalTraducido.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                
                // Traducir solo la parte del texto
                String translatedText = GoogleTranslate.translate("es",idioma, line);

                // Escribir la línea traducida en el nuevo archivo
                bw.write(translatedText);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException exo) {
            exo.printStackTrace();
            System.out.println("Problema en traducir preguntas Animal.");
        }
    
        //preguntas Animales
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(App.pathFiles+"preguntasObjeto.txt"), "UTF-8"));
             BufferedWriter bw = new BufferedWriter(new FileWriter(App.pathFiles+"preguntasObjetoTraducido.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                
                // Traducir solo la parte del texto
                String translatedText = GoogleTranslate.translate("es",idioma, line);

                // Escribir la línea traducida en el nuevo archivo
                bw.write(translatedText);
                bw.newLine();
            }
            br.close();
            bw.close();
        } catch (IOException exo) {
            exo.printStackTrace();
            System.out.println("Problema en traducir preguntas Objeto.");
        }
    }
}
