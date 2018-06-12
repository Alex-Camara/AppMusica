/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.util.List;
import logica.Cancion;
import logica.ListaReproduccion;

/**
 *
 * @author Alex Cámara
 */
public interface ListasReproduccionDao {
    List<ListaReproduccion> recuperarListas(int idBiblioteca) throws SQLException;
    
    public int agregarLista(ListaReproduccion nuevaLista, int idBiblioteca) throws SQLException;
    public int editarLista(ListaReproduccion lista) throws SQLException;
    public int eliminarLista(ListaReproduccion lista) throws SQLException;
    public int agregarCancionALista(Cancion cancion, ListaReproduccion lista) throws SQLException;
}
