/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import logica.Biblioteca;

/**
 *
 * @author javr
 */
public interface BibliotecaDao {
   public Biblioteca recuperarBilioteca() throws SQLException;
   public Biblioteca recuperarBilioteca(int idUsuario) throws SQLException;
   
}
