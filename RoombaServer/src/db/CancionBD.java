package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Cancion;

/**
 *
 * @author javr
 */
public class CancionBD implements CancionDao {

    @Override
    public List<Cancion> recuperarCancionesHistorial(int idUsuario) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Cancion> canciones = new ArrayList<>();

        String consulta = "SELECT idCancion FROM Historial WHERE idUsuario = ? ORDER BY fecha DESC LIMIT 23;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idUsuario);
        resultado = sentencia.executeQuery();
        while (resultado != null && resultado.next()) {
            Cancion cancionRecuperada = recuperarCancion(resultado.getInt("idCancion"));
            canciones.add(cancionRecuperada);

        }
        return canciones;
    }

    @Override
    public Cancion recuperarCancion(int idCancion) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentenciaCancion = null;
        ResultSet resultadoCancion = null;
        String consultaCancion = "SELECT * FROM Cancion WHERE idCancion = ?;";
        sentenciaCancion = conexion.prepareStatement(consultaCancion);
        sentenciaCancion.setInt(1, idCancion);
        resultadoCancion = sentenciaCancion.executeQuery();

        resultadoCancion.next();
        Cancion cancion = new Cancion();
        cancion.setIdCancion(resultadoCancion.getInt("idCancion"));
        cancion.setArtista(resultadoCancion.getString("artista"));
        cancion.setAlbum_idAlbum(resultadoCancion.getInt("Album_idAlbum"));
        cancion.setNombre(resultadoCancion.getString("nombre"));
        cancion.setRuta(resultadoCancion.getString("ruta"));
        return cancion;
    }

    @Override
    public int actualizarCalificacion(int idCancion, int calificacion) throws SQLException {
        int resultado = 0;
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;

        String consulta = "update CancionLocal SET calificacion = ? where idCancionLocal = ?;";

        try {
            sentencia = conexion.prepareStatement(consulta);
            sentencia.setInt(1, calificacion);
            sentencia.setInt(2, idCancion);
            resultado = sentencia.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CancionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    @Override
    public List<Cancion> recuperarCancionesExternas() throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Cancion> canciones = new ArrayList<>();

        String consulta = "SELECT * FROM Cancion WHERE privada = 0;";
        sentencia = conexion.prepareStatement(consulta);
        resultado = sentencia.executeQuery();
        while (resultado != null && resultado.next()) {
            Cancion cancionRecuperada = recuperarCancion(resultado.getInt("idCancion"));
            canciones.add(cancionRecuperada);

        }
        return canciones;
    }

}
