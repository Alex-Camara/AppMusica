package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logica.Genero;

/**
 * Clase que implementa las interfaces declaradas en el DAO homólogo. 
 * Esta permite la interacción con la base de datos con la clase correspondiente.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class GeneroBD implements GeneroDao {

   @Override
   public Genero recuperarGenero(int idGenero) throws SQLException {
      Connection conexion = null;
      conexion = Conexion.conectar();
      PreparedStatement sentenciaGenero = null;
      ResultSet resultadoGenero = null;
      Genero genero = null;
      
      String consultaGenero = "SELECT * FROM Genero WHERE idGenero = ?;";
      sentenciaGenero = conexion.prepareStatement(consultaGenero);
      sentenciaGenero.setInt(1, idGenero);
      resultadoGenero = sentenciaGenero.executeQuery();
      
      while (resultadoGenero != null && resultadoGenero.next()) {
      genero = new Genero();
      genero.setIdGenero(resultadoGenero.getInt("idGenero"));
      genero.setNombre(resultadoGenero.getString("nombre"));
      
      }
      return genero;
      
   }

   @Override
   public List<Genero> recuperarCatalogo() throws SQLException {
      Connection conexion = null;
      conexion = Conexion.conectar();
      PreparedStatement sentenciaGenero = null;
      ResultSet resultadoGenero = null;
      List<Genero> generos = new ArrayList<>();
      String consultaGenero = "SELECT * FROM Genero;";
      sentenciaGenero = conexion.prepareStatement(consultaGenero);
      resultadoGenero = sentenciaGenero.executeQuery();
      
      while (resultadoGenero != null && resultadoGenero.next()) {
         Genero genero = new Genero();
         genero.setIdGenero(resultadoGenero.getInt("idGenero"));
         genero.setNombre(resultadoGenero.getString("nombre"));
         generos.add(genero);
      }
      return generos;
   }

}
