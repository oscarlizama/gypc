/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.Modelo.*;


import java.io.File;  // Import the File class

import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 *
 * @author ADMIN
 */

public class Leer {
    String ruta;
 
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public Leer(String archivo){
        setRuta(archivo);
    }
  
    public static void main(String[] args) {

        
    try {
        String id,nombre,visi;
        boolean abs;
         
      File myObj = new File("C:/Users/Anabel/Desktop/Entradas GeneradorPython/Clases_Relaciones.xmi");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {   
        String data = myReader.nextLine();
        String data2=data.substring(1);
        
        if(data2=="packagedElement"){
            //buscar clase
            Pattern pattern = Pattern.compile("id=\"(.*?)\"");
            Matcher matcher = pattern.matcher(data);
            if(matcher.matches()) {
                id = matcher.group(1);
            }
            Pattern pattern1 = Pattern.compile("name=\"(.*?)\"");
            Matcher matcher1 = pattern1.matcher(data);
            while(matcher1.find()) {
                nombre= matcher1.group(1);
            }
            Pattern pattern2 = Pattern.compile("visibility=\"(.*?)\"");
            Matcher matcher2 = pattern2.matcher(data);
            while(matcher2.find()) {
                visi= matcher2.group(1);
            }
            Pattern pattern3 = Pattern.compile("isAbstract=\"(.*?)\" ");
            Matcher matcher3 = pattern3.matcher(data);
            while(matcher3.find()) {
               if(matcher3.group(1)=="true"){
                   abs = true;
               }else{
                   abs = false;
               }
               
            }
            while(data2!="/packagedElement>"){
                if(data2=="ownedAttribute"){
                    //buscar atributo
                    Pattern pattern4 = Pattern.compile(" name=\"(.*?)\" ");
                    Matcher matcher4 = pattern4.matcher(data);
                    if(matcher4.matches()) {
                    System.out.println(matcher4.group(1));
                    }
                }else {
                    if(data2=="ownedOperation"){
                        //buscar operacion
                        Pattern pattern5 = Pattern.compile(" name=\"(.*?)\" ");
                        Matcher matcher5 = pattern5.matcher(data);
                        if(matcher5.matches()) {
                            System.out.println(matcher5.group(1));
                        }
                    }else{
                        System.out.println(data);
                    }

                }
            }  
        }
        
        //System.out.println(data);
      }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    
  }
  

}
