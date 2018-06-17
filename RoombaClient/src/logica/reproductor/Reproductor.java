package logica.reproductor;

import java.io.File;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import logica.Cancion;
import logica.CancionIntermediaria;
import static logica.reproductor.ColaReproduccion.getPosicionActual;
import presentacion.IGUBarraReproduccionController;

/**
 *
 * @author javr
 */
public class Reproductor {

   private final JFXPanel fxPanel = new JFXPanel();
   private static File fileSound;
   private static Media sound;
   private static MediaPlayer mediaPlayer;
   
   public static void reproducir(Cancion cancion, boolean local, int calidad) {
      String ruta = cancion.getRuta();
      String userHome = System.getProperty("user.home");
      String rutaCompleta;
      if (local) {
         rutaCompleta = userHome + "/RombaFiles/local/" + ruta + "/" + calidad + ".mp3";
      } else {
         rutaCompleta = userHome + "/RombaFiles/cache/" + ruta + "/" + calidad + ".mp3";
      }
      if (mediaPlayer != null) {
         boolean playing = mediaPlayer.getStatus().equals(Status.PLAYING);
         if (playing) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20),
                new KeyValue(mediaPlayer.volumeProperty(), 0)));
            mediaPlayer.stop();
         }
      }
      
      ColaReproduccion.cambiarPosicion(+1);
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
   public static void rebobinar(){
      mediaPlayer.seek(Duration.ZERO);
      mediaPlayer.play();
   }

   public static void repetir(boolean repetir) {
      if (mediaPlayer != null) {
         if (repetir) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
         } else {
            mediaPlayer.setCycleCount(1);
            
         }
      }
   }
}
