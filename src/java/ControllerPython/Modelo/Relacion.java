/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Modelo;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Relacion {
    private String claseOrigen; // Primero se guardaran las referencias a los idStarUML
    private String claseDestino;
    private String nombreRelacion;
    private String multBajaOrigen;
    private String multAltaOrigen;
    private String multBajaDestino;
    private String multAltaDestino;
    private String visibilidad;
    private String tipoRel;
    private boolean estatico;
    
    public Relacion(){}
    
    public Relacion(String claseOrigen, String claseDestino, String nombreRelacion, String multBajaOrigen, String multAltaOrigen, String multBajaDestino, String multAltaDestino, String visibilidad, boolean estatico) {
        this.claseOrigen = claseOrigen;
        this.claseDestino = claseDestino;
        this.nombreRelacion = nombreRelacion;
        this.multBajaOrigen = multBajaOrigen;
        this.multAltaOrigen = multAltaOrigen;
        this.multBajaDestino = multBajaDestino;
        this.multAltaDestino = multAltaDestino;
        this.visibilidad = visibilidad;
        this.estatico = estatico;
    }
    
    public String getTipoRel() {
        return tipoRel;
    }

    public void setTipoRel(String tipoRel) {
        this.tipoRel = tipoRel;
    }    
    
    public void transformarClases(ArrayList<Clase> clases){
        //Metodo que toma el parametro this.claseOrigen y this.claseDestino, las lee y sustituye por el nombreClase
    }

    public String getClaseOrigen() {
        return claseOrigen;
    }

    public void setClaseOrigen(String claseOrigen) {
        this.claseOrigen = claseOrigen;
    }

    public String getClaseDestino() {
        return claseDestino;
    }

    public void setClaseDestino(String claseDestino) {
        this.claseDestino = claseDestino;
    }

    public String getNombreRelacion() {
        return nombreRelacion;
    }

    public void setNombreRelacion(String nombreRelacion) {
        this.nombreRelacion = nombreRelacion;
    }

    public String getMultBajaOrigen() {
        return multBajaOrigen;
    }

    public void setMultBajaOrigen(String multBajaOrigen) {
        this.multBajaOrigen = multBajaOrigen;
    }

    public String getMultAltaOrigen() {
        return multAltaOrigen;
    }

    public void setMultAltaOrigen(String multAltaOrigen) {
        this.multAltaOrigen = multAltaOrigen;
    }

    public String getMultBajaDestino() {
        return multBajaDestino;
    }

    public void setMultBajaDestino(String multBajaDestino) {
        this.multBajaDestino = multBajaDestino;
    }

    public String getMultAltaDestino() {
        return multAltaDestino;
    }

    public void setMultAltaDestino(String multAltaDestino) {
        this.multAltaDestino = multAltaDestino;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public boolean isEstatico() {
        return estatico;
    }

    public void setEstatico(boolean estatico) {
        this.estatico = estatico;
    }
    
    
}
