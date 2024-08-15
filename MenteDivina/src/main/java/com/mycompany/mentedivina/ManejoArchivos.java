/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mentedivina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelo.Juego;
import modelo.Tipo;

/**
 *
 * @author Justin Roldan
 */
public class ManejoArchivos {
    public static ArrayList<String> leerArchivo(String nombreArchivo){
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(App.pathFiles+nombreArchivo), "UTF-8"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                resultado.add(linea.trim());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public static void escribirEnArchivo(String nombreArchivo, String contenido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(App.pathFiles+nombreArchivo, true))) {
            writer.write(contenido);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    public static void borrarContenidoArchivo(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(App.pathFiles + nombreArchivo, false))) {
        } catch (IOException e) {
            System.err.println("Error al borrar el contenido del archivo: " + e.getMessage());
        }
    }

}
