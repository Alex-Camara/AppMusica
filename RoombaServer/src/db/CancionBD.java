package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Cancion;
import logica.Usuario;

/**
 * Clase que implementa las interfaces declaradas en el DAO homólogo. Esta
 * permite la interacción con la base de datos con la clase correspondiente.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
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
    
    public int recuperarCancion(String nombre) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentenciaCancion = null;
        ResultSet resultadoCancion = null;
        int idCancion = 0;
        String consultaCancion = "SELECT * FROM Cancion WHERE nombre = ?;";
        sentenciaCancion = conexion.prepareStatement(consultaCancion);
        sentenciaCancion.setString(1, nombre);
        resultadoCancion = sentenciaCancion.executeQuery();

        resultadoCancion.next();
        idCancion = resultadoCancion.getInt("idCancion");
        return idCancion;
    }

    @Override
    public int actualizarCalificacion(int idCancion, int calificacion) throws SQLException {
        int resultado = 0;
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;

        String consulta = "update CancionLocal SET calificacion = ? where idCancion = ?;";

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

    @Override
    public int guardarCancion(int idBiblioteca, Cancion cancion, boolean privada) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "insert into Cancion (nombre, artista, Album_idAlbum, ruta, privada) values(?, ?, ?, ?, ?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setString(1, cancion.getNombre());
        sentencia.setString(2, cancion.getArtista());
        sentencia.setInt(3, cancion.getAlbum_idAlbum());
        sentencia.setString(4, cancion.getRuta());
        sentencia.setBoolean(5, privada);

        resultado = sentencia.executeUpdate();
        
        int idCancion = recuperarCancion(cancion.getNombre());
        cancion.setIdCancion(idCancion);
        agregarCancionABiblioteca(idBiblioteca, cancion);
        return resultado;
    }
    
   public void actualizarHistorial(HashMap<Usuario, Cancion> hashRecibido) throws SQLException {
      Map.Entry<Usuario, Cancion> entry = hashRecibido.entrySet().iterator().next();
      Usuario usuario = entry.getKey();
      Cancion cancion = entry.getValue();
      List<Cancion> cancionesUsuario = recuperarCancionesHistorial(usuario.getIdUsuario());
      if (!contiene(cancionesUsuario, cancion)) {
         Connection conexion = null;
         conexion = Conexion.conectar();
         PreparedStatement sentencia = null;
         final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
         Date date = new Date();
         String consulta = "INSERT INTO Historial (idUsuario, idCancion, fecha) VALUES(?, ?, ?);";
         sentencia = conexion.prepareStatement(consulta);
         sentencia.setInt(1, usuario.getIdUsuario());
         sentencia.setInt(2, cancion.getIdCancion());
         sentencia.setString(3, DATEFORMAT.format(date));
         sentencia.executeUpdate();
      }
   }
   
   private boolean contiene(List<Cancion> cancionesUsuario, Cancion cancion) {
      for (int i = 0; i < cancionesUsuario.size(); i++) {
         if (cancionesUsuario.get(i).getIdCancion() == cancion.getIdCancion()) {
            return true;
         }
      }
      return false;
   }
    
    @Override
    public int agregarCancionABiblioteca(int idBiblioteca, Cancion cancion) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "insert into CancionLocal (idBiblioteca, idCancion) values(?, ?)";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idBiblioteca);
        sentencia.setInt(2, cancion.getIdCancion());
        System.out.println("id de cancion a agregar: " + cancion.getIdCancion());
        resultado = sentencia.executeUpdate();
        return resultado;
    }

    @Override
    public int eliminarCancionLocal(int idBiblioteca, int idCancion) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        eliminarCancionDeListas(idBiblioteca, idCancion);
        
        String consulta = "delete from CancionLocal where idBiblioteca = ? and idCancion = ?;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idBiblioteca);
        sentencia.setInt(2, idCancion);

        resultado = sentencia.executeUpdate();
        return resultado;
    }
    
    public int eliminarCancionDeListas(int idBiblioteca, int idCancion) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        int resultado = 0;

        String consulta = "delete from CancionLocal_has_ListaReproduccion where idBiblioteca = ? and idCancion = ?;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idBiblioteca);
        sentencia.setInt(2, idCancion);

        resultado = sentencia.executeUpdate();
        return resultado;
    }

    @Override
    public List<Cancion> recuperarCancionesAlbum(int idAlbum) throws SQLException {
        Connection conexion = null;
        conexion = Conexion.conectar();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Cancion> canciones = new ArrayList<>();

        String consulta = "SELECT * FROM Cancion WHERE privada = 0 and Album_idAlbum = ?;";
        sentencia = conexion.prepareStatement(consulta);
        sentencia.setInt(1, idAlbum);
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
        return canciones;
    }
}