package com.mycompany.mentedivina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    //Esto es un cambio
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(App.pathFiles+nombreArchivo,true), "UTF-8"))) {
            
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
