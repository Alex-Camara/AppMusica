/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.util.List;
import logica.Genero;

/**
 *
 * @author javr
 */
public interface GeneroDao {
    Genero recuperarGenero(int idGenero) throws SQLException;
    List<Genero> recuperarCatalogo() throws SQLException;
}
