package logica;

/**
 * Clase Historial con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Historial {
   int idUsuario;
   int idCancion;

   public int getIdUsuario() {
      return idUsuario;
   }

   public int getIdCancion() {
      return idCancion;
   }

   public void setIdUsuario(int idUsuario) {
      this.idUsuario = idUsuario;
   }

   public void setIdCancion(int idCancion) {
      this.idCancion = idCancion;
   }
   

}
