/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres;

import ControllerPostgres.Modelo.*;
import ControllerPostgres.Util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
            LecturaBD lbd = Leer.leerArchivo(archivoSubido.getInputStream());
            System.out.println("LecturaRealizada");
            System.out.println();
            File archivo = Generar.generarscript(lbd);   
            descargar(archivo);
        }catch(Exception e){ 
            e.printStackTrace();
        }
    }  
    
    public void descargar(File file){
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

        response.setHeader("Content-Disposition", "attachment;filename=Script.sql");  
        response.setContentLength((int) file.length());  
        ServletOutputStream out = null;  
        try {  
            FileInputStream input = new FileInputStream(file);  
            byte[] buffer = new byte[1024];  
            out = response.getOutputStream();  
            int i = 0;  
            while ((i = input.read(buffer)) != -1) {  
                out.write(buffer);
                out.flush();  
            }  
            FacesContext.getCurrentInstance().getResponseComplete();  
        } catch (IOException err) {  
            err.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
            } catch (IOException err) {  
                err.printStackTrace();  
            }    
        }
    }
//    public File generar(){
//        archivoGenerado = new Generar("C:\\Users\\Ideapad\\Desktop");//DIRECCION DONDE SE GUARDARA ARCHIVO
//        return archivoGenerado.generarscript(lec1.leerArchivo());
//    }
}
