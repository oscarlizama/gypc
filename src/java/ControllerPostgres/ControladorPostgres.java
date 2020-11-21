/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres;

import ControllerPostgres.Modelo.*;
import ControllerPostgres.Util.*;
import java.io.IOException;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;


@ManagedBean
public class ControladorPostgres {
    private Part archivoSubido;
    private String folder;
    private Leer lec1;
    private Generar archivoGenerado;
    
    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    public Part getArchivoSubido() {
        return archivoSubido;
    }

    public void setArchivoSubido(Part archivoSubido) {
        this.archivoSubido = archivoSubido;
    }
    public void cargar(){
        try{
            folder = new Scanner(archivoSubido.getInputStream()).useDelimiter("\\A").next();
            lec1 = new Leer(folder);
        }catch(IOException e){
            e.printStackTrace();
        }
    }  
    public void generar(){
        archivoGenerado = new Generar("C:\\Users\\Ideapad\\Desktop");//DIRECCION DONDE SE GUARDARA ARCHIVO
        archivoGenerado.generarscript(lec1.leerArchivo());
    }
}
