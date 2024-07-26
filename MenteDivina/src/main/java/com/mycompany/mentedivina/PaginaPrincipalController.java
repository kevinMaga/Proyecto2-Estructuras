package com.mycompany.mentedivina;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PaginaPrincipalController {


    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}