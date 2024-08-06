/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mentedivina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public static ArrayList<String> leerArchivoPreguntas(String nombreArchivo) {
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles +nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                resultado.add(linea.trim());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
   
    public static Map<Juego, ArrayList<String>> leerArchivoRespuestas(String nombreArchivo,Tipo t) {
        Map<Juego, ArrayList<String>> resultado = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles + nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] lista = linea.split("-");
                String[] info = lista[0].split(",");
                ArrayList<String> respuestas = new ArrayList<>();
                for (int i = 1; i < lista.length; i++) {
                    respuestas.add(lista[i]);
                }
                Juego j = new Juego(info[0],App.pathImages+info[1],t);
                resultado.put(j, respuestas);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
}
