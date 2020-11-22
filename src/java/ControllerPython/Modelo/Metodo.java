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
public class Metodo {
    private String nombreMetodo;
    private String visibilidad;
    private String estatico;    
    private String retornaTipo; //Identificado por la etiqueta "<ownedParameter" y mas adelante la opcion ' direction="return" ' 
    //Variables de Relacion
    private ArrayList<Parametro> parametros = new ArrayList<>();
    
    public Metodo(){}
    
    public Metodo(String nombreMetodo, String visibilidad, String estatico, String retornaTipo) {
        this.nombreMetodo = nombreMetodo;
        this.visibilidad = visibilidad;
        this.estatico = estatico;
        this.retornaTipo = retornaTipo;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getEstatico() {
        return estatico;
    }

    public void setEstatico(String estatico) {
        this.estatico = estatico;
    }

    public String getRetornaTipo() {
        return retornaTipo;
    }

    public void setRetornaTipo(String retornaTipo) {
        this.retornaTipo = retornaTipo;
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }
    
    public void addParametro(Parametro p){
        this.parametros.add(p);
    }
}
