package logica.reproductor;

import java.io.File;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import logica.Cancion;
import logica.CancionIntermediaria;
import presentacion.IGUBarraReproduccionController;

/**
 * Clase del sobre los elementos que se utilizan para la reproducción de una pista.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Reproductor {

   private static File fileSound;
   private static Media sound;
   private static MediaPlayer mediaPlayer;
   
   /**
    * Método para reproducir una canción
    * @param cancion Cancion a reproducir
    * @param local Boolean para identificar si puede encontrar en un archivo local
    * @param calidad int de la calidad deseada
    */
   public static void reproducir(Cancion cancion, boolean local, int calidad) {
      String ruta = cancion.getRuta();
      String rutaCompleta = obtenerRutaCompleta(ruta, local, calidad);
      if (mediaPlayer != null) {
         boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
         if (playing) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20),
                new KeyValue(mediaPlayer.volumeProperty(), 0)));
            timeline.pause();
            mediaPlayer.stop();
         }
      }
      fileSound = new File(rutaCompleta);
      sound = new Media(fileSound.toURI().toString());
      mediaPlayer = new MediaPlayer(sound);
      mediaPlayer.setOnEndOfMedia ( new Runnable() {
         public void run() {
            List<CancionIntermediaria> colaLocal = ColaReproduccion.getCola();
            if (ColaReproduccion.getPosicionActual() < colaLocal.size()) {
               CancionIntermediaria cancionTemp = colaLocal.get(ColaReproduccion.getPosicionActual());
               reproducir(cancionTemp.getCancion(), cancionTemp.isLocal(), cancionTemp.getCalidad());
            }
         }
      });
      mediaPlayer.play();
      if ((ColaReproduccion.getCola().size() - ColaReproduccion.getPosicionActual()) == 2) {
         List<CancionIntermediaria> colaLocal = ColaReproduccion.getCola();
         CancionIntermediaria cancionTemp = colaLocal.get(ColaReproduccion.getPosicionActual()+1);
         IGUBarraReproduccionController.obtenerCancion(cancionTemp.getCancion(), 
             cancionTemp.isLocal());
      }
   }

   /**
    * Método para pausar o reanudar la reproducción
    * @return Boolean para determinar si hizo una pausa o reanudó. Determinado por el estado del
    * reproductor
    */
   public static boolean pausaReanudar() {
      boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
      if (playing) {
         mediaPlayer.pause();
         return true;
      } else {
         mediaPlayer.play();
         return false;
      }
   }
   
   /**
    * Métod para regresar la reprocción actual al punto inicial
    */
   public static void rebobinar(){
      mediaPlayer.seek(Duration.ZERO);
      mediaPlayer.play();
   }

   /**
    * Método para repetir de manera indefinida la reproducción actual
    * @param repetir
    */
   public static void repetir(boolean repetir) {
      if (mediaPlayer != null) {
         if (repetir) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
         } else {
            mediaPlayer.setCycleCount(1);
            
         }
      }
   }

   /**
    * Método para obtener la ruta completa de la ubicación de un archivo de audio
    * @param ruta String base de la ruta
    * @param local Boolean para determinar si el archivo es local permanente
    * @param calidad int de la calidad del audio
    * @return
    */
   public static String obtenerRutaCompleta(String ruta, boolean local, int calidad) {
      String userHome = System.getProperty("user.home");
      String rutaCompleta;
      if (local) {
         rutaCompleta = userHome + "/RombaFiles/local/" + ruta + "/" + calidad + ".mp3";
      } else {
         rutaCompleta = userHome + "/RombaFiles/cache/" + ruta + "/" + calidad + ".mp3";
      }
      return rutaCompleta;
   }
}
