/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPostgres.Modelo;

/**
 *
 * @author ADMIN
 */
public class AtributoBD {
    private String idAtributoPwD;
    private String nombreAtributo;
    private String tipo;
    private int longitud;
    private int precision;
    private boolean mandatorio;
    private boolean unico;
    private boolean llavePrimaria;

    public AtributoBD(String idAtributoPwD, String nombreAtributo, String tipo, int longitud, int precision, boolean mandatorio, boolean unico, boolean llavePrimaria) {
        this.idAtributoPwD = idAtributoPwD;
        this.nombreAtributo = nombreAtributo;
        this.tipo = tipo;
        this.longitud = longitud;
        this.precision = precision;
        this.mandatorio = mandatorio;
        this.unico = unico;
        this.llavePrimaria = llavePrimaria;
    }

    public String getIdAtributoPwD() {
        return idAtributoPwD;
    }

    public void setIdAtributoPwD(String idAtributoPwD) {
        this.idAtributoPwD = idAtributoPwD;
    }

    public String getNombreAtributo() {
        return nombreAtributo;
    }

    public void setNombreAtributo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isMandatorio() {
        return mandatorio;
    }

    public void setMandatorio(boolean mandatorio) {
        this.mandatorio = mandatorio;
    }

    public boolean isUnico() {
        return unico;
    }

    public void setUnico(boolean unico) {
        this.unico = unico;
    }

    public boolean isLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(boolean llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }
    
}
