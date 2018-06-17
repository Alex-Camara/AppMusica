/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Genero;
import logica.ListaReproduccion;
import logica.Usuario;
import logica.conexion.Mensaje;
import logica.conexion.Cliente;
import logica.conexion.ClienteStreaming;
import org.apache.commons.io.FileUtils;
import presentacion.Utileria.Emergente;

/**
 * FXML Controller class
 *
 * @author javr
 */
public class IGUBibliotecaController implements Initializable {

   @FXML
   private BorderPane borderPanePrincipal;
   @FXML
   private Pane paneGeneral;
   @FXML
   private Pane paneBarraReproduccion;
   @FXML
   private JFXButton buttonBiblioteca;
   @FXML
   private JFXButton buttonExplorar;
   @FXML
   private JFXButton buttonHistorial;
   @FXML
   private JFXButton buttonCanciones;
   @FXML
   private JFXButton buttonAlbumes;
   @FXML
   private JFXButton buttonArtistas;
   @FXML
   private JFXButton buttonGeneros;
   @FXML
   private JFXButton buttonAgregarLocal;
   @FXML
   private JFXButton buttonListas;
   @FXML
   private JFXButton buttonContenido;
   @FXML
   private JFXButton buttonSalir;
   @FXML
   private JFXButton buttonSeleccionarArchivo;
   @FXML
   private JFXButton buttonAgregarArchivo;
   @FXML
   private JFXButton buttonAgregarListArchivos;
   @FXML
   private JFXTextField tFieldNombreArchivo;
   @FXML
   private JFXTextField tFieldArtistaArchivo;
   @FXML
   private JFXTextField tFieldAlbumArchivo;
   @FXML
   private JFXTextField tFieldBuscar;
   @FXML
   private ListView listArchivos;
   @FXML
   private Pane paneSubirContenido;
   @FXML
   private ImageView imageHistorial;
   @FXML
   private ImageView imageCancion;
   @FXML
   private ImageView imageArtista;
   @FXML
   private ImageView imageGenero;
   @FXML
   private ImageView imageAlbum;
   @FXML
   private ImageView imageAgregarCancion;
   @FXML
   private ImageView imageListas;
   @FXML
   private ImageView imageSalir;
   @FXML
   private ImageView imageContenido;

   private static final String RESALTADO = "-fx-background-color:#FFD7B4;";
   private static final String COLOR_TOGGLEBUTTON_NORMAL = "-fx-background-color: #F0EFF7;";
   private static final String COLOR_TEXTO_NORMAL = "-fx-text-fill: black;";
   private static final String COLOR_TEXTO_RESALTADO = "-fx-text-fill: #DB981A;";
   private boolean biblioteca = true;
   private Usuario usuario = null;
   private int idBiblioteca;
   List<Cancion> cancionesExternas = new ArrayList<>();
   List<Album> albumesExternos = new ArrayList<>();
   List<Genero> generosExternos = new ArrayList<>();
   List<Cancion> canciones = new ArrayList<>();
   List<Album> albumes = new ArrayList<>();
   List<Genero> generos = new ArrayList<>();
   List<ListaReproduccion> listasReproduccion = new ArrayList<>();

   private Pane paneAlbumes;
   private Pane paneArtistas;
   private Pane paneGeneros;
   private Pane paneAgregarLocal;
   private Pane paneCanciones;
   private Pane paneListasReproduccion;

   IGUAlbumesController controladorAlbumes;
   IGUArtistasController controladorArtistas;
   IGUGenerosController controladorGeneros;
   IGUAgregarCancionLocalController controladorAgregarLocal;
   IGUCancionesController controladorCanciones;
   IGUBarraReproduccionController controladorBarraReproduccion;
   IGUListasReproduccionController controladorListas;

   @FXML
   void resetSalida(MouseEvent event) {
      imageSalir.setImage(new Image("/presentacion/recursos/iconos/salir.png"));
   }

