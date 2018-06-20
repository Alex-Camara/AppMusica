/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.util.List;
import logica.Cancion;

/**
 * Clase que declara las interfaces a utilizar para interactuar con la base de datos especialmente
 * con la clase identificada.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public interface CancionDao {
   public List<Cancion> recuperarCancionesHistorial(int idUsuario) throws SQLException;
   public Cancion recuperarCancion(int idCancion) throws SQLException;
   public int actualizarCalificacion(int idCancion, int calificacion) throws SQLException;
   public List<Cancion> recuperarCancionesExternas() throws SQLException;
   public int guardarCancion(Cancion cancion) throws SQLException;
   
}
