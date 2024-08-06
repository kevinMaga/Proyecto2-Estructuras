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
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
/**
 * FXML Controller class
 *
 * @author Kevin Magallanes
 */

public class InicioController implements Initializable {
    
    public static ArrayList<String> preguntasAnimal = ManejoArchivos.leerArchivoPreguntas("preguntasAnimal.txt");
    public static ArrayList<String> preguntasObjeto = ManejoArchivos.leerArchivoPreguntas("preguntasObjeto.txt");
    public static Map<String,ArrayList<String>> respuestasAnimal =ManejoArchivos.leerArchivoRespuestas("respuestasAnimal.txt");
    public static Map<String,ArrayList<String>> respuestasObjeto =ManejoArchivos.leerArchivoRespuestas("respuestasObjeto.txt");
//    public static MediaPlayer mediaPlayer;
    
    @FXML
    private Button BtnJugar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //InicioController.reproducirSonido("menu1.mp3");
        
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
    
    private static BinaryTree<ArrayList<String>> crearArbol(int preguntasUsuario,ArrayList<String> preguntas) {
        BinaryTree<ArrayList<String>> arbol = new BinaryTree<>();
        arbol.setRoot(crearNodo(0, preguntasUsuario, preguntas));
        return arbol;
    }

    private static NodeBinaryTree<ArrayList<String>> crearNodo(int nivelActual, int preguntasUsuario, List<String> listaPreguntas) {
        if (nivelActual >= preguntasUsuario) {
            return new NodeBinaryTree<>(new ArrayList<>());
        }
        ArrayList<String> contenidoNodo = new ArrayList<>();
        contenidoNodo.add(listaPreguntas.get(nivelActual));
        NodeBinaryTree<ArrayList<String>> nodo = new NodeBinaryTree<>(contenidoNodo);
        nodo.setLeft(new BinaryTree<>(crearNodo(nivelActual + 1, preguntasUsuario, listaPreguntas)));
        nodo.setRight(new BinaryTree<>(crearNodo(nivelActual + 1, preguntasUsuario, listaPreguntas)));
        return nodo;
    }

    public static BinaryTree<ArrayList<String>> buscarAgregarClave(int preguntasUsuario,ArrayList<String> preguntas,Map<String,ArrayList<String>> respuestas) {
        BinaryTree<ArrayList<String>> arbol = crearArbol(preguntasUsuario,preguntas);
        for (String clave : respuestas.keySet()) {
            ArrayList<String> rClave = respuestas.get(clave);
            NodeBinaryTree<ArrayList<String>> nodoActual = arbol.getRoot();
            for (int i = 0; i < preguntasUsuario; i++) {
                String respuesta = rClave.get(i);
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
            ArrayList<String> claves = nodoActual.getContent();
            claves.add(clave);
            nodoActual.setContent(claves);
        }
        return arbol;
    }

    /*public static MediaPlayer reproducirSonido(String nombre){
        String ubicacion = "src/main/resources/sonidos/"+nombre;
        Media media = new Media(new File(ubicacion).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }
    **/
}
