/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.Modelo.*;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Validar {
    
    public static LecturaUML validar(LecturaUML lecOriginal){
        LecturaUML lecVal = new LecturaUML();
        //Cambiando campo generalización a nombre        
        for(Clase c:lecOriginal.getClases()){
            Clase cLV = c;
            if (c.getGeneralizacion() != null){
                String gen = c.getGeneralizacion();
                String clase;
                //Buscando y reemplazando
                for(Clase cBus:lecOriginal.getClases()){
                    if(gen.equals(cBus.getIdClaseStarUML())){
                        clase = cBus.getNombreClase();
                        cLV.setGeneralizacion(clase);
                    }
                }
            }
            lecVal.addClases(cLV);
        }
        //Cambiando Tipo NO
        //Generando nombreRelacion
        for(Relacion r:lecOriginal.getRelaciones()){
            Relacion rLV = r;
            //Cambiar nombre a Origen y Destino
            String origen = r.getClaseOrigen();
            String destino = r.getClaseDestino();
            for(Clase cBus:lecOriginal.getClases()){
                if(origen.equals(cBus.getIdClaseStarUML())){
                    String clase = cBus.getNombreClase();
                    rLV.setClaseOrigen(clase);
                }
                if(destino.equals(cBus.getIdClaseStarUML())){
                    String clase = cBus.getNombreClase();
                    rLV.setClaseDestino(clase);
                }
            }
            //Cambiando nombre a nombreRelacion
            if (r.getNombreRelacion().equals("RelDef")){
                rLV.setNombreRelacion("rel" + rLV.getClaseOrigen() + rLV.getClaseDestino());
            }
            //Caso espacios
            if (r.getNombreRelacion().contains("%20")){
                rLV.setNombreRelacion(r.getNombreRelacion().replace("%20", "_"));
            }
            lecVal.addRelaciones(rLV);
        }
        //Generando AtributoRelacion       
        return lecVal;
    }
    
    public static void imprimirClases(LecturaUML l){
        for (Clase c:l.getClases()){
            String clase = "";
            clase += "NombreClase "+ c.getNombreClase();
            clase += " Visibilidad Clase " + c.getVisibilidad();
            clase += " IdStarUML "+c.getIdClaseStarUML();
            clase += " Generalizacion " + c.getGeneralizacion();
            System.out.println(clase);
            //Imprimiendo atributos
            for(Atributo a:c.getAtributos()){
                String atrib = "";
                atrib += " NombreAtributo " + a.getNombreAtributo();
                atrib += " Visibilidad Attr " +a.getVisibilidad();
                atrib += " ValorDefecto " +a.getValorPorDefecto();
                atrib += " Estatico: " + a.isEstatico();
                atrib += " TipoAttr " +a.getTipo();
                System.out.println(atrib);
            }
            for(AtributoRelacion ar:c.getAtributosDeRelaciones()){
                
            }
            for(Metodo m:c.getMetodos()){
                String metodo = "";
                metodo += " NombreMetodo " + m.getNombreMetodo();
                metodo += " Visibilidad " +m.getVisibilidad();
                metodo += " Estatico " +m.getEstatico();
                metodo += " RetornaTipo " +m.getRetornaTipo();
                System.out.println(metodo);
                for (Parametro p:m.getParametros()){
                    String param = "";
                    param += " NombreParametro " +p.getNombreParametro();
                    param += " TipoParam " + p.getTipoParametro();
                    System.out.println(param);
                }
            }
            System.out.println("--------------------------------");
        }
        System.out.println("----RELACIONES----");
        for (Relacion r:l.getRelaciones()){
            System.out.println("_______");
            String Rels = "";
            Rels += "NombreRel: " + r.getNombreRelacion();
            Rels += ", TipoRel: " + r.getTipoRel();
            Rels += ", Visibilidad: " + r.getVisibilidad();
            Rels += "\n Clase Origen: " + r.getClaseOrigen();
            Rels += ", Multiplicidad: " + r.getMultBajaOrigen() +"  " + r.getMultAltaOrigen();
            Rels += "\n Clase Destino: " + r.getClaseDestino();
            Rels += ", Multiplicidad: " + r.getMultBajaDestino() +"  " + r.getMultAltaDestino();
            System.out.println(Rels);
            System.out.println("_______");
        }
        
    }
    
}
