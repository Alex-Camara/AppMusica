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
 * Clase que implementa las interfaces declaradas en el DAO homólogo. 
 * Esta permite la interacción con la base de datos con la clase correspondiente.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class BibliotecaBD implements BibliotecaDao {

   /**
    *
    * @param idBiblioteca
    * @return @throws SQLException
    */
   @Override
   public Biblioteca recuperarBiblioteca(int idBiblioteca) throws SQLException {
      Connection conexion = null;
      conexion = Conexion.conectar();
      PreparedStatement sentencia = null;
      ResultSet resultado = null;
      Biblioteca biblioteca = new Biblioteca();
      List<Cancion> canciones = new ArrayList<>();

      String consulta = "select c.idCancion, nombre, artista,Album_idAlbum, ruta,"
              + " calificacion from Cancion c inner join CancionLocal cl on "
              + "c.idCancion = cl.idCancion where idBiblioteca = ? ;";

      sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idBiblioteca);
        resultado = sentencia.executeQuery();

      while (resultado != null && resultado.next()) {
         Cancion cancion = new Cancion();
         cancion.setIdCancion(resultado.getInt("idCancion"));
         cancion.setArtista(resultado.getString("artista"));
         cancion.setAlbum_idAlbum(resultado.getInt("Album_idAlbum"));
         cancion.setNombre(resultado.getString("nombre"));
         cancion.setRuta(resultado.getString("ruta"));
         cancion.setCalificacion(resultado.getInt("calificacion"));
         canciones.add(cancion);
         
      }
      biblioteca.setIdBiblioteca(idBiblioteca);
      biblioteca.setCanciones(canciones);
      return biblioteca;
   }

}

