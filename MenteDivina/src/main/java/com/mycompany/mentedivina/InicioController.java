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
        musicaInicio = reproducirSonido("menu1.mp3");
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
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 520);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        comboBoxIdioma.setOnAction(e -> {
            if (!comboBoxIdioma.getValue().equals("Español")) {
                try {
                    idioma = GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString());
                    traducirArchivos();
                    LBL1.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL1.getText()));
                    LBL2.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL2.getText()));
                    LBL3.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), LBL3.getText()));
                    BtnJugar.setText(GoogleTranslate.translate(GoogleTranslate.detectLanguage(comboBoxIdioma.getValue().toString()), BtnJugar.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                preguntasAnimal = ManejoArchivos.leerArchivo("preguntasAnimalTraducido.txt");
                preguntasObjeto = ManejoArchivos.leerArchivo("preguntasObjetoTraducido.txt");
                respuestasAnimal = formarMapaRespuestas("respuestasAnimalTraducido.txt", Tipo.ANIMAL);
                respuestasObjeto = formarMapaRespuestas("respuestasObjetoTraducido.txt", Tipo.OBJETO);
            } else {
                preguntasAnimal = ManejoArchivos.leerArchivo("preguntasAnimal.txt");
                preguntasObjeto = ManejoArchivos.leerArchivo("preguntasObjeto.txt");
                respuestasAnimal = formarMapaRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
                respuestasObjeto = formarMapaRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
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
