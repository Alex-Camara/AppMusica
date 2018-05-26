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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Biblioteca;
import logica.Cancion;
import logica.ListaReproduccion;

/**
 *
 * @author Alex Cámara
 */
public class ListasReproduccionBD implements ListasReproduccionDao {

    @Override
    public List<ListaReproduccion> recuperarListas(int idBiblioteca) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<ListaReproduccion> listasReproduccion = new ArrayList<>();

        String consulta = "SELECT * FROM listareproduccion WHERE idBiblioteca = ?;";

        sentencia = conexion.prepareStatement(consulta);

        sentencia.setInt(1, idBiblioteca);
        resultado = sentencia.executeQuery();

        while (resultado != null && resultado.next()) {
            ListaReproduccion lista = new ListaReproduccion();
            lista.setIdListaReproduccion(resultado.getInt("idListaReproduccion"));
            lista.setNombre(resultado.getString("nombre"));
            listasReproduccion.add(lista);
        }

        for (int i = 0; i < listasReproduccion.size(); i++) {
            List<Cancion> listaCanciones;
            listaCanciones = recuperarCanciones(idBiblioteca, listasReproduccion.get(i).getIdListaReproduccion());
            listasReproduccion.get(i).setCanciones(listaCanciones);
        }
        return listasReproduccion;
    }

    private List<Cancion> recuperarCanciones(int idBiblioteca, int idListaReproduccion) {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Cancion> listasCanciones = new ArrayList<>();

        String consulta = "select nombre, artista, calificacion, Album_idAlbum from cancionlocal_has_listareproduccion "
                + "chl inner join cancionlocal cl on chl.idcancionlocal=cl.idcancionlocal inner join "
                + "cancion on idcancion = cl.idcancionlocal and chl.idbiblioteca = ? and "
                + "idlistareproduccion = ?;";

        try {
            sentencia = conexion.prepareStatement(consulta);

            sentencia.setInt(1, idBiblioteca);
            sentencia.setInt(2, idListaReproduccion);
            resultado = sentencia.executeQuery();
            while (resultado != null && resultado.next()) {
                Cancion cancion = new Cancion();
                cancion.setNombre(resultado.getString("nombre"));
                cancion.setArtista(resultado.getString("artista"));
                cancion.setCalificacion(resultado.getInt("calificacion"));
                cancion.setAlbum_idAlbum(resultado.getInt("Album_idAlbum"));
                listasCanciones.add(cancion);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ListasReproduccionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listasCanciones;
    }

}
