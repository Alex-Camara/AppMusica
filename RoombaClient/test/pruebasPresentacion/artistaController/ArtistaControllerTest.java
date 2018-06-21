package pruebasPresentacion.artistaController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import logica.Cancion;
import org.junit.Assert;
import org.junit.Test;
import presentacion.IGUArtistasController;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class ArtistaControllerTest {
   IGUArtistasController artistasController = new IGUArtistasController();
   public ArtistaControllerTest() {
   }
   
   @Test
   public void seleccionarCancionesArtista(){
      List<Cancion> canciones = new ArrayList<>();
      Cancion cancion = new Cancion();
      cancion.setArtista("Croc");
      canciones.add(cancion);
      Cancion cancion2 = new Cancion();
      cancion2.setArtista("Crystal");
      canciones.add(cancion2);
      Cancion cancion3 = new Cancion();
      cancion3.setArtista("Geyser");
      canciones.add(cancion3);
      Cancion cancion4 = new Cancion();
      cancion4.setArtista("Croc");
      canciones.add(cancion4);
      Cancion cancion5 = new Cancion();
      cancion5.setArtista("Puma");
      canciones.add(cancion5);
      Cancion cancion6 = new Cancion();
      cancion6.setArtista("Croc");
      canciones.add(cancion6);
      Cancion cancion7 = new Cancion();
      cancion7.setArtista("Star");
      canciones.add(cancion7);
      String artista = canciones.get(0).getArtista();
      List<Cancion> cancionesArtista = artistasController.seleccionarCancionesArtista(artista, canciones);
      for (int i = 0; i < cancionesArtista.size(); i++) {
         Assert.assertEquals(artista, cancionesArtista.get(i).getArtista());
      }  
   }
   
}
