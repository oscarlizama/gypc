/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.Modelo.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    
    public static File Escribiendo(LecturaUML lec) {
        try{
            File archivo= new File("archivo1.py");
            if(!archivo.exists()){
                archivo.createNewFile();
                //System.out.println("Creacion de archivo: " + archivo.getName());
            }
            //FileWriter linea= new FileWriter(archivo.getAbsoluteFile(), true);
            //Se prepara el stream
            FileOutputStream fichero=new FileOutputStream(archivo);            
            //Objeto que permite escribir en el archivo
            PrintWriter linea= new PrintWriter(fichero);
                //clases 
                Iterable<Clase> clases = lec.getClases();
                for(Clase c: clases){
                    if(c.getGeneralizacion() != "" && c.getGeneralizacion() != null){
                        linea.write("class "+ c.getNombreClase()+"("+c.getGeneralizacion()+"):\n");
                    }else{
                        linea.write("class "+ c.getNombreClase()+"():\n");
                    }
                    //atributos
                    Iterable<Atributo> atributos = c.getAtributos();
                    for(Atributo a: atributos){
                        if(a.isEstatico()==true){
                            if(a.getVisibilidad().equals("public")){
                            linea.write("\t"+a.getNombreAtributo());
                            }else{
                                if(a.getVisibilidad().equals("protected")){
                                    linea.write("\t_"+a.getNombreAtributo());
                                }else{
                                    if(a.getVisibilidad().equals("private")){
                                    linea.write("\t__"+a.getNombreAtributo());
                            }
                                }
                            }
                            if (a.getValorPorDefecto() != "" && a.getValorPorDefecto() != null){
                            linea.write(" = \"" + a.getValorPorDefecto() + "\"\n");
                            }else{linea.write(" = None\n");}
                        }                        
                    }
                    //constructor
                    linea.write("\tdef __init__(self");
                    if(c.getGeneralizacion() != "" && c.getGeneralizacion() != null){
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
                    Iterable<AtributoRelacion> aRelaciones = c.getAtributosDeRelaciones();
                    for(AtributoRelacion ar: aRelaciones){
                        linea.write(", "+ar.getNombreAtributo());
                    }
                    for(Atributo a:atributos){
                        if(a.isEstatico()==false){
                            if(a.getValorPorDefecto() != "" && a.getValorPorDefecto() != null){
                                linea.write(", "+a.getNombreAtributo()+" = \""+a.getValorPorDefecto() + "\"");
                            }else{
                                linea.write(", "+a.getNombreAtributo()+"= None ");
                            }
                        }
                    }
                    linea.write("):\n");
                    //Contenido init
                    if(c.getGeneralizacion()!= "" && c.getGeneralizacion() != null){
                        //constructor de padre
                        linea.write("\t\t"+c.getGeneralizacion()+".__init__(self");
                        //atributos del padre
                        Clase padre = null;
                        for(Clase c2:clases){
                            if(c2.getNombreClase()==c.getGeneralizacion()){
                                padre=c2;                                
                            }
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
                        if(a.getVisibilidad().equals("public")){
                            linea.write("\t\tself."+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                        }else{
                            if(a.getVisibilidad().equals("protected")){
                                linea.write("\t\tself._"+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                            }else{
                                if(a.getVisibilidad().equals("private")){
                                linea.write("\t\tself.__"+a.getNombreAtributo()+" = "+a.getNombreAtributo()+"\n");
                        }
                            }
                        }
                    }
                    
                    Iterable<Metodo> metodos = c.getMetodos();
                    for(Metodo m: metodos){
                        if(m.getEstatico().equals("true")){
                            linea.write("\t@staticmethod\n");
                            if(m.getVisibilidad().equals("public")){
                                linea.write("\tdef "+m.getNombreMetodo()+"(");
                            }else{
                                if(m.getVisibilidad().equals("protected")){
                                    linea.write("\tdef _"+m.getNombreMetodo()+"(");
                                }else{
                                    if(m.getVisibilidad().equals("private")){
                                        linea.write("\tdef __"+m.getNombreMetodo()+"(");
                                    }
                                }
                            }
                            //parametros si es estatico
                            Iterable<Parametro> parametros = m.getParametros();
                            boolean primParEst = true;
                            for(Parametro pa: parametros){
                                if (primParEst){
                                    linea.write(pa.getNombreParametro()); //Primer parámetro
                                    primParEst = false;
                                }else{
                                    linea.write(","+pa.getNombreParametro()); //Segundo y más parametros
                                }
                                
                            }
                            linea.write("):\n");
                            
                        }else{
                            if(m.getVisibilidad().equals("public")){
                                linea.write("\tdef "+m.getNombreMetodo()+"(self");
                            }else{
                                if(m.getVisibilidad().equals("protected")){
                                    linea.write("\tdef _"+m.getNombreMetodo()+"(self");
                                }else{
                                    if(m.getVisibilidad().equals("private")){
                                        linea.write("\tdef __"+m.getNombreMetodo()+"(self");
                                    }
                                }
                            }
                            //parametros
                            Iterable<Parametro> parametros = m.getParametros();
                            for(Parametro pa: parametros){
                                linea.write(","+pa.getNombreParametro()); 
                            }
                            linea.write("):\n");
                            
                        }
                        //tipo retorno
                        if(m.getRetornaTipo()=="int"){
                            linea.write("\t\treturn 0\n");
                        }else{
                            if(m.getRetornaTipo()=="String_id"){
                                linea.write("\t\treturn \"\" \n");
                            }else{
                                if(m.getRetornaTipo()=="null"){
                                    linea.write("\t\tpass\n");
                                }
                                else{linea.write("\t\treturn None\n");}
                            }
                        }
                    }
                    linea.write("\n");
                }
                linea.flush();
                return archivo;            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
