/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython;

import ControllerPython.Util.*;
import ControllerPython.Modelo.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



/**
 *
 * @author ADMIN
 */
@ManagedBean
public class ControladorPython {

    private Part file;
    private String fileContent;
    
    public void uploat(){
        try{
            LecturaUML lcU = LeerAlt.leerArchivo(file.getInputStream());
            LecturaUML lcUval = Validar.validar(lcU);
            //Validar.imprimirClases(lcUval);
            descargar(Generar.Escribiendo(lcUval));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
//    public void guardar(){
//        String usu = System.getProperty("user.name");     
//        String ruta="C:\\Users\\";
//        String rutaCompleta=ruta+usu+"\\Documents\\GeneradoPython";
//        File directorio = new File(rutaCompleta);
//        
//        if (!directorio.exists()) {
//            directorio.mkdir(); 
//            Generar nuevo = new Generar(rutaCompleta);
//            nuevo.Escribiendo();
//
//        }else{
//            Generar nuevo = new Generar(rutaCompleta);
//            nuevo.Escribiendo();
//        }
//    }
    
    public void descargar(File file){
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

        response.setHeader("Content-Disposition", "attachment;filename=archivo.py");  
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
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
  
    
    
    
    
}
