package com.mycompany.mentedivina;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class InicioController implements Initializable {

    @FXML
    private Button BtnJugar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BtnJugar.setOnAction(e -> {

            try {
                App.abrirNuevaVentana("paginaPrincipal", 929, 628);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
    }
}
