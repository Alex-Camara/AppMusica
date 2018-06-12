/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import logica.Album;

/**
 *
 * @author Alex Cámara
 */
public interface AlbumDao {
    Album recuperarAlbumes(int idAlbum) throws SQLException;
    
}
