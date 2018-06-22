package presentacion;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logica.Cancion;
import logica.CancionIntermediaria;
import logica.Usuario;
import logica.conexion.Mensaje;
import logica.conexion.Cliente;
import logica.conexion.ClienteStreaming;
import logica.reproductor.ColaReproduccion;
import logica.reproductor.Reproductor;
import presentacion.Utileria.Emergente;

/**
 * Clase del controlador para mostrar la barra de reproducción.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 *
 */
public class IGUBarraReproduccionController implements Initializable {

   @FXML
   private Pane paneBarraReproduccion;
   @FXML
   private Label labelTituloCancion;
   @FXML
   private ImageView imageEngrane;
   @FXML
   private ImageView imageSiguiente;
   @FXML
   private ImageView imageAnterior;
   @FXML
   private ImageView imageReproducir;
   @FXML
   private ImageView imageRepetir;
   @FXML
   private Label labelCalificacion;
   @FXML
   private ImageView imageEstrella1;
   @FXML
   private ImageView imageEstrella2;
   @FXML
   private ImageView imageEstrella3;
   @FXML
   private ImageView imageEstrella4;
   @FXML
   private ImageView imageEstrella5;
   @FXML
   private Label labelArtista;
   @FXML
   private Label labelTituloArtista;

   List<ImageView> estrellas = new ArrayList<>();
   private Cancion cancion;
   private static int calidad = 0;
   private boolean reproduciendo = false;
   private Usuario usuario = null;
   private boolean repeticion = false;

   /**
    * Método para asignar una canción que actualmente se reproduce
    *
    * @param cancion Cancion reproduciendo
    */
   public void setCancion(Cancion cancion) {
      this.cancion = cancion;
   }

   @FXML
   void resaltarEstrella(MouseEvent event) {
      ImageView imagen = (ImageView) (event.getSource());
      imagen.setImage(new Image("/presentacion/recursos/iconos/estrellaC.png"));
   }

   @FXML
   void resetEstrella(MouseEvent event) {
      ImageView imagen = (ImageView) (event.getSource());
      imagen.setImage(new Image("/presentacion/recursos/iconos/estrella.png"));
   }

   @FXML
   void resaltarAnterior(MouseEvent event) {
      imageAnterior.setImage(new Image("/presentacion/recursos/iconos/rewindSeleccionado.png"));
   }

   @FXML
   void resaltarEngrane(MouseEvent event) {
      imageEngrane.setImage(new Image("/presentacion/recursos/iconos/settingsSeleccionado.png"));
   }

   @FXML
   void resaltarReproducir(MouseEvent event) {
      if (reproduciendo) {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/pausaSeleccionado.png"));
      } else {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducirSeleccionado.png"));
      }
   }

   @FXML
   void resaltarSiguiente(MouseEvent event) {
      imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forwardSeleccionado.png"));
   }

   @FXML
   void resaltarRepetir(MouseEvent event) {
      if (repeticion) {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetir.png"));
      } else {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetirSeleccionado.png"));
      }
   }

   @FXML
   void resetAnterior(MouseEvent event) {
      imageAnterior.setImage(new Image("/presentacion/recursos/iconos/rewind.png"));
   }

   @FXML
   void resetEngrane(MouseEvent event) {
      imageEngrane.setImage(new Image("/presentacion/recursos/iconos/settings.png"));
   }

   @FXML
   void resetReproducir(MouseEvent event) {
      if (reproduciendo) {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/pausa.png"));
      } else {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducir.png"));
      }
   }

   @FXML
   void resetSiguiente(MouseEvent event) {
      imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forward.png"));
   }

