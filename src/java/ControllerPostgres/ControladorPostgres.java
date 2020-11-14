/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres;

import ControllerPostgres.Modelo.AtributoBD;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
        //Variables para guardar los atributos de la clase AtributoBD
        String idAtributo="", nombreA="", tipoA="", llavePri="";
        int longitudA=0, precisionA=0;
        boolean mandatorioA=false;
        
        //Variables apoyo
        boolean dentro=false,dentro2=false;
        int inicio, fin;
        
        /*LISTA UTILIZADA SOLO PARA FINES DE PRUEBA*/
        List<AtributoBD> atributos=new ArrayList<AtributoBD>();
        AtributoBD atributoBD;
        
        try(InputStream input=archivoSubido.getInputStream()){
            InputStreamReader isReader= new InputStreamReader(input);
            BufferedReader reader=new BufferedReader(isReader);
            //StringBuffer strBuf=new StringBuffer();
            String strLinea;
            
            while ( (strLinea = reader.readLine()) != null) {
                    /*PARA CLASE AtributoBD*/
                    if(strLinea.trim().contains("<c:Columns>")){
                        dentro=true;
                    }
                    while(dentro){
                        if(strLinea.trim().contains("</c:Columns>")){
                            dentro=false;   
                        }
                        if(!strLinea.equals("</o:Column>")){
                            if(strLinea.trim().contains("<o:Column Id=")){//Obtener identificador
                                inicio=strLinea.indexOf("\"")+1;
                                fin=strLinea.lastIndexOf("\"");
                                idAtributo=strLinea.substring(inicio, fin);
                            }
                            if(strLinea.trim().contains("<a:Name>")){//Obtener nombre de atributo
                                inicio=strLinea.indexOf(">")+1;
                                fin=strLinea.lastIndexOf("<");
                                nombreA=strLinea.substring(inicio, fin);
                            }
                            if(strLinea.trim().contains("<a:DataType>")){//Obtener el tipo
                                inicio=strLinea.indexOf(">")+1;
                                fin=strLinea.lastIndexOf("<");
                                tipoA=strLinea.substring(inicio, fin);
                            }
                            if(strLinea.trim().contains("<a:Length>")){//Obtener la longitud
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
                        else{
                            atributoBD=new AtributoBD(idAtributo,nombreA,tipoA,longitudA,precisionA,mandatorioA, false, false);
                            atributos.add(atributoBD);
                            precisionA=0;
                            mandatorioA=false;
                        }
                        strLinea=reader.readLine();
                    }
                    //Verificar cual de los atributos es la llave primaria
                    if(strLinea.trim().equals("<c:Keys>")){
                        dentro2=true;
                        
                    }
                    while(dentro2){
                        if(strLinea.trim().equals("</c:Keys>")){
                                dentro2=false;
                                
                        }
                        if(strLinea.trim().contains("<o:Column Ref=")){
                            inicio=strLinea.indexOf("\"")+1;
                            fin=strLinea.lastIndexOf("\"");
                            llavePri=strLinea.trim().substring(inicio, fin);
                            for(AtributoBD atr: atributos){
                                if(atr.getIdAtributoPwD().equals(llavePri)){
                                    atr.setLlavePrimaria(true);
                                }  
                            }
                        }
                        strLinea=reader.readLine();   
                    }
            }   
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
