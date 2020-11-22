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
public class LecturaBD {
    private BD baseDeDatos;

    public LecturaBD(BD baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    public BD getBaseDeDatos() {
        return baseDeDatos;
    }

    public void setBaseDeDatos(BD baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }
    
}
