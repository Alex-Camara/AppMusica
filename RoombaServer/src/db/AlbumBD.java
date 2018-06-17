/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logica.Album;

/**
 *
 * @author Alex Cámara
 */
public class AlbumBD implements AlbumDao {

   @Override
   public Album recuperarAlbumes(int idAlbum) throws SQLException {
      Connection conexion = null;
      conexion = Conexion.conectar();
      PreparedStatement sentencia = null;
      ResultSet resultado = null;
      Album album = new Album();

      String consulta = "SELECT * FROM Album WHERE idAlbum = ?;";

      sentencia = conexion.prepareStatement(consulta);
      sentencia.setInt(1, idAlbum);
      resultado = sentencia.executeQuery();

      while (resultado != null && resultado.next()) {
         album.setIdAlbum(resultado.getInt("idAlbum"));
         album.setNombre(resultado.getString("nombre"));
         album.setEstudio(resultado.getString("estudio"));
         album.setAnioLanzamiento(resultado.getInt("anioLanzamiento"));
         album.setIdGenero(resultado.getInt("idGenero"));
      }
      return album;
   }

}
