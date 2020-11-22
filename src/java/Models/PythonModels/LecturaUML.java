/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PythonModels;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class LecturaUML {
    private ArrayList<Clase> clases = new ArrayList<>();
    private ArrayList<Relacion> relaciones = new ArrayList<>();
    
    public ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(ArrayList<Relacion> relaciones) {
        this.relaciones = relaciones;
    }
    
    public ArrayList<Clase> getClases() {
        return clases;
    }

    public void setClases(ArrayList<Clase> clases) {
        this.clases = clases;
    }
    
    public void addClases(Clase c){
        this.clases.add(c);
    }
    
    public void clearClases(){
        this.clases.clear();
    }
    
    public void clearRelaciones(){
        this.relaciones.clear();
    }
    
    public void addClases(String idClaseStarUML, String nombreClase, String visibilidad, boolean Abstracto){
        Clase c = new Clase(idClaseStarUML, nombreClase, visibilidad, Abstracto);
        this.clases.add(c);
    }
    
    public void addRelaciones(Relacion r){
        this.relaciones.add(r);
    }
    
    public void addRelaciones(String claseOrigen, String claseDestino, String nombreRelacion, String multBajaOrigen, String multAltaOrigen, String multBajaDestino, String multAltaDestino, String visibilidad, boolean estatico){
        Relacion r = new Relacion(claseOrigen, claseDestino, nombreRelacion, multBajaOrigen, multAltaOrigen, multBajaDestino, multAltaDestino, visibilidad, estatico);
        this.relaciones.add(r);
    }
}
