package com.mycompany.mentedivina;

import com.goxr3plus.speech.translator.GoogleTranslate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import modelo.BinaryTree;
import modelo.Juego;
import modelo.ListaCircularDoble;
import modelo.NodeBinaryTree;

public class JuegoController implements Initializable {

    public static String modoJuego;
    public static Integer cantidadPreguntas;
    public static BinaryTree<Object> arbol = null;
    private int preguntasRespondidas = 0;
    private int ultimoIndice = -1;
    private NodeBinaryTree<Object> nodoActual;

    @FXML
    private Label LBLPreguntas;
    @FXML
    private Label LBL1;
    @FXML
    private Label LBL2;
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

    public static MediaPlayer musicaJuego = InicioController.reproducirSonido("pensando.mp3");
    public static String[] caras = {"preocupado.gif", "secreto.gif", "neutral.gif", "cinico.gif"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (InicioController.idioma != "es") {
            try {
                LBL1.setText(GoogleTranslate.translate("es", InicioController.idioma, LBL1.getText()));
                LBL2.setText(GoogleTranslate.translate("es", InicioController.idioma, LBL2.getText()));
                BTNSi.setText(GoogleTranslate.translate("es", InicioController.idioma, BTNSi.getText()));
                BTNNo.setText(GoogleTranslate.translate("es", InicioController.idioma, BTNNo.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        BTNSi.setCursor(Cursor.HAND);
        BTNNo.setCursor(Cursor.HAND);
        PaginaPrincipalController.musicaPaginaPrincipal.stop();
        musicaJuego = InicioController.reproducirSonido("pensando.mp3");
        actualizarImagen();
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
                App.abrirNuevaVentana("paginaPrincipal", 416, 520);
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
        BTNSi.setOnAction(e -> {
            preguntasRespondidas += 1;
            actualizarImagen();
            try {
                manejarRespuesta("si");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        BTNNo.setOnAction(e -> {
            preguntasRespondidas += 1;
            actualizarImagen();
            try {
                manejarRespuesta("no");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void mostrarPreguntaActual() {
        LBLPreguntas.setText((String) (nodoActual.getContent()));
    }

    private void actualizarImagen() {
        int intervalo = cantidadPreguntas / caras.length;
        int index = intervalo > 0 ? (preguntasRespondidas / intervalo) % caras.length : 1;
        if (index == ultimoIndice) {
            return;
        }
        ultimoIndice = index;
        String nombreImagen = caras[index];
        Image img = null;
        try (FileInputStream f = new FileInputStream(App.pathImages + nombreImagen)) {
            img = new Image(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imagenPensando.setImage(img);
    }

    private void manejarRespuesta(String respuesta) throws IOException {
    nodoActual = respuesta.equals("si") ? nodoActual.getLeft().getRoot() : nodoActual.getRight().getRoot();
    
    if (nodoActual.getLeft() == null && nodoActual.getRight() == null) {
        ArrayList<Juego> lista = (ArrayList<Juego>) nodoActual.getContent();
        ImageView imageView = null;

        if (!lista.isEmpty()) {
            musicaJuego.stop();
            musicaJuego = InicioController.reproducirSonido("victoria.mp3");

            if (lista.size() == 1) {
                Juego juego = lista.get(0);

                // Crear la lista circular con un solo elemento
                ArrayList<Juego> unicaLista = new ArrayList<>();
                unicaLista.add(juego);
                ListaCircularDoble listaJuegos = new ListaCircularDoble(unicaLista);

                // Asignar la lista a InfoJuegoController
                InfoJuegoController.listaJuegos = listaJuegos;

                // Mostrar imagen de victoria
                contenedor.getChildren().clear();
                FileInputStream fis = new FileInputStream(App.pathImages + "feliz.gif");
                Image image = new Image(fis, 250, 250, true, true);
                imageView = new ImageView(image);
                contenedor.getChildren().add(imageView);

                // Abrir la ventana de información del juego
                App.abrirNuevaVentana("infoJuego", 466, 494);
                LBLPreguntas.setText("El " + modoJuego + " es: " + juego.getNombre());
            } else {
                // Si hay más de un juego en la lista, abrir la ventana de posibles juegos
                PosiblesController.lista = lista;
                Stage s = (Stage) contenedor.getScene().getWindow();
                s.close();
                App.abrirNuevaVentana("posibles", 424, 448);
            }
        } else {
            // Si no se encontró ningún juego
            musicaJuego.stop();
            musicaJuego = InicioController.reproducirSonido("derrota.mp3");
            LBLPreguntas.setText("No se encontró un " + modoJuego + " así.");
            contenedor.getChildren().clear();
            FileInputStream fis = new FileInputStream(App.pathImages + "triste.gif");
            Image image = new Image(fis, 250, 250, true, true);
            imageView = new ImageView(image);
            contenedor.getChildren().add(imageView);
        }
    } else {
        // Continuar mostrando preguntas si aún no se ha llegado a una hoja
        mostrarPreguntaActual();
    }
}

}
