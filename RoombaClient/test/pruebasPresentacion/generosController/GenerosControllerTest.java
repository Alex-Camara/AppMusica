
package pruebasPresentacion.generosController;

import java.util.ArrayList;
import java.util.List;
import logica.Album;
import logica.Cancion;
import org.junit.Assert;
import org.junit.Test;
import presentacion.IGUGenerosController;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class GenerosControllerTest {
   IGUGenerosController generosController = new IGUGenerosController();
   
   public GenerosControllerTest() {
   }
   
   @Test
   public void recuperarAlbumesDeGenero(){
      List<Album> albumes = new ArrayList<>();
      Album a = new Album();
      a.setIdAlbum(1);
      a.setIdGenero(2);
      albumes.add(a);
      Album a2 = new Album();
      a2.setIdAlbum(2);
      a2.setIdGenero(3);
      albumes.add(a2);
      Album a3 = new Album();
      a3.setIdAlbum(3);
      a3.setIdGenero(2);
      albumes.add(a3);
      Album a4 = new Album();
      a4.setIdAlbum(4);
      a4.setIdGenero(2);
      albumes.add(a4);
      Album a5 = new Album();
      a5.setIdAlbum(5);
      a5.setIdGenero(2);
      albumes.add(a5);
      
      List<Album> albumesGeneros = generosController.recuperarAlbumesDeGenero(2, albumes);
      for (int i = 0; i < albumesGeneros.size(); i++) {
         Assert.assertEquals(2, albumesGeneros.get(i).getIdGenero());
      }  
   }
   @Test
   public void recuperarCancionesPorAlbum(){
      List<Album> albumes = new ArrayList<>();
     Album a = new Album();
      a.setIdAlbum(1);
      a.setIdGenero(2);
      albumes.add(a);
      Album a3 = new Album();
      a3.setIdAlbum(3);
      a3.setIdGenero(2);
      albumes.add(a3);
      Album a4 = new Album();
      a4.setIdAlbum(4);
      a4.setIdGenero(2);
      albumes.add(a4);
      Album a5 = new Album();
      a5.setIdAlbum(5);
      a5.setIdGenero(2);
      albumes.add(a5);
      List<Cancion> canciones = new ArrayList<>();
      Cancion cancion = new Cancion();
      cancion.setAlbum_idAlbum(2);
      canciones.add(cancion);
      Cancion cancion2 = new Cancion();
      cancion2.setAlbum_idAlbum(3);
      canciones.add(cancion2);
      Cancion cancion3 = new Cancion();
      cancion3.setAlbum_idAlbum(2);
      canciones.add(cancion3);
      Cancion cancion4 = new Cancion();
      cancion4.setAlbum_idAlbum(2);
      canciones.add(cancion4);
      Cancion cancion5 = new Cancion();
      cancion5.setAlbum_idAlbum(5);
      canciones.add(cancion5);
      Cancion cancion6 = new Cancion();
      cancion6.setAlbum_idAlbum(2);
      canciones.add(cancion6);
      Cancion cancion7 = new Cancion();
      cancion7.setAlbum_idAlbum(2);
      canciones.add(cancion7);
      
      
      List<Cancion> cancionesGenero = generosController.recuperarCancionesPorAlbum(albumes, canciones);
      for (int i = 0; i < cancionesGenero.size(); i++) {
         Assert.assertEquals(albumes.get(i).getIdAlbum(), cancionesGenero.get(i).getAlbum_idAlbum());
      }  
   }
   
}
