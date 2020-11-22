/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Util;

import ControllerPython.Modelo.*;

/**
 *
 * @author ADMIN
 */
public class Validar {
    
    
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
        for (Relacion r:l.getRelaciones()){
            
        }
    }
    
}
