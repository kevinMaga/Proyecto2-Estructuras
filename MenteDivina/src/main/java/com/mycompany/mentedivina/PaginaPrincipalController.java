/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.mentedivina;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.Initializable;
import modelo.BinaryTree;
import modelo.NodeBinaryTree;

/**
 * FXML Controller class
 *
 * @author Justin Roldan
 */
public class PaginaPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Piensa en un animal.");
        System.out.print("¿Cuántas preguntas puedo hacer? ");
        int nPreguntas = scanner.nextInt();
        scanner.nextLine();
        BinaryTree<ArrayList<String>> arbol = InicioController.buscarAgregarAnimal(nPreguntas);
    }
}
