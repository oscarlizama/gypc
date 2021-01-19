/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.ControllerProbador;
import Controllers.Utils.PostgresUtils.Generar;
import Controllers.Utils.PostgresUtils.Leer;
import Controllers.Utils.ProbadorUtils.LeerP;
import Models.PostgresModels.LecturaBD;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author ADMIN
 */

@ManagedBean
public class ControladorProbador {
    private Part archivoSubido;
    private String folder;
    private String parametros;
    private String resultadoEx;
    private String paramsOut;

    public String getParamsOut() {
        return paramsOut;
    }

    public void setParamsOut(String paramsOut) {
        this.paramsOut = paramsOut;
    }

    public String getResultadoEx() {
        return resultadoEx;
    }

    public void setResultadoEx(String resultadoEx) {
        this.resultadoEx = resultadoEx;
    }

    
    
    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    
    
    public Part getArchivoSubido() {
        return archivoSubido;
    }

    public void setArchivoSubido(Part archivoSubido) {
        this.archivoSubido = archivoSubido;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
    
    public void leerParams(){
        try {
            if (archivoSubido != null){
                String fileName = archivoSubido.getSubmittedFileName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                if (extension.equals(".py")){
                    String[] params = LeerP.param(archivoSubido);
                    paramsOut = params[0];
                }                
            }            
        } catch (Exception e) {
        }
    }
    
    public void cargar(){
        try{  
            System.out.println("entre a cargar");
            System.out.println(archivoSubido.toString());
            if (archivoSubido != null){
                String fileName = archivoSubido.getSubmittedFileName();
                String extension = fileName.substring(fileName.lastIndexOf("."));
                if (extension.equals(".py")){
                    InputStream ipsStream = archivoSubido.getInputStream();
                    String codigo = "";
                    try {
                        Scanner myReader = new Scanner(ipsStream);
                        //Extrayendo parámetros                        )
                        while (myReader.hasNextLine()) {
                            codigo += myReader.nextLine() + "\n";
                        }
                    } catch (Exception e) {
                    }
                    //System.out.println(codigo);
                    BufferedWriter bfw = new BufferedWriter(new FileWriter(fileName));
                    bfw.write(codigo);
                    bfw.close();
                    ArrayList<String> params = new ArrayList<String>();
                    String[] par = parametros.split(",");
                    for (String p : par) {
                        params.add(p);
                    }
                    String command = "cmd.exe /c python " + fileName;
                    for (String param : params) {
                        command += " " + param;                        
                    }
                    System.out.println("Comando a ejecutar: " + command);
                    Process p = Runtime.getRuntime().exec(command);
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    System.out.println("----------------------------------");
                    System.out.println("------------resultados------------");
                    System.out.println("----------------------------------");                    
                    String linea = in.readLine();
                    System.out.println("linea es " + linea);
                    String res = linea;
                    boolean prim = true;
                    while(linea != null){
                        System.out.println(linea);
                        try {
                            if (prim != true){
                                res += "\n" + linea;                                
                            }else{prim = false;}
                            linea = in.readLine();
                        } catch (Exception e) {
                            linea = null;
                        }
                    }
                    resultadoEx = res;
                    System.out.println("ete res: " + res);
                }                
            }             
        }catch(Exception e){ 
            e.printStackTrace();
        }
    }
}
