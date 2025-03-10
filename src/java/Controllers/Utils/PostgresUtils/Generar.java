/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Utils.PostgresUtils;

import Models.PostgresModels.BD;
import Models.PostgresModels.AtributoBD;
import Models.PostgresModels.LecturaBD;
import Models.PostgresModels.LlaveForanea;
import Models.PostgresModels.Tabla;
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
     
     public static File generarscript(LecturaBD lectura){       
      BD base=lectura.getBaseDeDatos();
      
        try{
            String rutaL = "";
            File file= new File("Script.sql");
            //Si el archivo no existe crea uno nuevo
            if(!file.exists()){
                file.createNewFile();
            }
            //Se prepara el stream
            FileOutputStream fichero=new FileOutputStream(file);            
            //Objeto que permite escribir en el archivo
            PrintWriter pw= new PrintWriter(fichero);
            //Tablas
            String tablas="",nombreTabla="";
            
            for(Tabla tab: base.getTablas()){
                nombreTabla+="drop table "+tab.getNombreTabla().toUpperCase()+";\n";
            }
            pw.write(nombreTabla+"\n");
            
            
            for(Tabla tb: base.getTablas()){
                tablas+="\nCREATE TABLE "+tb.getNombreTabla()+" (\n";
                for(AtributoBD atr: tb.getAtributoBDs()){
                    tablas+="\t"+atr.getNombreAtributo()+"\t"+atr.getTipo()+"\t";
                    if(atr.isMandatorio()==true){
                        tablas+="not null";
                    }
                    else{
                        tablas+="null";
                    }
                    if(atr.isUnico()==true){ 
                        tablas+="\tunique,\n";
                    }
                    else{
                        tablas+=",\n";
                    }
                    
                }
                int conPK = 1;
                for(AtributoBD atr: tb.getAtributoBDs()){
                    if(atr.isLlavePrimaria()==true){
                        if (conPK > 1){
                            tablas+=",\n\tconstraint PK_"+tb.getNombreTabla()+" primary key ("+atr.getNombreAtributo()+")"; 
                        }else{
                            tablas+="\tconstraint PK_"+tb.getNombreTabla()+" primary key ("+atr.getNombreAtributo()+")";                             
                        }   
                        conPK++;
                    }
                }
                tablas+="\n);\n\n";
                
            }
            pw.write(tablas+"\n");
            
            
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
            return file;
        }catch(IOException e){
           e.printStackTrace();
        }
        return null;
     }
    
}
