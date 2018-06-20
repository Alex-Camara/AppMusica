/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import logica.Usuario;

/**
 * Clase que declara las interfaces a utilizar para interactuar con la base de datos especialmente
 * con la clase identificada.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public interface UsuarioDao {
    public Usuario recuperarUsuario(String correo, String clave) throws SQLException;
    public Usuario registrarUsuario(Usuario usuario) throws SQLException;
    public int actualizarUsuario(int idUsuario, String nombreArtistico) throws SQLException;
}
