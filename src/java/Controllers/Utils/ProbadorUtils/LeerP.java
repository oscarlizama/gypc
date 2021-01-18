/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Utils.ProbadorUtils;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Part;

/**
 *
 * @author ADMIN
 */
public class LeerP {
    
    public static String param(Part archivoSubido){
        String params = "";
        try {
            InputStream ipsStream = archivoSubido.getInputStream();
            Scanner myReader = new Scanner(ipsStream);
            int cantP = 0;
            String data;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine().trim();
                int val = 0;
                if (data.contains("argv[")){
                    Pattern pattern = Pattern.compile("argv[(.*?)]");
                    Matcher matcher = pattern.matcher(data);
                    if(matcher.find()) {
                            val = Integer.parseInt(matcher.group(1));
                            if (val > cantP){
                                cantP = val;
                            }
                        }
                }
                params = cantP + " parámetros";
            }
        } catch (Exception e) {
        }
        return params;
    }
}
