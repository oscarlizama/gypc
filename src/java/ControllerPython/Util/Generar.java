/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.Modelo.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ADMIN
 */
public class Generar {
    
    String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public Generar(String directorio){
        setRuta(directorio);
    }
    
    public void Escribiendo() {
        
        
 
        try{
            File archivo= new File(ruta+"\\archivo.py");
            if(!archivo.exists()){
                archivo.createNewFile();
                //System.out.println("Creacion de archivo: " + archivo.getName());
            }
            FileWriter linea= new FileWriter(archivo.getAbsoluteFile(), true);
                //clases 
                Iterable<Clase> clases = null;
                for(Clase c: clases){
                    if(c.getGeneralizacion() != ""){
                        linea.write("class "+ c.getNombreClase()+"("+c.getGeneralizacion()+"):\n");
                    }else{
                        linea.write("class "+ c.getNombreClase()+"():\n");
                    }
                    //atributos
                    Iterable<Atributo> atributos = null;
                    for(Atributo a: atributos){
                        if(a.isEstatico()==true){
                            linea.write("\t"+a.getNombreAtributo()+" = None\n");
                        }
                    }
                    //constructor
                    linea.write("\tdef __init__(self");
                    if(c.getGeneralizacion() != ""){
                        //atributos padre
                        Clase padre = null;
                        for(Clase c2:clases){
                            if(c2.getNombreClase()==c.getGeneralizacion())
                                padre=c2;
                        }
                        for(Atributo ap:padre.getAtributos()){
                            linea.write(", "+ap.getNombreAtributo());
                        }
                        for(AtributoRelacion ap:padre.getAtributosDeRelaciones()){
                            linea.write(","+ ap.getNombreAtributo());
                        }
                        
                    }
                    Iterable<AtributoRelacion> aRelaciones = null;
                    for(AtributoRelacion ar: aRelaciones){
                        linea.write(", "+ar.getNombreAtributo());
                    }
                    for(Atributo a:atributos){
                        if(a.isEstatico()==false){
                            if(a.getValorPorDefecto() != ""){
                                linea.write(", "+a.getNombreAtributo()+" = "+a.getValorPorDefecto());
                            }else{
                                linea.write(", "+a.getNombreAtributo()+"= None ");
                            }
                        }
                    }
                    linea.write("):\n");
                    if(c.getGeneralizacion()!=""){
                        //constructor de padre
                        linea.write("\t\t"+c.getGeneralizacion()+".__init__(self");
                        //atributos del padre
                        Clase padre = null;
                        for(Clase c2:clases){
                            if(c2.getNombreClase()==c.getGeneralizacion())
                                padre=c2;
                        }
                        for(Atributo ap:padre.getAtributos()){
                            linea.write(", "+ap.getNombreAtributo());
                        }
                        for(AtributoRelacion ap:padre.getAtributosDeRelaciones()){
                            linea.write(","+ ap.getNombreAtributo());
                        }
                        linea.write(")\n");
                        
                    }
                    //visibilidad de los atributos
                    for(Atributo a: atributos){
                        if(a.getVisibilidad()=="public"){
                            linea.write("\t\tself."+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                        }else{
                            if(a.getVisibilidad()=="protected"){
                                linea.write("\t\tself._"+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                            }else{
                                if(a.getVisibilidad()=="private"){
                                linea.write("\t\tself.__"+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                        }
                            }
                        }
                    }
                    Iterable<Metodo> metodos = null;
                    for(Metodo m: metodos){
                        if(m.getEstatico()=="true"){
                            linea.write("\t@staticmethod\n");
                            if(m.getVisibilidad()=="public"){
                                linea.write("\tdef."+m.getNombreMetodo()+"(");
                            }else{
                                if(m.getVisibilidad()=="protected"){
                                    linea.write("\tdef._"+m.getNombreMetodo()+"(");
                                }else{
                                    if(m.getVisibilidad()=="private"){
                                        linea.write("\tdef.__"+m.getNombreMetodo()+"(");
                                    }
                                }
                            }
                            //parametros si es estatico
                            Iterable<Parametro> parametros = null;
                            for(Parametro pa: parametros){

                                linea.write(pa.getNombreParametro());
                                if(parametros.iterator().hasNext()){
                                    linea.write(","+pa.getNombreParametro());
                                }
                            }
                            linea.write("):\n");
                            
                        }else{
                            if(m.getVisibilidad()=="public"){
                                linea.write("\tdef."+m.getNombreMetodo()+"(self");
                            }else{
                                if(m.getVisibilidad()=="protected"){
                                    linea.write("\tdef._"+m.getNombreMetodo()+"(self");
                                }else{
                                    if(m.getVisibilidad()=="private"){
                                        linea.write("\tdef.__"+m.getNombreMetodo()+"(self");
                                    }
                                }
                            }
                            //parametros
                            Iterable<Parametro> parametros = null;
                            for(Parametro pa: parametros){

                                linea.write(","+pa.getNombreParametro()); 
                            }
                            linea.write("):\n");
                            
                        }
                        //tipo retorno
                        if(m.getRetornaTipo()=="int"){
                            linea.write("\t\treturn 0\n");
                        }else{
                            if(m.getRetornaTipo()=="String"){
                                linea.write("\t\treturn \"\" \n");
                            }else{
                                if(m.getRetornaTipo()=="null"){
                                    linea.write("\t\tpass\n");
                                }
                            }
                        }
                    }
                    
                    
                }
                
                //linea.write("nueva linea3");
                linea.close();
                //System.out.println("linea agregada...");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
