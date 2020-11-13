/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython;

import ControllerPython.Util.*;
import ControllerPython.Modelo.*;
import java.io.IOException;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;



/**
 *
 * @author ADMIN
 */
@ManagedBean
public class ControladorPython {

    private Part file;
    private String fileContent;
    
    public static void main(String[] args){

        
    }
    
    public void uploat(){
        try{
            fileContent = new Scanner(file.getInputStream()).useDelimiter("\\A").next();
            Leer lec1 = new Leer(fileContent);
        }catch(IOException e){
            e.printStackTrace();
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
