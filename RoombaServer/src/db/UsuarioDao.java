/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import logica.Usuario;

/**
 *
 * @author Alex Cámara
 */
public interface UsuarioDao {
    public Usuario recuperarUsuario(String correo, String clave) throws SQLException;
    public Usuario registrarUsuario(Usuario usuario) throws SQLException;
}