   @FXML
   void resaltarSalida(MouseEvent event) {
      imageSalir.setImage(new Image("/presentacion/recursos/iconos/salirSeleccionado.png"));
   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      cargarPanes();
      clicCanciones();
      verificarCreador();
      crearDirectorio();
      /*Mensaje mensajeCanciones = new Mensaje("recuperarCanciones");
            mensajeCanciones.setObjeto(usuario);
        Cliente.enviarMensaje(mensajeCanciones);*/

   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   private void cargarPanes() {
      controladorAlbumes = new IGUAlbumesController();
      paneAlbumes = controladorAlbumes.abrirIGUAlbumes();

      controladorArtistas = new IGUArtistasController();
      paneArtistas = controladorArtistas.abrirIGUArtistas();

      controladorGeneros = new IGUGenerosController();
      paneGeneros = controladorGeneros.abrirIGUGeneros();

      controladorAgregarLocal = new IGUAgregarCancionLocalController();
      paneAgregarLocal = controladorAgregarLocal.abrirIGUAgregarCancionLocal();

      controladorCanciones = new IGUCancionesController();
      paneCanciones = controladorCanciones.abrirIGUCanciones();

      controladorListas = new IGUListasReproduccionController();
      paneListasReproduccion = controladorListas.abrirIGUListasReproduccion();

      controladorBarraReproduccion = new IGUBarraReproduccionController();
      paneBarraReproduccion = controladorBarraReproduccion.abrirIGUBarraReproduccion(usuario);
      borderPanePrincipal.setBottom(paneBarraReproduccion);
   }

   @FXML
   private void clicExplorar() {
      biblioteca = false;
      buttonExplorar.setStyle(RESALTADO);
      buttonBiblioteca.setStyle(COLOR_TOGGLEBUTTON_NORMAL);

      clicCanciones();
      //paneCanciones.setVisible(true);
      //Debe recuperar la lista del servirodr
//      cargarTablaCanciones(canciones);
   }

   @FXML
   private void clicBiblioteca() {
      biblioteca = true;
      buttonBiblioteca.setStyle(RESALTADO);
      buttonExplorar.setStyle(COLOR_TOGGLEBUTTON_NORMAL);

      clicCanciones();
      //paneCanciones.setVisible(true);
      //Debe recuperar la lista de la biblioteca
//      cargarTablaCanciones(canciones);
   }

   @FXML
   private void clicCanciones() {
      ocultarSeleccion();
      buttonCanciones.setStyle(COLOR_TEXTO_RESALTADO);
      if (biblioteca) {
         //ir por canciones de biblioteca
         Mensaje mensajeCanciones = new Mensaje("recuperarCanciones");
         mensajeCanciones.setObjeto(usuario);
         idBiblioteca = usuario.getIdBiblioteca();
         Cliente.enviarMensaje(mensajeCanciones);
         borderPanePrincipal.setCenter(paneCanciones);
      } else {
         //ir por canciones del servidor
         Mensaje mensajeRecuperarCanciones = new Mensaje("recuperarCancionesExternas");
         Cliente.enviarMensaje(mensajeRecuperarCanciones);
         borderPanePrincipal.setCenter(paneCanciones);
      }

   }

   @FXML
   private void clicArtistas() {
      ocultarSeleccion();
      buttonArtistas.setStyle(COLOR_TEXTO_RESALTADO);

      borderPanePrincipal.setCenter(paneArtistas);
      controladorArtistas.setControladorBarraReproduccion(controladorBarraReproduccion);

      if (biblioteca) {
         controladorArtistas.cargarTablaArtistas(canciones);
      } else {
         controladorArtistas.cargarTablaArtistas(cancionesExternas);
      }
   }

   @FXML
   private void clicHistorial() {
      ocultarSeleccion();
      buttonHistorial.setStyle(COLOR_TEXTO_RESALTADO);
      Mensaje mensaje = new Mensaje("recuperarHistorial");
      mensaje.setObjeto(usuario);
      Cliente.enviarMensaje(mensaje);
      paneCanciones.setVisible(true);

   }

   @FXML
   private void clicAlbumes() {
      ocultarSeleccion();
      buttonAlbumes.setStyle(COLOR_TEXTO_RESALTADO);

      borderPanePrincipal.setCenter(paneAlbumes);
      controladorAlbumes.setControladorBarraReproduccion(controladorBarraReproduccion);
      if (biblioteca) {
         controladorAlbumes.cargarTablaAlbumes(albumes, canciones);
      } else {
         controladorAlbumes.cargarTablaAlbumes(albumes, cancionesExternas);
      }

   }

   @FXML
   private void clicGeneros() {
      ocultarSeleccion();
      buttonGeneros.setStyle(COLOR_TEXTO_RESALTADO);
      borderPanePrincipal.setCenter(paneGeneros);
      controladorGeneros.setControladorBarraReproduccion(controladorBarraReproduccion);

      if (biblioteca) {
         controladorGeneros.cargarTablaGeneros(generos, albumes, canciones);
      } else {
         controladorGeneros.cargarTablaGeneros(generosExternos, albumesExternos, cancionesExternas);
      }

   }

   @FXML
   private void clicSubirLocal() {
      clicBiblioteca();
      ocultarSeleccion();
      Cliente.enviarMensaje(new Mensaje("recuperarCatalogoGeneros"));
      buttonAgregarLocal.setStyle(COLOR_TEXTO_RESALTADO);

      borderPanePrincipal.setCenter(paneAgregarLocal);
      //controladorAgregarLocal.setVisibilidad(true);
   }

   @FXML
   private void clicListas() {
      clicBiblioteca();
      ocultarSeleccion();
      buttonListas.setStyle(COLOR_TEXTO_RESALTADO);
      borderPanePrincipal.setCenter(paneListasReproduccion);
      controladorListas.setListasReproduccion(listasReproduccion);
      controladorListas.setControladorBarraReproduccion(controladorBarraReproduccion);
      controladorListas.cargarTablaListas();
      //paneListasReproduccion.setVisibilidad(true);
   }

   @FXML
   private void clicSubirContenido() {
      ocultarSeleccion();
      buttonContenido.setStyle(COLOR_TEXTO_RESALTADO);
   }

   @FXML
   private void buscarCancion(KeyEvent event) {
      if (event.getCode() == KeyCode.ENTER) {
         String nombre = tFieldBuscar.getText();
         if (!nombre.isEmpty()) {
            ocultarSeleccion();
            Mensaje mensaje = new Mensaje("buscarCancion");
            mensaje.setObjeto(nombre);
            Cliente.enviarMensaje(mensaje);
            borderPanePrincipal.setCenter(paneCanciones);
            //Falta añadir cuando se limpia la consulta y entonces vuelves a buscar las otras canciones
         }
      }
   }

   @FXML
   private void clicSalir() {
      Mensaje mensajeSalida = new Mensaje("cerrarConexión");
      Cliente.enviarMensaje(mensajeSalida);
      ClienteStreaming.cerrarConexion();
      Cliente.cerrarConexion();
      String userHome = System.getProperty("user.home");
      String finalPath = userHome + "RombaFiles/cache/";
      File file = new File(finalPath);
      limpiarCache(file);

   }

   public void ocultarSeleccion() {
      buttonHistorial.setStyle(COLOR_TEXTO_NORMAL);
      buttonCanciones.setStyle(COLOR_TEXTO_NORMAL);
      buttonAlbumes.setStyle(COLOR_TEXTO_NORMAL);
      buttonArtistas.setStyle(COLOR_TEXTO_NORMAL);
      buttonGeneros.setStyle(COLOR_TEXTO_NORMAL);
      buttonAgregarLocal.setStyle(COLOR_TEXTO_NORMAL);
      buttonListas.setStyle(COLOR_TEXTO_NORMAL);
      buttonContenido.setStyle(COLOR_TEXTO_NORMAL);
   }

   private void verificarCreador() {
      if (usuario.getTipoUsuario().equals("Creador")) {
         buttonContenido.setVisible(true);
      }
   }

   private static void crearDirectorio() {
      String userHome = System.getProperty("user.home");
      File rommbaCache = new File(userHome + "/RombaFiles/cache");
      File rommbaLocal = new File(userHome + "/RombaFiles/local");
      boolean check = false;
      if (rommbaCache.isDirectory() && rommbaLocal.isDirectory()) {
         check = true;
      } else {
         if (!rommbaCache.isDirectory()) {
            check = rommbaCache.mkdirs();
         }
         if (!rommbaLocal.isDirectory()) {
            check = rommbaLocal.mkdirs();
         }
      }
      if (!check) {
         Emergente.cargarEmergente("Error", "Imposible crear las carpetas de la aplicación");
      }
   }

   public void abrirIGUBiblioteca(Stage stageActual, Usuario usuario) {
      try {
         BorderPane rootPane;
         Stage stagePrincipal = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBiblioteca.fxml"));

         IGUBibliotecaController controlador = new IGUBibliotecaController();
         controlador.setUsuario(usuario);
         fxmlLoader.setController(controlador);
         Cliente.iniciarEscuchaDeMensajes(controlador);
         rootPane = fxmlLoader.load();
         Scene scene = new Scene(rootPane);
         stagePrincipal.setScene(scene);
         stagePrincipal.setTitle("ROOMBA Biblioteca");
         stagePrincipal.show();
         stageActual.close();

      } catch (IOException ex) {
         Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public void recibirMensaje(Object respuesta) {
      Mensaje mensaje = (Mensaje) respuesta;
      String asunto = mensaje.getAsunto();
      switch (asunto) {
         case "canciones":
            Biblioteca bibliotecaServ = (Biblioteca) mensaje.getObjeto();
            canciones = bibliotecaServ.getCanciones();
            //System.out.println("canciones Rec " + canciones.get(0).toString());
            //controladorCanciones.cargarTablaCanciones(canciones, controladorBarraReproduccion);
            break;
         case "albumes":
            albumes = (List<Album>) mensaje.getObjeto();
            //System.out.println("albumes Rec " + albumes.get(0).getNombre());
            //System.out.println("albumes Rec " + albumes.get(0).getIdAlbum());
            break;
         case "generos":
            generos = (List<Genero>) mensaje.getObjeto();
            //System.out.println("Generos " + generos.get(0).getNombre());
            break;
         case "historial":
            List<Cancion> cancionesHistorial = (List<Cancion>) mensaje.getObjeto();
            //System.out.println("Canciones Historial " + cancionesHistorial.get(0).getNombre());
            controladorCanciones.cargarTablaCanciones(cancionesHistorial, controladorBarraReproduccion);
            break;
         case "catalogoGeneros":
            List<Genero> catalogoGeneros = (List<Genero>) mensaje.getObjeto();
            //System.out.println("Gen catalogo " + catalogoGeneros.get(0).getNombre());
            controladorAgregarLocal.cargarComboGeneros(catalogoGeneros);
            break;
         case "listasReproduccion":
            listasReproduccion = (List<ListaReproduccion>) mensaje.getObjeto();
            controladorCanciones.setListasReproduccion(listasReproduccion);
            controladorCanciones.cargarTablaCanciones(canciones, controladorBarraReproduccion);
            //System.out.println("Listas " + listasReproduccion.get(0).getNombre());
            break;
         case "cancionesExternas":
            cancionesExternas = (List<Cancion>) mensaje.getObjeto();
            //System.out.println("Canciones Historial " + cancionesHistorial.get(0).getNombre());
            controladorCanciones.cargarTablaCanciones(cancionesExternas, controladorBarraReproduccion);
            break;
         case "albumesExternos":
            albumesExternos = (List<Album>) mensaje.getObjeto();
            //System.out.println("albumes Rec " + albumes.get(0).getNombre());
            //System.out.println("albumes Rec " + albumes.get(0).getIdAlbum());
            break;
         case "generosExternos":
            generosExternos = (List<Genero>) mensaje.getObjeto();
            //System.out.println("Generos " + generos.get(0).getNombre());
            break;
         case "cancionEncontrada":
            List<Cancion> cancionesEncontradas = (List<Cancion>) mensaje.getObjeto();
            canciones = cancionesEncontradas;
            controladorCanciones.cargarTablaCanciones(canciones, controladorBarraReproduccion);
            //No sé si con esto basta o es necesario también recargar álbumes
            break;
         case "cerrarConexión":
            Cliente.cerrarConexion();
            break;

      }

   }

   /**
    * Método para eliminar las carpetas de caché. Gracias a Jeff Learman.
    *
    * @param file archivo con el path a eliminar
    */
   private void limpiarCache(File cache) {
      try {
         //No funciona :l
         FileUtils.deleteDirectory(cache);
         System.out.println("Limpio");
      } catch (IOException ex) {
         Logger.getLogger(IGUBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

}
