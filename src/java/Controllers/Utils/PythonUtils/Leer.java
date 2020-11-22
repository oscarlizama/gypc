/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Utils.PythonUtils;

import Models.PythonModels.LecturaUML;
import Models.PythonModels.Parametro;
import Models.PythonModels.Relacion;
import Models.PythonModels.Metodo;
import Models.PythonModels.Clase;
import Models.PythonModels.Atributo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ADMIN
 */
public class Leer {
    
    
    //Definición de métodos
    
    public static LecturaUML leerArchivo(InputStream inpS){
        LecturaUML lec = new LecturaUML();
        InputStreamReader isReader = new InputStreamReader(inpS);
        BufferedReader buffer = new BufferedReader(isReader);
        String data;
        
        try {
            Scanner myReader = new Scanner(inpS);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine().trim();
                //Verificando que el inicio de linea sea de una clase
                if(data.startsWith("<packagedElement") && data.contains("xmi:type=\"uml:Class\"")){
                    //Declarando variable pattern y matcher 
                    Pattern pattern;
                    Matcher matcher;
                    //Declarando Clase
                    Clase c = new Clase();
                    //Extrayendo datos de clase
                    pattern = Pattern.compile("id=\"(.*?)\"");
                    matcher = pattern.matcher(data);
                    if(matcher.find()) {
                        c.setIdClaseStarUML(matcher.group(1));
                    }
                    pattern = Pattern.compile("name=\"(.*?)\"");
                    matcher = pattern.matcher(data);
                    while(matcher.find()) {
                        c.setNombreClase(matcher.group(1));
                    }
                    pattern = Pattern.compile("visibility=\"(.*?)\"");
                    matcher = pattern.matcher(data);
                    while(matcher.find()) {
                        c.setVisibilidad(matcher.group(1));
                    }
                    pattern = Pattern.compile("isAbstract=\"(.*?)\" ");
                    matcher = pattern.matcher(data);
                    while(matcher.find()) { 
                        if(matcher.group(1)== "true"){
                            c.setAbstracto(true);
                        }else{c.setAbstracto(false);}
                    }
                    
                    //Verificando si la clase está vacía
                    boolean claseVacia = false;
                    if (data.endsWith("/>")){
                        claseVacia = true;
                    }else{  //La clase contiene alguna definición dentro de ella
                        data = myReader.nextLine().trim();    //Salta a la siguiente línea
                    }
                    
                    //Entorno de Clase
                    while(!data.startsWith("</packagedElement>") && claseVacia == false){
                        
                        //Caso Atributo
                        if (data.startsWith("<ownedAttribute")){
                            //Declarando Atributo
                            Atributo attr = new Atributo();
                            //Extrayendo datos de atributo
                            pattern = Pattern.compile(" name=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                attr.setNombreAtributo(matcher.group(1));
                            }
                            pattern = Pattern.compile(" visibility=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                attr.setVisibilidad(matcher.group(1));
                            }
                            pattern = Pattern.compile("isStatic=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            while(matcher.find()) {
                                if(matcher.group(1).equals("true")){
                                    attr.setEstatico(true);
                                }else{attr.setEstatico(false);}
                            }
                            pattern = Pattern.compile(" type=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                attr.setTipo(matcher.group(1));
                            }
                            //Verificando si la etiq se cierre en la misma linea, de lo contrario, especifica valor por defecto
                            if (!data.endsWith("/>")){
                                data = myReader.nextLine().trim();    //Salto de linea, al valor por defecto
                                pattern = Pattern.compile("value=\"(.*?)\"");
                                matcher = pattern.matcher(data);
                                while(matcher.find()) {
                                    attr.setValorPorDefecto(matcher.group(1));
                                }
                                data = myReader.nextLine().trim();    //Salto de linea, al fin de etiqueta (solo 1 valor por def)
                            }
                            c.addAtributo(attr);
                        }//Fin Caso Atributo
                        
                        //Caso Metodo
                        if (data.startsWith("<ownedOperation")){
                            //Declarando Método
                            Metodo mth = new Metodo();
                            //Extrayendo datos del método
                            pattern = Pattern.compile(" name=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                mth.setNombreMetodo(matcher.group(1));
                            }
                            pattern = Pattern.compile(" visibility=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                mth.setVisibilidad(matcher.group(1));
                            }
                            pattern = Pattern.compile("isStatic=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            while(matcher.find()) {
                                mth.setEstatico(matcher.group(1));
                            }
                            //Verificando si la etiq se cierra en la misma linea, de lo contrario, lleva parametros/tipo Retorno
                            if (!data.endsWith("/>")){
                                data = myReader.nextLine().trim();    //Salto de linea, a la sig etiq (parametro/retorno)
                                while(!data.startsWith("</ownedOperation>")){   //Entorno del método
                                    //Caso Retorna tipo
                                    if(data.startsWith("<ownedParameter") && data.contains("direction=\"return\"")){
                                        pattern = Pattern.compile(" type=\"(.*?)\" ");
                                        matcher = pattern.matcher(data);
                                        if(matcher.find()) {
                                            mth.setRetornaTipo(matcher.group(1));
                                        }
                                    }
                                    //Caso Parametro
                                    if(data.startsWith("<ownedParameter") && data.contains("direction=\"in\"")){
                                        //Declarando Parámetro
                                        Parametro param = new Parametro();
                                        //Extrayendo datos de parámetro
                                        pattern = Pattern.compile("name=\"(.*?)\"");
                                        matcher = pattern.matcher(data);
                                        while(matcher.find()) {  
                                            param.setNombreParametro(matcher.group(1));
                                        }
                                        pattern = Pattern.compile(" type=\"(.*?)\" ");
                                        matcher = pattern.matcher(data);
                                        while(matcher.find()) {  
                                            param.setTipoParametro(matcher.group(1));
                                        }
                                        mth.addParametro(param);
                                    } //Fin lectura Parámetro
                                    data = myReader.nextLine().trim();
                                }//Fin entorno metodo
                            }
                            c.addMetodo(mth);
                        }//Fin Caso Metodo
                        
                        //Caso Generalización
                        if (data.startsWith("<generalization")){
                            pattern = Pattern.compile("general=\"(.*?)\"");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                c.setGeneralizacion(matcher.group(1));
                            }
                        }//Fin Caso Generalizacion
                        
                        //Caso Relacion
                        if(data.startsWith("<ownedMember")){
                            //Declarando Objeto Relacion
                            Relacion rel = new Relacion();
                            //Extrayendo datos de primera linea
                            pattern = Pattern.compile("name=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                rel.setNombreRelacion(matcher.group(1));
                            }
                            pattern = Pattern.compile("visibility=\"(.*?)\"");
                            matcher = pattern.matcher(data);
                            while(matcher.find()) {
                                rel.setVisibilidad(matcher.group(1));
                            }
                            //Estableciendo por defecto el tipo de asociación como Associación
                            rel.setTipoRel("Association");
                            //Dando Salto de Línea
                            data = myReader.nextLine().trim();    //Salto de linea, al contenido de ownedMember
                            //La primera línea despues de un <ownedMember, es un <ownedEnd, de la clase origen
                            //Leer agregacion y tipo(Id Clase Origen), static
                            pattern = Pattern.compile("aggregation=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                String res = matcher.group(1);
                                if (res.equals("shared")){ rel.setTipoRel("shared");}
                                else if (res.equals("composite")) {rel.setTipoRel("composite");}                                
                            }
                            pattern = Pattern.compile(" type=\"(.*?)\"");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                rel.setClaseOrigen(matcher.group(1));
                            }
                            pattern = Pattern.compile("isStatic=\"(.*?)\" ");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                if (matcher.group(1).equals("true")){rel.setEstatico(true);}
                                else {rel.setEstatico(false);}                                
                            }
                            //Dando Salto de Línea
                            data = myReader.nextLine().trim(); //Salto de Línea, contenido de Punta Relacion
                            //Entorno de Punta Relacion
                            while(!data.startsWith("</ownedEnd>")){
                                //Caso Mult Baja
                                if (data.startsWith("<lowerValue")){
                                    pattern = Pattern.compile("value=\"(.*?)\"");
                                    matcher = pattern.matcher(data);
                                    if(matcher.find()) {
                                    rel.setMultBajaOrigen(matcher.group(1));
                                    }
                                }
                                //Caso Mult Alta
                                if (data.startsWith("<upperValue")){
                                    pattern = Pattern.compile("value=\"(.*?)\"");
                                    matcher = pattern.matcher(data);
                                    if(matcher.find()) {
                                    rel.setMultAltaOrigen(matcher.group(1));
                                    }
                                }
                                data = myReader.nextLine().trim(); //Salto de Línea, entorno punta
                            }//Fin Entorno Punta
                            data = myReader.nextLine().trim(); //Salto de Línea, <ownedEnd Destino
                            //Extrayendo datos de Punta destino
                            pattern = Pattern.compile(" type=\"(.*?)\"");
                            matcher = pattern.matcher(data);
                            if(matcher.find()) {
                                rel.setClaseDestino(matcher.group(1));
                            }
                            data = myReader.nextLine().trim(); //Salto de Línea, contenido de Punta Relacion
                            //Entorno de Punta Relacion
                            while(!data.startsWith("</ownedEnd>")){
                                //Caso Mult Baja
                                if (data.startsWith("<lowerValue")){
                                    pattern = Pattern.compile("value=\"(.*?)\"");
                                    matcher = pattern.matcher(data);
                                    if(matcher.find()) {                                        
                                        rel.setMultBajaDestino(matcher.group(1));
                                    }
                                }
                                //Caso Mult Alta
                                if (data.startsWith("<upperValue")){
                                    pattern = Pattern.compile("value=\"(.*?)\"");
                                    matcher = pattern.matcher(data);
                                    if(matcher.find()) {
                                    rel.setMultAltaDestino(matcher.group(1));
                                    }
                                }
                                data = myReader.nextLine().trim(); //Salto de Línea, entorno punta
                            }//Fin Entorno Punta
                            if (rel.getNombreRelacion() == null || rel.getNombreRelacion().isEmpty()){
                                String nameGen = "RelDef";
                                rel.setNombreRelacion(nameGen);
                            }
                            lec.addRelaciones(rel);
                        }//Fin Caso Relacion
                        
                        //Dando el siguiente salto
                        data = myReader.nextLine().trim();    //Salta a la siguiente línea
                    }//Fin de entorno de clase
                    //Añadiendo clase
                    lec.addClases(c);
                }//Fin de while global                                            
                
                
            }
        } catch (Exception ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lec;
    }
}
