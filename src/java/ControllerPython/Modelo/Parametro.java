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
public class Parametro {
    private String nombreParametro;
    private String tipoParametro;

    public Parametro(String nombreParametro, String tipoParametro) {
        this.nombreParametro = nombreParametro;
        this.tipoParametro = tipoParametro;
    }

    public String getNombreParametro() {
        return nombreParametro;
    }

    public void setNombreParametro(String nombreParametro) {
        this.nombreParametro = nombreParametro;
    }

    public String getTipoParametro() {
        return tipoParametro;
    }

    public void setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }
    
    
}
