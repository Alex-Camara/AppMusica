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
public class BibliotecaBD implements BibliotecaDao {

   /**
    *
    * @return @throws SQLException
    */
   @Override
   public Biblioteca recuperarBilioteca() throws SQLException {
      Connection conexion = null;
      conexion = Conexion.conectar();
      PreparedStatement sentencia = null;
      ResultSet resultado = null;
      Biblioteca biblioteca = new Biblioteca();
      List<Cancion> canciones = new ArrayList<>();

      String consulta = "SELECT * FROM cancion WHERE privada = 0;";

      sentencia = conexion.prepareStatement(consulta);
      resultado = sentencia.executeQuery();

      while (resultado != null && resultado.next()) {
         Cancion cancion = new Cancion();
         cancion.setIdCancion(resultado.getInt("idCancion"));
         cancion.setArtista(resultado.getString("artista"));
         cancion.setAlbum_idAlbum(resultado.getInt("Album_idAlbum"));
         cancion.setNombre(resultado.getString("nombre"));
         cancion.setRuta(resultado.getString("ruta"));
         canciones.add(cancion);
         
      }
      biblioteca.setCanciones(canciones);
      return biblioteca;
   }

   @Override
   public Biblioteca recuperarBilioteca(int idUsuario) throws SQLException {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }
}

