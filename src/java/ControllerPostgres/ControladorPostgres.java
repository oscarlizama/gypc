/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

/**
 *
 * @author ADMIN
 */
@ManagedBean
public class ControladorPostgres {
    private Part archivoSubido;
    private String folder;

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
    
    public void leerArchivo(){
        try(InputStream input=archivoSubido.getInputStream()){
            InputStreamReader isReader= new InputStreamReader(input);
            BufferedReader reader=new BufferedReader(isReader);
            //StringBuffer strBuf=new StringBuffer();
            String texto;
            while ( (texto = reader.readLine()) != null){
                if (texto.trim().startsWith("private") && texto.endsWith(";")){
                    System.out.println("La linea: '" + texto + "' es una declaración de variable de tipo privada");
                }
                
                
            }   
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
