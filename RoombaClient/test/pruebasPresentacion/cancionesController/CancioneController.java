
package pruebasPresentacion.cancionesController;

import java.util.ArrayList;
import java.util.List;
import logica.Cancion;
import logica.ListaReproduccion;
import org.junit.Assert;
import org.junit.Test;
import presentacion.IGUCancionesController;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class CancioneController {
   IGUCancionesController cancionesController = new IGUCancionesController();
   
   public CancioneController() {
   }
   
   @Test
   public void recuperarAlbumesDeGenero(){
      List<Cancion> cancionesdeLista = new ArrayList<>();
      Cancion cancion = new Cancion();
      cancion.setNombre("CanciónPrueba");
      cancionesdeLista.add(cancion);
      ListaReproduccion lista = new ListaReproduccion();
      lista.setCanciones(cancionesdeLista);
      List<ListaReproduccion> listas = new ArrayList<>();
      listas.add(lista);
      Boolean repeticion = cancionesController.verificarDuplicada(cancion, lista, listas);
      Assert.assertEquals(repeticion, true);
   }
}
