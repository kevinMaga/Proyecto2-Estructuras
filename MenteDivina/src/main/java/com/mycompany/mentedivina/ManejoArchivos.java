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

/**
 *
 * @author Justin Roldan
 */
public class ManejoArchivos {
    public static ArrayList<String> leerArchivoPreguntasAnimal() {
        ArrayList<String> resultado = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles + "preguntasAnimal.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                resultado.add(linea.trim());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public static ArrayList<String> leerArchivoPreguntasObjeto() {
        ArrayList<String> resultado2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles + "preguntasObjeto.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                resultado2.add(linea.trim());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado2;
    }

    public static Map<String, ArrayList<String>> leerArchivoRespuestasAnimal() {
        Map<String, ArrayList<String>> resultado = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles + "respuestasAnimal.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] lista = linea.split("\\s+");
                ArrayList<String> respuestas = new ArrayList<>();
                for (int i = 1; i < lista.length; i++) {
                    respuestas.add(lista[i]);
                }
                resultado.put(lista[0], respuestas);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public static Map<String, ArrayList<String>> leerArchivoRespuestasObjeto() {
        Map<String, ArrayList<String>> resultado1 = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.pathFiles + "respuestasObjeto.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] lista = linea.split("\\s+");
                ArrayList<String> respuestas = new ArrayList<>();
                for (int i = 1; i < lista.length; i++) {
                    respuestas.add(lista[i]);
                }
                resultado1.put(lista[0], respuestas);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado1;
    }
}
