package logica.reproductor;

import java.util.ArrayList;
import java.util.List;
import logica.CancionIntermediaria;
import presentacion.IGUBarraReproduccionController;

/**
 *
 * @author javr
 */
public class ColaReproduccion {
   private static List<CancionIntermediaria> cola = new ArrayList<>();
   private static int posicionActual = 0;
   public static void agregarAContinuacion(CancionIntermediaria cancion){
      cola.add(posicionActual+1, cancion);
      checkDatos();
   }
   public static void cambiarPosicion(int posicion){
      posicionActual = posicionActual + posicion;
   }
   public static void agregarACola(CancionIntermediaria cancion){
      cola.add(cancion);
      checkDatos();
   }
   public static void vaciarCola(){
      posicionActual = 0;
      cola.clear();
   }
   public static int getPosicionActual(){
      return posicionActual;
   }

   public static List<CancionIntermediaria> getCola() {
      return cola;
   }
   private static void checkDatos(){
      if ((cola.size()-getPosicionActual()) == 2) {
         List<CancionIntermediaria> colaLocal = ColaReproduccion.getCola();
         CancionIntermediaria cancionTemp = colaLocal.get(ColaReproduccion.getPosicionActual()+1);
         IGUBarraReproduccionController.obtenerCancion(cancionTemp.getCancion(), 
             cancionTemp.isLocal());
      }
   }
   
}
