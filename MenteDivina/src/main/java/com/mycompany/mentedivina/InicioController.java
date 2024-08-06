package com.mycompany.mentedivina;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    public static ArrayList<String> preguntasAnimal = ManejoArchivos.leerArchivoPreguntas("preguntasAnimal.txt");
    public static ArrayList<String> preguntasObjeto = ManejoArchivos.leerArchivoPreguntas("preguntasObjeto.txt");
    public static Map<Juego, ArrayList<String>> respuestasAnimal = ManejoArchivos.leerArchivoRespuestas("respuestasAnimal.txt", Tipo.ANIMAL);
//    public static Map<Juego, ArrayList<String>> respuestasObjeto = ManejoArchivos.leerArchivoRespuestas("respuestasObjeto.txt", Tipo.OBJETO);
    public static MediaPlayer mediaPlayer;

    @FXML
    private Button BtnJugar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InicioController.reproducirSonido("menu1.mp3");

        BtnJugar.setOnAction(e -> {
            Stage s = (Stage) BtnJugar.getScene().getWindow();
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 486);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }

    private static BinaryTree<Object> crearArbol(int preguntasUsuario, ArrayList<String> preguntas) {
        BinaryTree<Object> arbol = new BinaryTree<>();
        arbol.setRoot(crearNodo(0, preguntasUsuario, preguntas));
        return arbol;
    }

    private static NodeBinaryTree<Object> crearNodo(int nivelActual, int preguntasUsuario, List<String> listaPreguntas) {
        if (nivelActual >= preguntasUsuario) {
            return new NodeBinaryTree<>(new ArrayList<Juego>());
        }
        ArrayList<String> contenidoNodo = new ArrayList<>();
        contenidoNodo.add(listaPreguntas.get(nivelActual));
        NodeBinaryTree<Object> nodo = new NodeBinaryTree<>(contenidoNodo);
        nodo.setLeft(new BinaryTree<>(crearNodo(nivelActual + 1, preguntasUsuario, listaPreguntas)));
        nodo.setRight(new BinaryTree<>(crearNodo(nivelActual + 1, preguntasUsuario, listaPreguntas)));
        return nodo;
    }

    public static BinaryTree<Object> buscarAgregarClave(int preguntasUsuario, ArrayList<String> preguntas, Map<Juego, ArrayList<String>> respuestas) {
        BinaryTree<Object> arbol = crearArbol(preguntasUsuario, preguntas);
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
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }

}
