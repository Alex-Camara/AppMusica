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

        String consulta = "SELECT * FROM ListaReproduccion WHERE idBiblioteca = ?;";

        sentencia = conexion.prepareStatement(consulta);

        sentencia.setInt(1, idBiblioteca);
        resultado = sentencia.executeQuery();

        while (resultado != null && resultado.next()) {
            ListaReproduccion lista = new ListaReproduccion();
            lista.setIdListaReproduccion(resultado.getInt("idListaReproduccion"));
            lista.setNombre(resultado.getString("nombre"));
            lista.setIdBiblioteca(idBiblioteca);
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

        String consulta = "select nombre, artista, calificacion, Album_idAlbum from CancionLocal_has_ListaReproduccion "
                + "chl inner join CancionLocal cl on chl.idCancionLocal=cl.idCancionLocal inner join "
                + "Cancion on idCancion = cl.idCancionLocal and chl.idBiblioteca = ? and "
                + "idListaReproduccion = ?;";

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

    @Override
    public int agregarLista(ListaReproduccion nuevaLista, int idBiblioteca) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "insert into ListaReproduccion (nombre, idBiblioteca) values(?, ?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, nuevaLista.getNombre());
        sentencia.setInt(2, idBiblioteca);
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    @Override
    public int editarLista(ListaReproduccion lista) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "update ListaReproduccion set nombre = ? where idListaReproduccion = ?;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, lista.getNombre());
        sentencia.setInt(2, lista.getIdListaReproduccion());
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    @Override
    public int eliminarLista(ListaReproduccion lista) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;
        eliminarCancionesDeLista(lista.getIdListaReproduccion());

        String consulta = "delete from ListaReproduccion where idListaReproduccion = ?";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, lista.getIdListaReproduccion());
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    public int eliminarCancionesDeLista(int idLista) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "delete from CancionLocal_has_ListaReproduccion where idListaReproduccion = ?;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idLista);
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    @Override
    public int agregarCancionALista(Cancion cancion, ListaReproduccion lista) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "insert into CancionLocal_has_ListaReproduccion (idBiblioteca, idCancionLocal, idListaReproduccion) values(?, ?, ?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, lista.getIdBiblioteca());
        sentencia.setInt(2, cancion.getIdCancion());
        sentencia.setInt(3, lista.getIdListaReproduccion());
        resultado = sentencia.executeUpdate();
        return resultado;
    }
}
