package logica;

/**
 * Clase CanciónIntermediaria con los atributos de la clase hómologa de la base de datos con
 * información extra local necesaria.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class CancionIntermediaria {
   private Cancion cancion;
   private boolean local;
   private int calidad;

   public CancionIntermediaria(Cancion cancion, boolean local, int calidad) {
      this.cancion = cancion;
      this.local = local;
      this.calidad = calidad;
   }

   public Cancion getCancion() {
      return cancion;
   }

   public boolean isLocal() {
      return local;
   }

   public int getCalidad() {
      return calidad;
   }
   
   

}
