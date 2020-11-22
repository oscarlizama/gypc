/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PostgresModels;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class BD {
    //Atributos
    private String nombreBD;
    private String codificacion;
    // Atributos de Relacion
    private ArrayList<LlaveForanea> llavesForaneas = new ArrayList<>();
    private ArrayList<Tabla> tablas = new ArrayList<>();

    public BD(String nombreBD, String codificacion) {
        this.nombreBD = nombreBD;
        this.codificacion = codificacion;
    }

    public String getNombreBD() {
        return nombreBD;
    }

    public void setNombreBD(String nombreBD) {
        this.nombreBD = nombreBD;
    }

    public String getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    public ArrayList<LlaveForanea> getLlavesForaneas() {
        return llavesForaneas;
    }

    public void setLlavesForaneas(ArrayList<LlaveForanea> llavesForaneas) {
        this.llavesForaneas = llavesForaneas;
    }

    public ArrayList<Tabla> getTablas() {
        return tablas;
    }

    public void setTablas(ArrayList<Tabla> tablas) {
        this.tablas = tablas;
    }
    
    public void addTabla(Tabla t){
        this.tablas.add(t);
    }
    
    public void addTabla(String idTablaPwD, String nombreTabla){
        Tabla t = new Tabla(idTablaPwD, nombreTabla);
        this.tablas.add(t);
    }
    
    public void addLlaveForanea(LlaveForanea l){
        this.llavesForaneas.add(l);
    }
    
    public void addLlaveForanea(String idReferenciaPwD, String nombreLlave, String tablaPadre, String tablaHijo, String atributoPadre, String atributoHijo, String onDelete, String onUpdate, String cardinalidad){
        LlaveForanea l = new LlaveForanea(idReferenciaPwD, nombreLlave, tablaPadre, tablaHijo, atributoPadre, atributoHijo, onDelete, onUpdate, cardinalidad);
        this.llavesForaneas.add(l);
    }
}
