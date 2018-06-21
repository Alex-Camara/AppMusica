/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasPresentacion.albumController;

import java.util.ArrayList;
import java.util.List;
import logica.Cancion;
import org.junit.Assert;
import org.junit.Test;
import presentacion.IGUAlbumesController;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class AlbumControllerTest {
   IGUAlbumesController albumesController = new IGUAlbumesController();
   
   public AlbumControllerTest() {
   }
   
   @Test
   public void recuperarCancionesPorAlbum(){
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
      
      
      List<Cancion> cancionesAlbumes = albumesController.recuperarCancionesPorAlbum(2, canciones);
      for (int i = 0; i < cancionesAlbumes.size(); i++) {
         Assert.assertEquals(2, cancionesAlbumes.get(i).getAlbum_idAlbum());
      }  
   }
   
}
