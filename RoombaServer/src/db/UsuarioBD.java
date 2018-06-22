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
 * Clase que implementa las interfaces declaradas en el DAO homólogo. 
 * Esta permite la interacción con la base de datos con la clase correspondiente.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class UsuarioBD implements UsuarioDao {

    @Override
    public Usuario recuperarUsuario(String correo, String clave) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Usuario usuario = new Usuario();

        String consulta = "SELECT * FROM Usuario WHERE correo = ? AND clave = ?;";

        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, correo);
        sentencia.setString(2, clave);
        resultado = sentencia.executeQuery();

        while (resultado != null && resultado.next()) {
            usuario.setIdUsuario(resultado.getInt("idUsuario"));
            usuario.setNombre(resultado.getString("nombre"));
            usuario.setPaterno(resultado.getString("paterno"));
            usuario.setMaterno(resultado.getString("materno"));
            usuario.setNombreArtistico(resultado.getString("nombreArtistico"));
            usuario.setIdBiblioteca(resultado.getInt("idBiblioteca"));
            usuario.setTipoUsuario(resultado.getString("tipoUsuario"));
        }
        return usuario;
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;
        Usuario usuarioIngresado;

        String consulta = "insert into Usuario (nombre, paterno, materno, correo,"
                + " clave, tipousuario) values(?, ?, ?, ?, ?, ?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, usuario.getNombre());
        sentencia.setString(2, usuario.getPaterno());
        sentencia.setString(3, usuario.getMaterno());
        sentencia.setString(4, usuario.getCorreo());
        sentencia.setString(5, usuario.getClave());
        sentencia.setString(6, "usuario");
        resultado = sentencia.executeUpdate();

        usuarioIngresado = recuperarUsuario(usuario.getCorreo(), usuario.getClave());
        agregarBiblioteca(usuarioIngresado);
        actualizarBiblioteca(usuarioIngresado.getIdUsuario());
        usuarioIngresado.setIdBiblioteca(usuarioIngresado.getIdUsuario());
        return usuarioIngresado;
    }

    private int agregarBiblioteca(Usuario usuario) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "insert into Biblioteca (idBiblioteca) values(?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, usuario.getIdUsuario());
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    private int actualizarBiblioteca(int idUsuario) throws SQLException {
        int resultado = 0;
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;

        String consulta = "update Usuario SET idBiblioteca = ? where idUsuario = ?";

        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idUsuario);
        sentencia.setInt(2, idUsuario);
        resultado = sentencia.executeUpdate();
        return resultado;
    }
    
    @Override
    public int actualizarUsuario(int idUsuario, String nombreArtistico) throws SQLException {
        int resultado = 0;
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;

        String consulta = "update Usuario SET tipoUsuario = 'creador', nombreArtistico = ? where idUsuario = ?;";

        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, nombreArtistico);
        sentencia.setInt(2, idUsuario);
        resultado = sentencia.executeUpdate();
        return resultado;
    }
}
