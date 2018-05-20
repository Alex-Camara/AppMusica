/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex Cámara
 */
public class Conexion {

  private  static Connection conexion;
    private static String host;
    private static String db;
    private static String username;
    private static String password;
    
    
    public static Connection conectar(){
      host= "localhost";
      db = "appMusica";
      username = "roombaServer";
      password = "mACaJaVRZsjRldgG#09";
      if(conexion == null){
        try {
          Class.forName("com.mysql.jdbc.Driver");
          String url ="jdbc:mysql://"+host+"/"+db;
          conexion = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
          Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
          Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      }
      return conexion;
    }
  
    public static void cerrarConexion(){
      if(conexion != null){
        try {
          conexion.close();
        } catch (SQLException ex) {
          Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
}
