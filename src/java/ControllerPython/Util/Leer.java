/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.ControladorPython;
import ControllerPython.Modelo.*;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author ADMIN
 */

public class Leer {
    String ruta;
    Clase clase;
    List<Clase> listaClases = new ArrayList<Clase>();
    Atributo atributo;
    Metodo metodo;

 
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public Leer(String archivo){
        setRuta(archivo);
    }
    public void LeerArchivo(){
        String id = null,nombre = null ,visi=null;
        boolean abs = false;
        String nombreA=null,visiA=null,tipo=null, val=null;
        boolean estA= false;
        String nombreM=null,visiM=null,estM=null, reto=null;
        String nombreP=null,tipoP=null;
        
        try {
            File myObj = new File(ruta);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {   
                String data = myReader.nextLine().trim();
                if(data.startsWith("<packagedElement") && data.contains("xmi:type=\"uml:Class\"")){
                    //System.out.println("------------------------------------");
                    //buscar clase
                    Pattern pattern = Pattern.compile("id=\"(.*?)\"");
                    Matcher matcher = pattern.matcher(data);
                    if(matcher.find()) {
                        id = matcher.group(1);
                        //System.out.println("Id Clase: " + id);
                    }
                    Pattern pattern1 = Pattern.compile("name=\"(.*?)\"");
                    Matcher matcher1 = pattern1.matcher(data);
                    while(matcher1.find()) {
                        nombre= matcher1.group(1);
                        //System.out.println("nombreClase: " + nombre);
                    }
                    Pattern pattern2 = Pattern.compile("visibility=\"(.*?)\"");
                    Matcher matcher2 = pattern2.matcher(data);
                    while(matcher2.find()) {
                        visi= matcher2.group(1);
                        //System.out.println("VisibilidadClase: " + visi);
                    }
                    Pattern pattern3 = Pattern.compile("isAbstract=\"(.*?)\" ");
                    Matcher matcher3 = pattern3.matcher(data);
                    while(matcher3.find()) { 
                        if(matcher3.group(1)=="true"){
                            abs = true;
                        }else{
                            abs = false;
                        }
                        //System.out.println("esAbstracta: " + abs);
                    }
                    listaClases.add(new Clase(id,nombre,visi,abs));
                    
                    //Verificando caso especial de cierre en la misma linea
                    boolean ignorarWhile = false;
                    if (data.endsWith("/>")){
                        ignorarWhile = true;
                    }
                    //Siguiente linea dentro de la definición de clase
                    System.out.println("Entrando a clase------");
                    data = myReader.nextLine().trim();
                    while(!data.startsWith("</packagedElement>") && ignorarWhile == false){
                        //System.out.println(data);
                        if(data.startsWith("<ownedAttribute")){
                            //buscar atributo
                            Pattern pattern4 = Pattern.compile(" name=\"(.*?)\" ");
                            Matcher matcher4 = pattern4.matcher(data);
                            if(matcher4.find()) {
                                nombreA=matcher4.group(1);
                                //System.out.println("nombreAtributo " + matcher4.group(1));
                            }
                            Pattern pattern6 = Pattern.compile(" visibility=\"(.*?)\" ");
                            Matcher matcher6 = pattern6.matcher(data);
                            if(matcher6.find()) {
                                //System.out.println("visibilidad " + matcher6.group(1));
                                visiA=matcher6.group(1);
                            }
                            Pattern pattern7 = Pattern.compile("isStatic=\"(.*?)\" ");
                            Matcher matcher7 = pattern7.matcher(data);
                            while(matcher7.find()) {
                                if(matcher7.group(1)=="true"){
                                    estA = true;
                                }else{
                                    estA = false;
                                }
                                //System.out.println("Estatico: " + estA);
                            }  
                            Pattern pattern8 = Pattern.compile(" type=\"(.*?)\" ");
                            Matcher matcher8 = pattern8.matcher(data);
                            if(matcher8.find()) {
                                //System.out.println("tipo " + matcher8.group(1));
                                tipo=matcher8.group(1);
                            }
                            
                        }else {
                            if(data.startsWith("<defaultValue")){
                                Pattern pattern9 = Pattern.compile("value=\"(.*?)\"");
                                Matcher matcher9 = pattern9.matcher(data);
                                while(matcher9.find()) {
                                    val= matcher9.group(1);
                                    //System.out.println("valor por defecto: " + val);
                                }
                                clase.addAtributo(nombreA,visiA,estA,tipo,val);
                            }else{
                                if(data.startsWith("<ownedOperation")){
                                    //buscar operacion
                                    Pattern pattern5 = Pattern.compile(" name=\"(.*?)\" ");
                                    Matcher matcher5 = pattern5.matcher(data);
                                    if(matcher5.find()) {
                                        nombreM=matcher5.group(1);
                                        //System.out.println("nombreOperacion " + matcher5.group(1));
                                    }
                                    Pattern pattern10 = Pattern.compile(" visibility=\"(.*?)\" ");
                                    Matcher matcher10 = pattern10.matcher(data);
                                    if(matcher10.find()) {
                                        visiM=matcher10.group(1);
                                        //System.out.println("visibilidad " + matcher10.group(1));
                                    }
                                    Pattern pattern11 = Pattern.compile("isStatic=\"(.*?)\" ");
                                    Matcher matcher11 = pattern11.matcher(data);
                                    while(matcher11.find()) {
                                        estM =matcher11.group(1);
                                        //System.out.println("es estatico: " + abs);
                                    }
                                }else{
                                    if(data.startsWith("<ownedParameter")){
                                        Pattern pattern12 = Pattern.compile(" direction=\"(.*?)\" ");
                                        Matcher matcher12 = pattern12.matcher(data);
                                        if(matcher12.find()) {
                                            reto= matcher12.group(1);
                                            //System.out.println("retorna tipo " + matcher12.group(1));
                                        }
                                    }else{
                                        //if(data.startsWith("<packagedElement") && data.contains("xmi:type=\"uml:Class\""))
                                        if(data.startsWith("<ownedParameter") && data.contains("name")){
                                            Pattern pattern13 = Pattern.compile("name=\"(.*?)\"");
                                            Matcher matcher13 = pattern13.matcher(data);
                                            while(matcher13.find()) {  
                                                nombreP= matcher13.group(1);
                                                //System.out.println("nombre Parametro: " + nombreP);
                                            }
                                            Pattern pattern14 = Pattern.compile("type=\"(.*?)\"");
                                            Matcher matcher14 = pattern14.matcher(data);
                                            while(matcher14.find()) {  
                                                tipoP= matcher14.group(1);
                                                //System.out.println("tipo Parametro: " + nombreP);
                                            }    
                                        }
                                        //
                                        Parametro pa1= new Parametro(nombreP,tipoP);
                                        metodo.addParametro(pa1);
                                    }
                                    clase.addMetodo(nombreM, visiA, estM, reto);
                                    // La etiqueta comienza con otra cosa
                                    //System.out.println(data);
                                }
                            }
                        }
                        data = myReader.nextLine().trim();
                    }  
                    //System.out.println("Saliendo a clase------");
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
