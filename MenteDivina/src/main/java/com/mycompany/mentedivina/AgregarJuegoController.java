/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

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
        Map<Juego, ArrayList<String>> mapaJuego =inicializarListaDeJuegos(mapa,APLista,font2);
        contenedorSeleccionado=null;
        BTNAgregar.setOnMouseClicked(e -> {
            Juego j = mapaJuego.keySet().iterator().hasNext() ? mapaJuego.keySet().iterator().next() : null;
            if (j==null){
                PaginaPrincipalController.mostrarAlerta("Seleccionar conetenedor", "Porfavor seleccione un Juego para añadir");
            }else if(InicioController.respuestasAnimal.keySet().contains(j) || InicioController.respuestasObjeto.keySet().contains(j)){
                PaginaPrincipalController.mostrarAlerta("Juego encontrado", "Juego dentro de la lista de juegos del sistema, escoja otro juego");
            }else{
                if(j.getTipo().equals(Tipo.ANIMAL)){
                    InicioController.respuestasAnimal.put(j, mapaJuego.get(j));
                }else{
                    InicioController.respuestasObjeto.put(j, mapaJuego.get(j));
                }
                Stage s =(Stage)BTNAgregar.getScene().getWindow();
                s.close();
            }
        });
        
        
    }
    
    public Map<Juego, ArrayList<String>> inicializarListaDeJuegos(Map<Juego, ArrayList<String>> mapa, FlowPane APLista, Font font2) {
        Map<Juego, ArrayList<String>> juegoMapa = new HashMap<>();

        for (Juego juego : mapa.keySet()) {
            VBox contenedor = PosiblesController.crearContenedorParaJuego(font2, juego);
            contenedor.setOnMouseClicked(e -> {
                juegoMapa.put(juego, mapa.get(juego));
                seleccionarContenedor(contenedor, juego, mapa.get(juego));
            });
            APLista.getChildren().add(contenedor);
        }

        return juegoMapa;
    }


    private Map<Juego, ArrayList<String>> seleccionarContenedor(VBox contenedor, Juego juego, ArrayList<String> respuestasJuego) {
        Map<Juego, ArrayList<String>> juegoMapa = new HashMap<>();
        if (contenedorSeleccionado != null) {
            contenedorSeleccionado.setStyle("-fx-background-color: White;");
        }
        contenedor.setStyle("-fx-background-color: #ADD8E6;"); 
        contenedorSeleccionado = contenedor;
        juegoMapa.put(juego, respuestasJuego);
        return juegoMapa;
    }

    private String lineaRespuestas(ArrayList<String> lista) {
        StringBuilder linea = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            linea.append(lista.get(i));
            if (i < lista.size() - 1) {
                linea.append(";");
            }
        }
        return linea.toString();
    }

}
