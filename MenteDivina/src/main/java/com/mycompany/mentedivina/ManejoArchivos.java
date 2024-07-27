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
    public static ArrayList<String> leerArchivoPreguntas(){
        ArrayList<String> resultado = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(App.pathFiles+"preguntas.txt"))){
            String linea;
           while((linea=br.readLine())!=null){
               resultado.add(linea.trim());
           }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public static Map<String,ArrayList<String>> leerArchivoRespuestas(){
       Map<String,ArrayList<String>> resultado = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(App.pathFiles+"respuestas.txt"))){
            String linea;
           while((linea=br.readLine())!=null){
               String[] lista = linea.split("\\s+");
               ArrayList<String> respuestas = new ArrayList<>();
               for(int i=1;i<lista.length;i++){
                   respuestas.add(lista[i]);
               }
               resultado.put(lista[0], respuestas);
           }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
}
