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
    
    public static LecturaBD leerArchivo(InputStream inpS){
        //Variables para guardar los atributos de la clase AtributoBD
        String idAtributo="", nombreA="", tipoA="", llavePri="",linea="",linea2="",nombreTabla="",idTabla="";
        int longitudA=0, precisionA=0;
        boolean mandatorioA=false;
        
        //Variables apoyo
        boolean dentro=false,dentro2=false,dentro3=false,dentro4=false, dentro5=false,dentro6=false;
        int inicio, fin,inicio2,ultima2,inicio3,ultima3,n=0,i=0;
        
        //Objeto de BD, sera inicializado cuando se lea el archivo
        BD base = null;
        /*LISTA UTILIZADA SOLO PARA FINES DE PRUEBA*/
        List<AtributoBD> atributos=new ArrayList<AtributoBD>();
        
        AtributoBD atributoBD;
        //Obajeto de tablas
        Tabla tabla = null;
        LecturaBD lee;
        
        try{
            //File nombreArchivo=new File(ruta);
            //Abrir el archivo
            //FileInputStream fstream = new FileInputStream(nombreArchivo);
            // Creamos el objeto de entrada
            //DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            InputStreamReader isReader = new InputStreamReader(inpS);
            BufferedReader buffer = new BufferedReader(isReader);
            String strLinea;
            
            //Leer el archivo linea a linea
            while ((strLinea = buffer.readLine()) != null){
                 //Para Clase BD
                 if(strLinea.startsWith("<?PowerDesigner")){
                     dentro=true;
                     String nombre="", cod="", t;//t es una variable auixliar para separar cadenas
                     while(dentro){
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
                         }
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
                         if(!nombre.isEmpty()&&!cod.isEmpty()) {
                               dentro=false;
                         }
                     }
                     base=new BD(nombre,cod);

                     strLinea=buffer.readLine();
                 }
                 /*OBTENER TABLAS DEL ARCHIVO DE BASE DE DATOS*/
                 if(strLinea.trim().contains("<c:Tables>")){
                    dentro3=true;
                 }
                 while(dentro3){
                    if(strLinea.trim().contains("</c:Tables>")){
                        dentro3=false;   
                    }
                    if(!strLinea.equals("<c:Columns")){
                        if(strLinea.trim().contains("<o:Table Id=") || n != 0){
                            n++;
                            if(strLinea.trim().contains("<o:Table Id=")){
                                inicio3 = strLinea.indexOf("=");
                                linea2 = strLinea.substring(inicio3+2);
                                ultima3 = linea2.indexOf(">");
                                idTabla = linea2.substring(0, ultima3-1);  
                            }
                            if(n > 2){
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
                    /*OBTENER ATRIBUTOS DE CADA TABLA*/
                    if(strLinea.trim().contains("<c:Columns>")){
                        dentro4=true;
                    }
                    while(dentro4){
                        if(strLinea.trim().contains("</c:Columns>")){
                                dentro4=false;   
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
                        if(strLinea.equals("</o:Column>")){
                            atributoBD=new AtributoBD(idAtributo,nombreA,tipoA,longitudA,precisionA,mandatorioA, false, false);
                            atributos.add(atributoBD);
                            mandatorioA=false;
                            //System.out.println("\nidAtributo:"+idAtributo+"\tnombreA:"+nombreA+"\ttipoA:"+tipoA+"\tlongitud:"+longitudA+"\nprecision:"+precisionA+"\tmandatorio:"+mandatorioA+"\tLlave primaria:"+false+"\tunico:"+false);
                        }
                        strLinea=buffer.readLine();
                    }
                    /*VERIFICAR CUAL DE LOS ATRIBUTOS ES LA LLAVE PRIMARIA*/
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
                    
                    /*FINALIZA LA TABLA Y SE AGREGAN LOS ATRIBUTOS DE ELLA, Y LA TABLA SE AGREGA A LA BASE DE DATOS*/
                    if(strLinea.trim().equals("</o:Table>")){
                        tabla=new Tabla(idTabla, nombreTabla);
                        for(AtributoBD atr:atributos){
                            tabla.addAtributoBD(atr);
                        }
                        
                        base.addTabla(tabla);
                        
                        atributos.clear();
                    }
                    strLinea=buffer.readLine();
                 }
                 /*LLAVES FORANEAS */
                    if(strLinea.contains("<c:References>")){
                       String id="",nom="",tPadre="", tHijo="",aPadre="", aHijo="",onDelete="", onUpdate="",cardinalidad="";
                       dentro = true;  String cont="";
                       
                       while(dentro){
                         
                           if(strLinea.contains("</c:References")) dentro=false;
                           else{                           
                                if(strLinea.startsWith("<o:Reference")){
                                    inicio=strLinea.indexOf("\"")+1;
                                    fin=strLinea.lastIndexOf("\"");
                                    id=strLinea.substring(inicio, fin).trim();                                    
                                }
                                if(strLinea.startsWith("<a:Code")){
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    nom=strLinea.substring(inicio, fin).trim();                                 
                                 }
                                if(strLinea.startsWith("<a:Cardinality")){
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    cardinalidad=strLinea.substring(inicio, fin).trim();                                 
                                }
                                if(strLinea.startsWith("<a:UpdateConstraint")){
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    onUpdate=strLinea.substring(inicio, fin).trim(); 
                                    
                                }
                                if(strLinea.startsWith("<a:DeleteConstraint")){
                                    inicio=strLinea.indexOf(">")+1;
                                    fin=strLinea.lastIndexOf("<");
                                    onDelete=strLinea.substring(inicio, fin).trim();                                 
                                }
                                if(strLinea.startsWith("<c:ParentTable")){
                                    dentro4=true;
                                    while(dentro4){
                                         
                                        if(strLinea.contains("<o:Table ")){
                                            inicio=strLinea.indexOf("\"")+1;
                                            fin=strLinea.lastIndexOf("\"");
                                            tPadre=strLinea.substring(inicio, fin).trim();  
                                            dentro4=false;
                                            break;
                                        }
                                        strLinea=buffer.readLine();
                                    }                                    
                                }
                                if(strLinea.startsWith("<c:ChildTable")){
                                    while(!strLinea.startsWith("</c:ChildTable")){
                                        if(strLinea.startsWith("<o:Table ")){
                                            inicio=strLinea.indexOf("\"")+1;
                                            fin=strLinea.lastIndexOf("\"");
                                            tHijo=strLinea.substring(inicio, fin).trim();  
                                            break;
                                        }   
                                        strLinea=buffer.readLine();
                                    }                                    
                                }
                                if(strLinea.startsWith("<c:Object1")){
                                    while(!strLinea.startsWith("</c:Object1")){
                                        if(strLinea.startsWith("<o:Column")){
                                            inicio=strLinea.indexOf("\"")+1;
                                            fin=strLinea.lastIndexOf("\"");
                                            aPadre=strLinea.substring(inicio, fin).trim();
                                            break;
                                        } 
                                        strLinea=buffer.readLine();
                                    }                                    
                                }
                                if(strLinea.startsWith("<c:Object2")){
                                    while(!strLinea.startsWith("</c:Object2")){
                                        if(strLinea.startsWith("<o:Column")){
                                            inicio=strLinea.indexOf("\"")+1;
                                            fin=strLinea.lastIndexOf("\"");
                                            aHijo=strLinea.substring(inicio, fin).trim();  
                                            
                                            break;
                                        }  
                                        strLinea=buffer.readLine();
                                    }                                    
                                }
                                if(aHijo.equals(cont))
                                {
                                    strLinea=buffer.readLine();
                                    continue;
                                }else{
                                    cont=aHijo;
                                }
                                LlaveForanea fk = new LlaveForanea(id, nom, tPadre, tHijo, aPadre,aHijo ,onDelete, onUpdate, cardinalidad);
                                base.addLlaveForanea(fk);  
                            } 
                            strLinea=buffer.readLine();                            
                        }     
                      
                    }
                }
            
        }catch(Exception ex){
            //Excepciones
            System.err.println("Ocurrio un error: "+ex.getMessage());
            ex.printStackTrace();
        }
        lee=new LecturaBD(base);
        return lee;
    }
}
