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
public class Clase { //Identificado por ' xmi:type="uml:Class" ' y el inicio de etiqueta ' <packagedElement ' 
    private String idClaseStarUML; //Identificador interno de objetos, p.ej. "AAAAAAF1obbA00qARCs="
    private String nombreClase;
    private String visibilidad; //Opciones: public, private, protected, package
    private boolean Abstracto;
    //Variables de relaciones
    private ArrayList<Atributo> atributos = new ArrayList<>();
    private ArrayList<Metodo> metodos = new ArrayList<>();
    private ArrayList<AtributoRelacion> atributosDeRelaciones = new ArrayList<>();

    public Clase(String idClaseStarUML, String nombreClase, String visibilidad, boolean Abstracto) {
        this.idClaseStarUML = idClaseStarUML;
        this.nombreClase = nombreClase;
        this.visibilidad = visibilidad;
        this.Abstracto = Abstracto;
    }
    
    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public boolean isAbstracto() {
        return Abstracto;
    }

    public void setAbstracto(boolean Abstracto) {
        this.Abstracto = Abstracto;
    }

    public String getIdClaseStarUML() {
        return idClaseStarUML;
    }

    public void setIdClaseStarUML(String idClaseStarUML) {
        this.idClaseStarUML = idClaseStarUML;
    }

    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(ArrayList<Atributo> atributos) {
        this.atributos = atributos;
    }

    public ArrayList<Metodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(ArrayList<Metodo> metodos) {
        this.metodos = metodos;
    }

    public ArrayList<AtributoRelacion> getAtributosDeRelaciones() {
        return atributosDeRelaciones;
    }

    public void setAtributosDeRelaciones(ArrayList<AtributoRelacion> atributosDeRelaciones) {
        this.atributosDeRelaciones = atributosDeRelaciones;
    }
    
    public void addAtributo(Atributo a){
        this.atributos.add(a);
    }
    
    public void addAtributo(String nombreAtributo, String visibilidad, boolean Estatico, String tipo, String valorPorDefecto){
        Atributo a = new Atributo(nombreAtributo, visibilidad, Estatico, tipo, valorPorDefecto);
        this.atributos.add(a);
    }
    
    public void addMetodo(Metodo m){
        this.metodos.add(m);
    }
    
    public void addMetodo(String nombreMetodo, String visibilidad, String estatico, String retornaTipo){
        Metodo m = new Metodo(nombreMetodo, visibilidad, estatico, retornaTipo);
        this.metodos.add(m);
    }
    
    public void addAtributoDeRelacion(AtributoRelacion a){
        this.atributosDeRelaciones.add(a);
    }
    
    public void addAtributoDeRelacion(String nombreAtributo, String visibilidad, String nombreClase, boolean estatico){
        AtributoRelacion a = new AtributoRelacion(nombreAtributo, visibilidad, nombreClase, estatico);
        this.atributosDeRelaciones.add(a);
    }
}