   @FXML
   void resetRepetir(MouseEvent event) {
      if (repeticion) {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetirSeleccionado.png"));
      } else {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetir.png"));
      }

   }

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      estrellas.add(imageEstrella1);
      estrellas.add(imageEstrella2);
      estrellas.add(imageEstrella3);
      estrellas.add(imageEstrella4);
      estrellas.add(imageEstrella5);
   }

   /**
    * Método para cargar la barra de reproducción con sus elementos y la asignación de la canción a
    * reproducir
    *
    * @param cancionLocal Canción a reproducir
    */
   public void cargarBarraReproduccion(Cancion cancionLocal) {
      labelTituloCancion.setText(cancionLocal.getNombre());
      labelTituloArtista.setText(cancionLocal.getArtista());

      labelTituloCancion.setVisible(true);
      labelCalificacion.setVisible(true);
      labelArtista.setVisible(true);
      labelTituloArtista.setVisible(true);

      if (IGUBibliotecaController.biblioteca) {
         imageEstrella1.setVisible(true);
         imageEstrella2.setVisible(true);
         imageEstrella3.setVisible(true);
         imageEstrella4.setVisible(true);
         imageEstrella5.setVisible(true);

         iluminarEstrellas(cancionLocal.getCalificacion());
      } else {
         imageEstrella1.setDisable(true);
         imageEstrella2.setDisable(true);
         imageEstrella3.setDisable(true);
         imageEstrella4.setDisable(true);
         imageEstrella5.setDisable(true);
      }

      cancion = cancionLocal;
   }

   @FXML
   private void clicReproducir() {
      boolean pausa = Reproductor.pausaReanudar();
      reproduciendo = !pausa;
      if (reproduciendo) {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/pausa.png"));
      } else {
         imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducir.png"));
      }
   }

   @FXML
   private void clicRepetir() {
      repeticion = !repeticion;
      Reproductor.repetir(repeticion);
      if (repeticion) {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetirSeleccionado.png"));
      } else {
         imageRepetir.setImage(new Image("/presentacion/recursos/iconos/repetir.png"));
      }
   }

   @FXML
   private void clicAdelante() {
      if (reproduciendo) {
         List<CancionIntermediaria> colaTemporal = ColaReproduccion.getCola();
         if (ColaReproduccion.getPosicionActual() < colaTemporal.size()) {
            CancionIntermediaria cancionTemp = colaTemporal.get(ColaReproduccion.getPosicionActual());
            cargarBarraReproduccion(cancionTemp.getCancion());
            Reproductor.reproducir(cancionTemp.getCancion(),
                cancionTemp.isLocal(), cancionTemp.getCalidad());
            ColaReproduccion.cambiarPosicion(+1);
         } else {
            Reproductor.rebobinar();
         }
      }
   }

   @FXML
   private void clicAtras() {
      if (reproduciendo) {
         List<CancionIntermediaria> colaTemporal = ColaReproduccion.getCola();
         if (ColaReproduccion.getPosicionActual() > 0) {
            CancionIntermediaria cancionTemp = colaTemporal.get(ColaReproduccion.getPosicionActual() - 1);
            cargarBarraReproduccion(cancionTemp.getCancion());
            Reproductor.reproducir(cancionTemp.getCancion(),
                cancionTemp.isLocal(), cancionTemp.getCalidad());
            if (ColaReproduccion.getPosicionActual() != 1) {
               ColaReproduccion.cambiarPosicion(-1);
            }
         } else {
            Reproductor.rebobinar();
         }
      }
   }

   @FXML
   void calificarCancion(MouseEvent event) {
      ImageView imagen = (ImageView) (event.getSource());
      String idImagen = imagen.getId();
      String stringCalificacion = idImagen.substring(idImagen.length() - 1);
      int calificacion = Integer.valueOf(stringCalificacion);
      Integer[] calificacionEnvio = new Integer[2];

      cancion.setCalificacion(calificacion);
      iluminarEstrellas(calificacion);
      System.out.println("calificacion: " + cancion.getCalificacion());
      calificacionEnvio[0] = cancion.getIdCancion();
      calificacionEnvio[1] = cancion.getCalificacion();
      Mensaje mensajeEnviar = new Mensaje("actualizarCalificación");
      mensajeEnviar.setObjeto(calificacionEnvio);
      Cliente.enviarMensaje(mensajeEnviar);
   }

   private void iluminarEstrellas(int calificacion) {
      for (int i = 0; i < estrellas.size(); i++) {
         if (i < calificacion) {
            estrellas.get(i).setImage(new Image("/presentacion/recursos/iconos/estrellaC.png"));
         } else {
            estrellas.get(i).setImage(new Image("/presentacion/recursos/iconos/estrella.png"));
         }
      }
   }

   @FXML
   void clicConfigurar(ActionEvent event) {
      final String[] data = {"Alta", "Media", "Baja"};
      List<String> listICalidades = Arrays.asList(data);
      ChoiceDialog choiceCalidad = new ChoiceDialog(listICalidades.get(0), listICalidades);
      choiceCalidad.setHeaderText("Calidad del audio");
      choiceCalidad.setContentText("Selecciona la calidad del audio");
      Optional<ButtonType> eleccion = choiceCalidad.showAndWait();
      if (eleccion.isPresent()) {
         String selected = choiceCalidad.getResult().toString();
         switch (selected) {
            case "Alta":
               calidad = 2;
               break;
            case "Media":
               calidad = 1;
               break;
            case "Baja":
               calidad = 0;
               break;
         }
      }
      calidad = 2;
   }

   /**
    * Método para recuperar la canción y reproducirla
    *
    * @param cancion Canción a reproducir
    */
   public void recuperarCancionYReproducir(Cancion cancion) {
      final boolean local = false;
      if (!checkExistenciaDescarga(cancion.getRuta())) {
         if (!checkExistenciaCache(cancion.getRuta(), calidad)) {
            Thread recibir = new Thread() {
               public void run() {
                  try {
                     ClienteStreaming.solicitarCancion(cancion.getRuta(), calidad);
                     Future<Integer> futureTask = ClienteStreaming.recuperarCancion(cancion.getRuta(), local, calidad);
                     while (!futureTask.isDone()) {

                     }
                     if (futureTask.get() == 1) {
                        cargarBarraReproduccion(cancion);
                        restaurarCola(new CancionIntermediaria(cancion, local, calidad));
                        Reproductor.reproducir(cancion, local, calidad);
                        agregarCancionHistorial(cancion);
                        setReproduciendo();
                        ColaReproduccion.cambiarPosicion(+1);

                     } else {
                        Emergente.cargarEmergente("Error", "Imposible recuperar la canción, intenta más tarde.");
                     }
                  } catch (IOException | InterruptedException | ExecutionException ex) {
                     Logger.getLogger(IGUBarraReproduccionController.class.getName()).log(Level.SEVERE, null, ex);
                  }
               }
            };
            recibir.start();
         } else {
            cargarBarraReproduccion(cancion);
            setReproduciendo();
            restaurarCola(new CancionIntermediaria(cancion, local, calidad));
            Reproductor.reproducir(cancion, local, calidad);
            agregarCancionHistorial(cancion);
            ColaReproduccion.cambiarPosicion(+1);
         }
      } else {
         final int MAXIMA_CALIDAD = 2;
         cargarBarraReproduccion(cancion);
         setReproduciendo();
         restaurarCola(new CancionIntermediaria(cancion, !local, MAXIMA_CALIDAD));
         Reproductor.reproducir(cancion, !local, MAXIMA_CALIDAD);
         agregarCancionHistorial(cancion);
         ColaReproduccion.cambiarPosicion(+1);
      }
   }

   /**
    * Método para cargar el controller de la ventana
    *
    * @param usuario Usuario que ha ingresado al sistema
    * @return Pane con la asignación del controller
    */
   public Pane abrirIGUBarraReproduccion(Usuario usuario) {
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBarraReproduccion.fxml"));

         fxmlLoader.setController(this);
         paneBarraReproduccion = fxmlLoader.load();
         this.usuario = usuario;

      } catch (IOException ex) {
         Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
      }
      return paneBarraReproduccion;
   }

   private static boolean checkExistenciaCache(String ruta, int calidad) {
      boolean check = false;
      boolean checkCache = true;
      String rutaCompleta = Reproductor.obtenerRutaCompleta(ruta, checkCache, calidad);
      File fileCheck = new File(rutaCompleta);
      return fileCheck.exists();
   }

   private static boolean checkExistenciaDescarga(String ruta) {
      boolean check = false;
      boolean local = true;
      final int MAXIMA_CALIDAD = 2;
      String rutaCompleta = Reproductor.obtenerRutaCompleta(ruta, local, MAXIMA_CALIDAD);
      File fileCheck = new File(rutaCompleta);
      return fileCheck.exists();
   }

   private void agregarCancionHistorial(Cancion cancion) {
      HashMap<Usuario, Cancion> hash = new HashMap<>();
      hash.put(usuario, cancion);
      Mensaje mensajeAgregarHistorial = new Mensaje("insertarEnHistorial");
      mensajeAgregarHistorial.setObjeto(hash);
      Cliente.enviarMensaje(mensajeAgregarHistorial);
   }

   private void restaurarCola(CancionIntermediaria cancion) {
      ColaReproduccion.vaciarCola();
      ColaReproduccion.agregarACola(cancion);
      System.out.println("Size " + ColaReproduccion.getCola().size());
   }

   private void setReproduciendo() {
      reproduciendo = true;
      imageReproducir.setImage(new Image("/presentacion/recursos/iconos/pausa.png"));
   }

   void reajustarDatosColaReproduccion(Cancion cancion, boolean vaACola) {
      boolean local = false;
      int calidad = this.calidad;
      if (checkExistenciaDescarga(cancion.getRuta())) {
         local = true;
         calidad = 2;
      } 
      if (vaACola) {
         ColaReproduccion.agregarACola(new CancionIntermediaria(cancion, local, calidad));
      } else {
         ColaReproduccion.agregarAContinuacion(new CancionIntermediaria(cancion, local, calidad));
      }
   }

   /**
    * Método para descargar una canción a un directorio cache con una calidad determinada
    *
    * @param cancion Canción a descargar
    * @param local Boolean en caso de encontrarse en un directorio local
    * @return Boolean del estatus de éxito
    */
   public static boolean obtenerCancion(Cancion cancion, boolean local) {
      System.out.println("Obtener");
      if (!checkExistenciaCache(cancion.getRuta(), calidad)) {
         try {
            Future<Integer> futureTask = ClienteStreaming.recuperarCancion(cancion.getRuta(), local, calidad);
            ClienteStreaming.solicitarCancion(cancion.getRuta(), calidad);
            while (!futureTask.isDone()) {

            }
            if (futureTask.get() == 1) {
               return true;
            } else {
               Emergente.cargarEmergente("Error", "Imposible recuperar la canción, intenta más tarde.");
            }
         } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(IGUBarraReproduccionController.class.getName()).log(Level.SEVERE, null, ex);
         }
      } else {
         return true;
      }
      return false;
   }

   /**
    * Método para descargar una canción a un directorio local
    *
    * @param cancion Canción a descargar
    * @param local Boolean en caso de encontrarse en un directorio local
    */
   public static void descargarCancion(Cancion cancion, boolean local) {
      final int MAXIMA_CALIDAD = 2;
      if (!checkExistenciaDescarga(cancion.getRuta())) {
         try {
            Future<Integer> futureTask = ClienteStreaming.recuperarCancion(cancion.getRuta(), local, MAXIMA_CALIDAD);
            ClienteStreaming.solicitarCancion(cancion.getRuta(), MAXIMA_CALIDAD);
            while (!futureTask.isDone()) {

            }
            if (futureTask.get() == 1) {
               Platform.runLater(() -> {
                  Emergente.cargarEmergente("Aviso", "Canción descargada en carpeta local");
               });
            } else {
               Emergente.cargarEmergente("Error", "Imposible recuperar la canción, intenta más tarde.");
            }
         } catch (IOException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(IGUBarraReproduccionController.class.getName()).log(Level.SEVERE, null, ex);
         }
      }
   }
}
