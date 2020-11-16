/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres.Util;
import ControllerPostgres.Modelo.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
    
    public void leerArchivo(){
        

        //Variables para guardar los atributos de la clase AtributoBD
        String idAtributo="", nombreA="", tipoA="", llavePri="",linea="",linea2="",nombreTabla="",idTabla="";
        int longitudA=0, precisionA=0;
        boolean mandatorioA=false;
        
        //Variables apoyo
        boolean dentro=false,dentro2=false,dentro3=false,dentro4=false;
        int inicio, fin,inicio2,ultima2,inicio3,ultima3,n=0,i=0;
        
        //Objeto de BD, sera inicializado cuando se lea el archivo
        BD base;
        /*LISTA UTILIZADA SOLO PARA FINES DE PRUEBA*/
        List<AtributoBD> atributos=new ArrayList<AtributoBD>();
        
        AtributoBD atributoBD;
        List<Tabla> tablas = new ArrayList<Tabla>();
        Tabla tabla;
        
        try{
            
            File nombreArchivo=new File(ruta);
            //Abrir el archivo
            FileInputStream fstream = new FileInputStream(nombreArchivo);
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            
            //Leer el archivo linea a linea
            while ((strLinea = buffer.readLine()) != null){
                
                 //Para Clase BD
                    if(strLinea.startsWith("<?PowerDesigner")){
                        dentro=true;
                        String nombre="", cod="", t;//t es una variable auixliar para separar cadenas
                        while(dentro){
                            //busca el atributo codificacion en la linea
                           if(strLinea.contains("AppLocale")){
                                inicio=strLinea.indexOf("AppLocale");
                                t=strLinea.substring(inicio);
                                Pattern pattern = Pattern.compile("\"[^\"]*+\"([^\"])");
                                Matcher match = pattern.matcher(t);
                                if(match.find()){
                                    cod=match.group(0);
                                    inicio=cod.indexOf("\"")+1;
                                    fin=cod.lastIndexOf("\"");
                                    cod=cod.substring(inicio,fin).trim();                                    
                                }
                           }
                           //busca atributo nombre en la linea 
                           if(strLinea.contains("Name")){
                                inicio=strLinea.indexOf("Name");
                                t=strLinea.substring(inicio);
                                Pattern pattern = Pattern.compile("\"[^\"]*+\"([^\"])");
                                Matcher match = pattern.matcher(t);
                                if(match.find()){
                                    nombre=match.group(0);
                                    inicio=nombre.indexOf("\"")+1;
                                    fin=nombre.lastIndexOf("\"");
                                    nombre=nombre.substring(inicio,fin).trim();                                    
                                }
                                dentro=false;
                           }
                           base = new BD(nombre,cod);
                        }
                        
                     //para clase tabla
                     if(strLinea.trim().contains("<c:Tables>")){
                        dentro3=true;
                        }
                
                       while(dentro3){
                            if(strLinea.trim().contains("</c:Tables>")){
                                dentro3=false;   
                            }
                            
                            if(!strLinea.equals("<c:Columns>")){
                                
                                if(strLinea.trim().contains("<o:Table Id=") || n != 0)
                                {
                                   n++;
                                   if(strLinea.trim().contains("<o:Table Id="))
                                     {
                                     inicio3 = strLinea.indexOf("=");
                                     linea2 = strLinea.substring(inicio3+2);
                                     ultima3 = linea2.indexOf(">");
                                     idTabla = linea2.substring(0, ultima3-1);
                                     
                                     }
                                
                                   if(n > 2)
                                   {
                                    if (strLinea.trim().contains("<a:Name>") &&  !strLinea.trim().contains("PK") && !strLinea.trim().contains("FK") && !strLinea.trim().contains("_")){
                                     inicio2 = strLinea.indexOf(">");
                                     linea = strLinea.substring(inicio2+1);
                                     ultima2 = linea.indexOf("/");
                                     nombreTabla = linea.substring(0, ultima2-1);
                                     n=0;
                                     } 
                                   }
                                }
        
                            }
                            else{
                                tabla=new Tabla(idTabla,nombreTabla);
                                tablas.add(tabla);
                                }
                        strLinea=buffer.readLine();
                    }
                        
                    //Para clase AtributoBD
                    if(strLinea.trim().contains("<c:Columns>")){
                        dentro=true;
                    }
                    while(dentro){
                            if(strLinea.trim().contains("</c:Columns>")){
                                dentro=false;   
                            }
                            if(!strLinea.equals("</o:Column>")){
                                if(strLinea.trim().contains("<o:Column Id=")){//Obtener identificador de columna o atributo
                                    inicio=strLinea.indexOf("\"")+1;
                                    fin=strLinea.lastIndexOf("\"");
                                    idAtributo=strLinea.substring(inicio, fin);
                                    
                                }
                                if(strLinea.trim().contains("<a:Name>")){//Obtener nombre de atributo
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    nombreA=strLinea.substring(inicio, fin);
                                }
                                if(strLinea.trim().contains("<a:DataType>")){//Obtener el tipo de dato del atributo
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    tipoA=strLinea.substring(inicio, fin);
                                }

                                if(strLinea.trim().contains("<a:Length>")){//Obtener la longitud del atributo
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    longitudA=Integer.parseInt(strLinea.substring(inicio, fin));
                                }
                                
                                
                                if(strLinea.trim().contains("<a:Precision>")){//Obtener precision en caso de haberla y si no asignar 0
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    precisionA=Integer.parseInt(strLinea.trim().substring(inicio,fin));

                                }

                                if(strLinea.trim().contains("<a:Column.Mandatory>")){//Obtener mandatorio del atributo
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    if(strLinea.substring(inicio,fin).equals("1")){
                                        mandatorioA=true;
                                    }
                                }
                            }
                            else{//Se crea objeto tipo atributoBD cuando todos los atributos de la tabla han sido leidos
                                atributoBD=new AtributoBD(idAtributo,nombreA,tipoA,longitudA,precisionA,mandatorioA, false, false);
                                atributos.add(atributoBD);
                                precisionA=0;
                                mandatorioA=false;
                            }
                        strLinea=buffer.readLine();
                    }
                    //Verificar cual de los atributos es la llave primaria
                    if(strLinea.trim().equals("<c:Keys>")){
                        dentro2=true;
                        
                    }
                    while(dentro2){
                        if(strLinea.trim().equals("</c:Keys>")){
                                dentro2=false;
                                
                        }
                        //Si la referencia de la llave es igual a el id de un atributo(de la lista que se tiene), se cambia el atributo "llavePrimaria" de false a true
                        if(strLinea.trim().contains("<o:Column Ref=")){
                            inicio=strLinea.indexOf("\"")+1;
                            fin=strLinea.lastIndexOf("\"");
                            llavePri=strLinea.trim().substring(inicio, fin);
                            for(AtributoBD atr: atributos){//Lista de objetos de tipo AtributoBD que ya han sido creados
                                if(atr.getIdAtributoPwD().equals(llavePri)){
                                    atr.setLlavePrimaria(true);
                                }  
                            }
                        }
                        strLinea=buffer.readLine();   
                    }
                    
                   
                       
                    }
                }
                // Cerramos el archivo
                entrada.close();
        }catch(Exception ex){
            //Excepciones
            System.err.println("Ocurrio un error: "+ex.getMessage());
            ex.printStackTrace();
        }
    }
}
