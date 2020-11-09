/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPython.Modelo;

/**
 *
 * @author ADMIN
 */
public class AtributoRelacion {
    private String nombreAtributo;
    private String visibilidad;
    private String nombreClase;
    private boolean estatico;

    public AtributoRelacion(String nombreAtributo, String visibilidad, String nombreClase, boolean estatico) {
        this.nombreAtributo = nombreAtributo;
        this.visibilidad = visibilidad;
        this.nombreClase = nombreClase;
        this.estatico = estatico;
    }

    public String getNombreAtributo() {
        return nombreAtributo;
    }

    public void setNombreAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public boolean isEstatico() {
        return estatico;
    }

    public void setEstatico(boolean estatico) {
        this.estatico = estatico;
    }
    
}
