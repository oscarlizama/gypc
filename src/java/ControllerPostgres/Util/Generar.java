/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres.Util;

import ControllerPostgres.Modelo.*;
import java.io.*;
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
     
     public Generar (String ruta){
         setRuta(ruta);
     }
     
     public void generarscript(BD base){       
      
      
        try{
            
            File file= new File(ruta+"\\Script.sql");
            //Si el archivo no existe crea uno nuevo
            if(!file.exists()){
                file.createNewFile();
            }
            //Se prepara el stream
            FileOutputStream fichero=new FileOutputStream(file);            
            //Objeto que permite escribir en el archivo
            PrintWriter pw= new PrintWriter(fichero);
            
            //LLaves foraneas
            for(LlaveForanea fk:base.getLlavesForaneas()){
                String inicio= "alter table ";
                String ondelete="";
                String onupdate="";
                String thija="";
                String tpadre="";
                String ahija="";
                String apadre="";                
                for(Tabla t:base.getTablas()){
                    
                    if(t.getIdTablaPwD().equals(fk.getTablaHijo())){
                        thija=t.getNombreTabla();
                       
                    }
                    if(t.getIdTablaPwD().equals(fk.getTablaPadre())){
                        tpadre=t.getNombreTabla(); 
                        
                    }
                    
                    for(AtributoBD a:t.getAtributoBDs()){
                       
                        if(a.getIdAtributoPwD().equals(fk.getAtributoHijo())){
                            ahija=a.getNombreAtributo();
                            
                        }
                        if(a.getIdAtributoPwD().equals(fk.getAtributoPadre())){
                            apadre=a.getNombreAtributo();
                        }                        
                    }        
                    
                }
                if(fk.getOnDelete().equals("1")) ondelete="restrict"; 
                if(fk.getOnUpdate().equals("1")) onupdate="restrict";
                 
                pw.write(inicio + thija+"\n");               
                pw.write("add constrait "+ fk.getNombreLlave()+" foreign key ("+ahija+")\n");
                pw.write("references "+tpadre+"("+apadre+")\n");
                pw.write("on delete "+ondelete+" on update "+onupdate+";\n"); 
                pw.write("\n");
                pw.flush();
             
                
            }
            fichero.close();
        }catch(IOException e){
           e.printStackTrace();
        }
     }
    
}
