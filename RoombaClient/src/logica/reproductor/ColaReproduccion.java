package logica.reproductor;

import java.util.ArrayList;
import java.util.List;
import logica.CancionIntermediaria;
import presentacion.IGUBarraReproduccionController;

/**
 * Clase para el control de la lista de reproducción actual
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class ColaReproduccion {
   private static List<CancionIntermediaria> cola = new ArrayList<>();
   private static int posicionActual = 0;

   /**
    * Método para agregar una canción a continuación de la actual
    * @param cancion Cancion a agregar
    */
   public static void agregarAContinuacion(CancionIntermediaria cancion){
      cola.add(posicionActual+1, cancion);
      checkDatos();
   }

   /**
    * Método para cambiar la posición de la actual reproducción
    * @param posicion int del cambio de la posición
    */
   public static void cambiarPosicion(int posicion){
      posicionActual = posicionActual + posicion;
   }

   /**
    * Método para agregar un elemento a la lista de reproducción actual
    * @param cancion Canción a agregar al final de la lista
    */
   public static void agregarACola(CancionIntermediaria cancion){
      cola.add(cancion);
      checkDatos();
   }

   /**
    * Método para vaciar la lista de reproducción y regresar la posición de reproducción al inico
    */
   public static void vaciarCola(){
      posicionActual = 0;
      cola.clear();
   }

   /**
    * Método para obtener la posición actual de reproducción de la lista
    * @return int de la posición actual
    */
   public static int getPosicionActual(){
      return posicionActual;
   }

   /**
    * Método obtener la lista de reproducción actual.
    * @return Lista de Cancion
    */
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
