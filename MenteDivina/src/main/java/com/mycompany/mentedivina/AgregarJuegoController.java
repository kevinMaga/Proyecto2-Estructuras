/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import com.goxr3plus.speech.translator.GoogleTranslate;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.Juego;
import modelo.ListaCircularDoble;
import modelo.Tipo;

/**
 * FXML Controller class
 *
 * @author Kevin
 */
public class AgregarJuegoController implements Initializable {

    @FXML
    private Label LBL1;
    @FXML
    private Label LBLPosibles;
    @FXML
    private FlowPane APLista;
    @FXML
    private Button BTNAgregar;

    public static Map<Juego, ArrayList<String>> mapa;
    private VBox contenedorSeleccionado;
    private Juego j;
    private ArrayList<String> respuestasJuego;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 28);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/LuckiestGuy-Regular.ttf"), 18);
        BTNAgregar.setFont(font2);
        LBL1.setFont(font);
        LBLPosibles.setFont(font);
        contenedorSeleccionado=null;
        inicializarListaDeJuegos(mapa,APLista,font2);
        if(InicioController.idioma!="es"){
            try {
                BTNAgregar.setText(GoogleTranslate.translate("es",InicioController.idioma,BTNAgregar.getText()));
                LBL1.setText(GoogleTranslate.translate("es",InicioController.idioma,LBL1.getText()));
                LBLPosibles.setText(GoogleTranslate.translate("es",InicioController.idioma,LBLPosibles.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        BTNAgregar.setOnMouseClicked(e -> {
            if (j==null){
                PaginaPrincipalController.mostrarAlerta("Seleccionar conetenedor", "Porfavor seleccione un Juego para añadir");
            }else if(verificarSiJuegoEstaAnadido(j)){
                PaginaPrincipalController.mostrarAlerta("Juego encontrado", "Juego dentro de la lista de juegos del sistema, escoja otro juego");
            }else{
                if(j.getTipo().equals(Tipo.ANIMAL)){
                    InicioController.respuestasAnimal.put(j, respuestasJuego);          
                }else{
                    InicioController.respuestasObjeto.put(j,respuestasJuego);
                }
                ManejoArchivos.escribirEnArchivo("juegosAnadidos.txt", j.getNombre());
                Stage s =(Stage)BTNAgregar.getScene().getWindow();
                s.close();
            }
        });
        
        
    }
    
    public void inicializarListaDeJuegos(Map<Juego, ArrayList<String>> mapa, FlowPane APLista, Font font2) {
        for (Juego juego : mapa.keySet()) {
            VBox contenedor = PosiblesController.crearContenedorParaJuego(font2, juego);
            contenedor.setOnMouseClicked(e -> {
                seleccionarContenedor(contenedor);
                j=juego;
                respuestasJuego =mapa.get(juego);
            });
            APLista.getChildren().add(contenedor);
        }

    }
    
    private boolean verificarSiJuegoEstaAnadido(Juego j){
        ArrayList<String> juegosAnadidos = ManejoArchivos.leerArchivo("juegosAnadidos.txt");
        for(String juegoAnadido:juegosAnadidos){
            if(juegoAnadido.equals(j.getNombre())){
                return true;
            }
        }
        return false;
    }


    private void seleccionarContenedor(VBox contenedor) {
        if (contenedorSeleccionado != null) {
            contenedorSeleccionado.setStyle("-fx-background-color: White;");
        }
        contenedor.setStyle("-fx-background-color: #ADD8E6;"); 
        contenedorSeleccionado = contenedor;
    }

}
