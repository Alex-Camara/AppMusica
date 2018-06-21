/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.util.List;
import logica.Album;

/**
 * Clase que declara las interfaces a utilizar para interactuar con la base de datos especialmente
 * con la clase identificada.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public interface AlbumDao {
    Album recuperarAlbumes(int idAlbum) throws SQLException;
    Album guardarAlbum(Album album) throws SQLException;
    List<Album> recuperarAlbumGenero(int idGenero) throws SQLException;
}
