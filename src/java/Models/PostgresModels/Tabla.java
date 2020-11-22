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
public class Tabla {
    //Atributos
    private String idTablaPwD;
    private String nombreTabla;
    //Atributos de Relacion
    private ArrayList<AtributoBD> atributoBDs = new ArrayList<>();

    public String getIdTablaPwD() {
        return idTablaPwD;
    }

    public void setIdTablaPwD(String idTablaPwD) {
        this.idTablaPwD = idTablaPwD;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public ArrayList<AtributoBD> getAtributoBDs() {
        return atributoBDs;
    }

    public void setAtributoBDs(ArrayList<AtributoBD> atributoBDs) {
        this.atributoBDs = atributoBDs;
    }

    public Tabla(String idTablaPwD, String nombreTabla) {
        this.idTablaPwD = idTablaPwD;
        this.nombreTabla = nombreTabla;
    }
    
    public void addAtributoBD(AtributoBD a){
        this.atributoBDs.add(a);
    }
    
    public void addAtributoBD(String idAtributoPwD, String nombreAtributo, String tipo, int longitud, int precision, boolean mandatorio, boolean unico, boolean llavePrimaria){
        AtributoBD a = new AtributoBD(idAtributoPwD, nombreAtributo, tipo, longitud, precision, mandatorio, unico, llavePrimaria);
        this.atributoBDs.add(a);
    }
}
