package com.mycompany.mentedivina;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.BinaryTree;
import modelo.NodeBinaryTree;

public class JuegoController implements Initializable {

    public static String modoJuego;
    public static Integer cantidadPreguntas;
    public static BinaryTree<ArrayList<String>> arbol = null;
    private NodeBinaryTree<ArrayList<String>> nodoActual;

    @FXML
    private Label LBLPreguntas;
    @FXML
    private Button BTNSi;
    @FXML
    private Button BTNNo;
    @FXML
    private ImageView IVInicio;
    @FXML
    private ImageView imagenPensando;
    
    private int iterador=cantidadPreguntas;
    private Stack<String> imgs = new Stack<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imgs.push("pensando2.jpg");
        imgs.push("pensando3.jpg");
        if (modoJuego.equals("animal")) {
            arbol = InicioController.buscarAgregarClave(cantidadPreguntas, InicioController.preguntasAnimal, InicioController.respuestasAnimal);
        } else if (modoJuego.equals("objeto")) {
            arbol = InicioController.buscarAgregarClave(cantidadPreguntas, InicioController.preguntasObjeto, InicioController.respuestasObjeto);
        }
        nodoActual = arbol.getRoot();
        mostrarPreguntaActual();
        IVInicio.setOnMouseClicked(e -> {
            Stage s = (Stage) IVInicio.getScene().getWindow();
            s.close();
            try {
                App.abrirNuevaVentana("paginaPrincipal", 416, 486);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        if(cantidadPreguntas>6){
            cantidadPreguntas/=3;
        }
        BTNSi.setOnAction(e -> manejarRespuesta("si"));
        BTNNo.setOnAction(e -> manejarRespuesta("no"));
    }

    private void mostrarPreguntaActual() {
        LBLPreguntas.setText(nodoActual.getContent().get(0));
    }

    private void manejarRespuesta(String respuesta){
        
        if (iterador%cantidadPreguntas==0 && !imgs.empty()){
            cambioImagen(imgs.pop());
        }
        nodoActual = respuesta.equals("si") ? nodoActual.getLeft().getRoot() : nodoActual.getRight().getRoot();
        if (nodoActual.getLeft() == null && nodoActual.getRight() == null) {
            ArrayList<String> listaAnimales =nodoActual.getContent();
            if (!listaAnimales.isEmpty()) { 
                if(listaAnimales.size()==1){
                    LBLPreguntas.setText("El animal es: " + nodoActual.getContent().get(0));
                }else{
                    ListaAnimalesPosiblesController.animales=listaAnimales;
                    LBLPreguntas.setText("Hay varios animales posibles");
                    try {
                        App.abrirNuevaVentana("listaAnimalesPosibles", 370, 469);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                if(modoJuego.equals("objeto")){
                    LBLPreguntas.setText("No se encontró un objeto con esas características.");
                }
                LBLPreguntas.setText("No se encontró un animal con esas características.");
            }
        } else {
            mostrarPreguntaActual();
        }
        iterador--;
    }
    private void cambioImagen(String nombre) {
        try{
            FileInputStream f = new FileInputStream("src/main/resources/images/"+nombre);
            Image img =new Image(f,150,120,true,true);
            imagenPensando.setImage(img);
        }catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
    }

}
