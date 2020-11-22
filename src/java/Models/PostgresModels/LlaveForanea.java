/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PostgresModels;

/**
 *
 * @author ADMIN
 */
public class LlaveForanea {
    private String idReferenciaPwD;
    private String nombreLlave;
    private String tablaPadre;
    private String tablaHijo;
    private String atributoPadre;
    private String atributoHijo;
    private String onDelete;
    private String onUpdate;
    private String cardinalidad;

    public String getIdReferenciaPwD() {
        return idReferenciaPwD;
    }

    public void setIdReferenciaPwD(String idReferenciaPwD) {
        this.idReferenciaPwD = idReferenciaPwD;
    }

    public String getNombreLlave() {
        return nombreLlave;
    }

    public void setNombreLlave(String nombreLlave) {
        this.nombreLlave = nombreLlave;
    }

    public String getTablaPadre() {
        return tablaPadre;
    }

    public void setTablaPadre(String tablaPadre) {
        this.tablaPadre = tablaPadre;
    }

    public String getTablaHijo() {
        return tablaHijo;
    }

    public void setTablaHijo(String tablaHijo) {
        this.tablaHijo = tablaHijo;
    }

    public String getAtributoPadre() {
        return atributoPadre;
    }

    public void setAtributoPadre(String atributoPadre) {
        this.atributoPadre = atributoPadre;
    }

    public String getAtributoHijo() {
        return atributoHijo;
    }

    public void setAtributoHijo(String atributoHijo) {
        this.atributoHijo = atributoHijo;
    }

    public String getOnDelete() {
        return onDelete;
    }

    public void setOnDelete(String onDelete) {
        this.onDelete = onDelete;
    }

    public String getOnUpdate() {
        return onUpdate;
    }

    public void setOnUpdate(String onUpdate) {
        this.onUpdate = onUpdate;
    }

    public String getCardinalidad() {
        return cardinalidad;
    }

    public void setCardinalidad(String cardinalidad) {
        this.cardinalidad = cardinalidad;
    }

    public LlaveForanea(String idReferenciaPwD, String nombreLlave, String tablaPadre, String tablaHijo, String atributoPadre, String atributoHijo, String onDelete, String onUpdate, String cardinalidad) {
        this.idReferenciaPwD = idReferenciaPwD;
        this.nombreLlave = nombreLlave;
        this.tablaPadre = tablaPadre;
        this.tablaHijo = tablaHijo;
        this.atributoPadre = atributoPadre;
        this.atributoHijo = atributoHijo;
        this.onDelete = onDelete;
        this.onUpdate = onUpdate;
        this.cardinalidad = cardinalidad;
    }
}
