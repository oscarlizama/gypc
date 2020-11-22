/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PythonModels;

/**
 *
 * @author ADMIN
 */
public class Atributo { //Identificado por ' xmi:type="uml:Property" ' y el inicio de etiqueta '<ownedAttribute'    
    private String nombreAtributo;
    private String visibilidad; //Opciones: public, private, protected, package
    private boolean Estatico; // static
    private String tipo; //Opciones: int, String, Date, boolean, float, char
    private String valorPorDefecto; //Identificado por la etiqueta de inicio '<defaultValue', dentro de un Atributo
    
    public Atributo(){}
    
    public Atributo(String nombreAtributo, String visibilidad, boolean Estatico, String tipo, String valorPorDefecto) {
        this.nombreAtributo = nombreAtributo;
        this.visibilidad = visibilidad;
        this.Estatico = Estatico;
        this.tipo = tipo;
        this.valorPorDefecto = valorPorDefecto;
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

    public boolean isEstatico() {
        return Estatico;
    }

    public void setEstatico(boolean Estatico) {
        this.Estatico = Estatico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValorPorDefecto() {
        return valorPorDefecto;
    }

    public void setValorPorDefecto(String valorPorDefecto) {
        this.valorPorDefecto = valorPorDefecto;
    }
    
    
}
