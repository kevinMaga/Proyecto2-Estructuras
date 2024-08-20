package com.mycompany.mentedivina;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class MenuController implements Initializable {

    @FXML
    private ImageView imgArchivoP;
    @FXML
    private ImageView imgArchivoR;
    @FXML
    private Label label;
    @FXML
    private Button btnSiguiente;

    public static boolean isArchivoPSubido = false;
    public static boolean isArchivoRSubido = false;

    // Variables estáticas para almacenar los nombres de los archivos subidos
    public static String nombreArchivoP;
    public static String nombreArchivoR;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnSiguiente.setOnMouseClicked(E->{
            Stage s =(Stage)imgArchivoP.getScene().getWindow();
            s.close();
            try {
                App.abrirNuevaVentana("principal", 424, 520);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        imgArchivoP.setOnMouseClicked(event -> {
            isArchivoPSubido = handleFileUpload(true);
            checkIfBothFilesUploaded();
        });

        imgArchivoR.setOnMouseClicked(event -> {
            isArchivoRSubido = handleFileUpload(false);
            checkIfBothFilesUploaded();
        });
    }

    private boolean handleFileUpload(boolean esArchivoP) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtrar para permitir solo archivos de texto
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Mostrar el diálogo de selección de archivo
        Stage stage = (Stage) imgArchivoP.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Generar un nombre aleatorio utilizando UUID y agregar la extensión del archivo original
                String randomFileName = UUID.randomUUID().toString() + ".txt";
                

                // Especificar la ruta donde deseas guardar el archivo
                File destinationFile = new File(App.pathFiles + randomFileName);

                // Copiar el archivo al destino especificado
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Mostrar mensaje de éxito
                label.setText("Archivo subido correctamente: " + randomFileName);

                // Guardar el nombre del archivo en la variable estática correspondiente
                if (esArchivoP) {
                    nombreArchivoP = randomFileName;
                } else {
                    nombreArchivoR = randomFileName;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; // Indica que el archivo no fue subido
    }

    private void checkIfBothFilesUploaded() {
        if (isArchivoPSubido && isArchivoRSubido) {
            label.setText("Ambos archivos fueron subidos correctamente.");
        }
    }
}
