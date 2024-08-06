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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.BinaryTree;
import modelo.Juego;
import modelo.NodeBinaryTree;

public class JuegoController implements Initializable {

    public static String modoJuego;
    public static Integer cantidadPreguntas;
    public static BinaryTree<Object> arbol = null;
    private NodeBinaryTree<Object> nodoActual;
    
    
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
    @FXML
    private VBox contenedor;
    @FXML
    private ImageView imagenMago;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InicioController.mediaPlayer.stop();
        InicioController.reproducirSonido("pensando.mp3");
        
        Image img = null;
        try(FileInputStream f = new FileInputStream(App.pathImages+ "lluvia-de-ideas.gif")){
            img = new Image(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imagenPensando.setImage(img);
        if (modoJuego.equals("animal")) {
            arbol = InicioController.buscarAgregarClave(cantidadPreguntas, InicioController.preguntasAnimal, InicioController.respuestasAnimal);
        } else if (modoJuego.equals("objeto")) {
//            arbol = InicioController.buscarAgregarClave(cantidadPreguntas, InicioController.preguntasObjeto, InicioController.respuestasObjeto);
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
        BTNSi.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 19px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Bernard MT Condensed';"
        );

        BTNNo.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #FFD700, #FFA500);"
                + "-fx-background-radius: 15; "
                + "-fx-background-insets: 0,1,2,3,0;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 19px;"
                + "-fx-padding: 10 20 10 20;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 5);"
                + "-fx-font-family: 'Bernard MT Condensed';"
        );
        BTNSi.setOnAction(e -> manejarRespuesta("si"));
        BTNNo.setOnAction(e -> manejarRespuesta("no"));
    }

    private void mostrarPreguntaActual() {
        LBLPreguntas.setText((String) (nodoActual.getContent()));
    }

    private void manejarRespuesta(String respuesta){
        nodoActual = respuesta.equals("si") ? nodoActual.getLeft().getRoot() : nodoActual.getRight().getRoot();
        if (nodoActual.getLeft() == null && nodoActual.getRight() == null) {
            ArrayList<Juego> lista =(ArrayList<Juego>) nodoActual.getContent();
            ImageView imageView = null;
            if (!lista.isEmpty()) {  
                InicioController.mediaPlayer.stop();
                InicioController.reproducirSonido("victoria.mp3");
                if (lista.size() == 1) {
                    Juego juego = lista.get(0);
                    contenedor.getChildren().clear();   
                    try (FileInputStream fis = new FileInputStream(juego.getRutaImagen())) {
                        Image image = new Image(fis,250,250,true,true);
                        imageView = new ImageView(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    contenedor.getChildren().add(imageView);
                    LBLPreguntas.setText("El "+ modoJuego+" es: " + juego.getNombre());
                } else{
                    ListaPosiblesController.lista=lista;
                    Stage s =(Stage)contenedor.getScene().getWindow();
                    s.close();
                    try {
                        App.abrirNuevaVentana("listaPosibles", 370, 469);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                InicioController.mediaPlayer.stop();
                InicioController.reproducirSonido("derrota.mp3");
                LBLPreguntas.setText("No se encontró un "+modoJuego+" así.");
                contenedor.getChildren().clear();
                ImageView imageView1 = null;
                if(modoJuego.equals("animal")){
                    try (FileInputStream fis = new FileInputStream(App.pathImages+"noAnimal")) {
                        Image image = new Image(fis,250,250,true,true);
                        imageView1 = new ImageView(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }   
                }else{
                    try (FileInputStream fis = new FileInputStream(App.pathImages+"noObjeto")) {
                        Image image = new Image(fis,250,250,true,true);
                        imageView1 = new ImageView(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }   
                }
                contenedor.getChildren().add(imageView1);
            }
        } else {
            mostrarPreguntaActual();
        }
    }
}
