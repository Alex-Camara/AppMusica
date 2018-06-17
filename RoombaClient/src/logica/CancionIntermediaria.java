package logica;

/**
 *
 * @author javr
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
