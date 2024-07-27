package com.mycompany.mentedivina;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import modelo.BinaryTree;
import modelo.NodeBinaryTree;

public class InicioController implements Initializable {
    
    public static ArrayList<String> preguntas;
    public static Map<String,ArrayList<String>> respuestas;

    @FXML
    private Button BtnJugar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preguntas=ManejoArchivos.leerArchivoPreguntas();
        respuestas=ManejoArchivos.leerArchivoRespuestas();
        System.out.println(buscarAgregarAnimal(1).getRoot().getContent());
        BtnJugar.setOnAction(e -> {

            try {
                App.abrirNuevaVentana("paginaPrincipal", 929, 628);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
    
    private static BinaryTree<ArrayList<String>> crearArbol(int preguntasUsuario) {
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

    public static BinaryTree<ArrayList<String>> buscarAgregarAnimal(int preguntasUsuario) {
        BinaryTree<ArrayList<String>> arbol = crearArbol(preguntasUsuario);
        for (String animal : respuestas.keySet()) {
            ArrayList<String> rAnimal = respuestas.get(animal);
            NodeBinaryTree<ArrayList<String>> nodoActual = arbol.getRoot();
            for (int i = 0; i < preguntasUsuario; i++) {
                String respuesta = rAnimal.get(i);
                if (respuesta.equals("SI")) {
                    if (nodoActual.getLeft() != null) {
                        nodoActual = nodoActual.getLeft().getRoot();
                    }
                } else {
                    if (nodoActual.getRight() != null) {
                        nodoActual = nodoActual.getRight().getRoot();
                    }
                }
            }
            ArrayList<String> animales = nodoActual.getContent();
            animales.add(animal);
            nodoActual.setContent(animales);
        }
        return arbol;
    }

    
}
