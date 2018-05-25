package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.Biblioteca;
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

      String consulta = "SELECT idCancion FROM historial WHERE idUsuario = ? ORDER BY fecha DESC LIMIT 23;";
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
      String consultaCancion = "SELECT * FROM cancion WHERE idCancion = ?;";
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

}
