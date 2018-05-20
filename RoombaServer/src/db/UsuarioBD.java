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
import logica.Usuario;

/**
 *
 * @author Alex Cámara
 */
public class UsuarioBD implements UsuarioDao {

    @Override
    public Usuario recuperarUsuario(String correo, String clave) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Usuario usuario = new Usuario();

        String consulta = "SELECT * FROM usuario WHERE correo = ? AND clave = ?;";

        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, correo);
        sentencia.setString(2, clave);
        resultado = sentencia.executeQuery();
        
        while (resultado != null && resultado.next()) {
         usuario.setIdUsuario(resultado.getInt("idUsuario"));
         usuario.setNombre(resultado.getString("nombre"));
         usuario.setPaterno(resultado.getString("paterno"));
         usuario.setMaterno(resultado.getString("materno"));
         usuario.setTipoUsuario(resultado.getString("nombreArtistico"));
         usuario.setIdBiblioteca(resultado.getInt("idBiblioteca"));
      }
        return usuario;
    }

}
