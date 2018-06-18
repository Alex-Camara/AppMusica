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
import logica.Cancion;

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

    @Override
    public Album guardarAlbum(Album album) throws SQLException {
        int resultado = 0;
        Album albumEncontrado = buscarAlbum(album.getNombre());
        System.out.println("album encontrado: " + albumEncontrado.getIdAlbum());
        if (albumEncontrado.getIdAlbum() == -1) {
            Connection conexion = null;
            conexion = Conexion.conectar();
            PreparedStatement sentencia = null;

            String consulta = "insert into Album (nombre, idGenero) values(?, ?)";
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setString(1, album.getNombre());
            sentencia.setInt(2, album.getIdGenero());
            resultado = sentencia.executeUpdate();
            
            albumEncontrado = buscarAlbum(album.getNombre());
            System.out.println("album encontrado: " + albumEncontrado.getIdAlbum());
        }
        return albumEncontrado;
    }

    private Album buscarAlbum(String nombre) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Album album = new Album();
        album.setIdAlbum(-1);

        String consulta = "SELECT * FROM Album WHERE nombre = ?;";

        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, nombre);
        resultado = sentencia.executeQuery();

        while (resultado != null && resultado.next()) {
            album.setIdAlbum(resultado.getInt("idAlbum"));
        }
        return album;
    }

}
