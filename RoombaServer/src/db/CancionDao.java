/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import logica.Cancion;
import logica.Usuario;

/**
 *
 * @author javr
 */
public interface CancionDao {
   public List<Cancion> recuperarCancionesHistorial(int idUsuario) throws SQLException;
   public Cancion recuperarCancion(int idCancion) throws SQLException;
   public int actualizarCalificacion(int idCancion, int calificacion) throws SQLException;
   public List<Cancion> recuperarCancionesExternas() throws SQLException;
   public void actualizarHistorial(HashMap<Usuario, Cancion> hash) throws SQLException;
   public List<Cancion> buscarCancion(String nombre)throws SQLException;

   
}
