/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Usuario;
import logica.conexion.Mensaje;
import logica.conexion.Servidor;

/**
 * FXML Controller class
 *
 * @author javr
 */
public class IGUBibliotecaController implements Initializable {
   @FXML
   private JFXComboBox comboGenero;
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
   private JFXButton buttonSeleccionarArch;
   @FXML
   private JFXButton buttonAgregarArch;
   @FXML
   private JFXButton buttonAgregarListArch;
   @FXML
   private JFXTextField tFieldNombreArch;
   @FXML
   private JFXTextField tFieldArtistaArch;
   @FXML
   private JFXTextField tFieldAlbumArch;
   @FXML
   private ListView listArchivos;
   @FXML
   private Button buttonReproducir;
   @FXML
   private Button buttonSiguiente;
   @FXML
   private Button buttonAnterior;
   @FXML
   private Button buttonConfiguracion;
   @FXML
   private Pane paneCanciones;
   @FXML
   private Pane paneAlbumes;
   @FXML
   private Pane paneArtistas;
   @FXML
   private Pane paneSubirContenido;
   @FXML
   private Pane paneListas;
   @FXML
   private Pane paneAgregarLocal;
   @FXML
   private Label labelPaneArtNombre;
   @FXML
   private TableView<Cancion> tableCanciones;
   @FXML
   private TableColumn<?, ?> tcColumnNombre;
   @FXML
   private TableColumn<?, ?> tcColumnArtista;
   @FXML
   private TableColumn<?, ?> tcColumnCalificacion;
   @FXML
   private TableView<Cancion> tableArtistas;
   @FXML
   private TableColumn<?, ?> taColumnArtista;
   @FXML
   private TableView<Cancion> tableCancionesArtista;
   @FXML
   private TableColumn<?, ?> tcaColumnNombre;
   @FXML
   private ImageView imageReproducir;
   @FXML
   private ImageView imageAnterior;
   @FXML
   private ImageView imageSiguiente;
   @FXML
   private ImageView imageEngrane;
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
   private static final String COLOR_NORMAL = "-fx-background-color: #E0DEF5;";

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
      imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducirSeleccionado.png"));
   }

   @FXML
   void resaltarSiguiente(MouseEvent event) {
      imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forwardSeleccionado.png"));
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
   void resetReprouducir(MouseEvent event) {
      imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducir.png"));
   }

   @FXML
   void resetSiguiente(MouseEvent event) {
      imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forward.png"));
   }

   @FXML
   void resaltarGeneros(MouseEvent event) {
      buttonGeneros.setStyle(RESALTADO);
   }

   @FXML
   void resaltarHistorial(MouseEvent event) {
      buttonHistorial.setStyle(RESALTADO);
   }

   @FXML
   void resaltarListas(MouseEvent event) {
      buttonListas.setStyle(RESALTADO);
   }

   @FXML
   void resetAgregarCancion(MouseEvent event) {
      buttonAgregarLocal.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetAlbumes(MouseEvent event) {
      buttonAlbumes.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetArtistas(MouseEvent event) {
      buttonArtistas.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetCanciones(MouseEvent event) {
      buttonCanciones.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetContenido(MouseEvent event) {
      buttonContenido.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetGeneros(MouseEvent event) {
      buttonGeneros.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetHistorial(MouseEvent event) {
      buttonHistorial.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resetListas(MouseEvent event) {
      buttonListas.setStyle(COLOR_NORMAL);
   }

   @FXML
   void resaltarArtistas(MouseEvent event) {
      buttonArtistas.setStyle(RESALTADO);
   }

   @FXML
   void resaltarCanciones(MouseEvent event) {
      buttonCanciones.setStyle(RESALTADO);
   }

   @FXML
   void resaltarContenido(MouseEvent event) {
      buttonContenido.setStyle(RESALTADO);
   }

   @FXML
   void resaltarAgregarCancion(MouseEvent event) {
      buttonAgregarLocal.setStyle(RESALTADO);
   }

   @FXML
   void resaltarAlbumes(MouseEvent event) {
      buttonAlbumes.setStyle(RESALTADO);
   }

   @FXML
   void resetSalida(MouseEvent event) {

   }

   @FXML
   void resaltarSalida(MouseEvent event) {

   }

   private boolean biblioteca = true;
   private Usuario usuario = null;
   List<Cancion> canciones = new ArrayList<>();
   List<Album> albumes = new ArrayList<>();

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      clicCanciones();
      verificarCreador();

   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   @FXML
   private void clicExplorar() {
      biblioteca = false;
      buttonBiblioteca.setStyle("-fx-background-color: #505050");
      buttonExplorar.setStyle("-fx-background-color: #dc9919");
      ocultarContenidos();
      paneCanciones.setVisible(true);
      //Debe recuperar la lista del servirodr
//      cargarTablaCanciones(canciones);

   }

   @FXML
   private void clicBiblioteca() {
      biblioteca = true;
      buttonExplorar.setStyle("-fx-background-color: #505050");
      buttonBiblioteca.setStyle("-fx-background-color: #dc9919");
      ocultarContenidos();
      paneCanciones.setVisible(true);
      //Debe recuperar la lista de la biblioteca
//      cargarTablaCanciones(canciones);

   }

   @FXML
   private void clicCanciones() {
      ocultarSeleccion();
      buttonCanciones.setStyle("-fx-text-fill: #db981a");
      ocultarContenidos();
      if (biblioteca) {
         //ir por canciones de biblioteca
         Servidor.enviarMensaje(new Mensaje("cancionesServidor"));
         paneCanciones.setVisible(true);
      } else {
         //ir por canciones del servidor
//         cargarTablaCanciones(canciones);
//         paneCanciones.setVisible(true);
      }

   }

   @FXML
   private void clicArtistas() {
      ocultarSeleccion();
      buttonArtistas.setStyle("-fx-text-fill: #db981a");
      ocultarContenidos();
      if (biblioteca) {
         //ir por Artistas sin repeticon (canciones) de biblioteca
         cargarTablaArtistas(canciones);
         paneArtistas.setVisible(true);
      } else {
//         //ir por Artistas sin repeticon (canciones) del servidor
//         cargarTablaArtistas(canciones);
//         paneArtistas.setVisible(true);
      }
      if (canciones != null) {
         List<Cancion> cancionesSeleccionadas = seleccionarCancionesArtista(canciones.get(0).getArtista());
         cargarTablaCancionesArtista(cancionesSeleccionadas);
      }
      agregarListenersTablaArtistas();
      labelPaneArtNombre.setVisible(true);

   }

   @FXML
   private void clicHistorial() {

   }

   @FXML
   private void clicAlbumes() {

   }

   @FXML
   private void clicGeneros() {
      if (albumes != null) {

      }
      Servidor.enviarMensaje(new Mensaje("recuperarAlbumes"));
      paneAlbumes.setVisible(true);

   }

   @FXML
   private void clicSubirLocal() {
      ocultarContenidos();
      paneAgregarLocal.setVisible(true);

   }

   @FXML
   private void clicListas() {

   }

   @FXML
   private void clicSubirContenido() {

   }

   @FXML
   private void clicConfigurar() {

   }

   @FXML
   private void clicReproducir() {

      //Necesario añadir una validación si está reproduciendo para hacer el cambio
      Image image = new Image(getClass().getResourceAsStream("/presentacion/recursos/botonPausa.png"));
      buttonReproducir.setGraphic(new ImageView(image));

   }

   @FXML
   private void clicSiguiente() {

   }

   @FXML
   private void clicAnterior() {

   }

   @FXML
   private void clicBuscar() {

   }

   @FXML
   private void clicSalir() {
      Servidor.cerrarConexion();

   }

   /**
    * Agrega los eventos ante los cuales respondera la tabla.
    */
   public void agregarListenersTablaArtistas() {
      tableArtistas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         if (newSelection != null) {
            
            labelPaneArtNombre.setText(newSelection.getArtista());
            List<Cancion> cancionesSeleccionadas = seleccionarCancionesArtista(newSelection.getArtista());
            cargarTablaCancionesArtista(cancionesSeleccionadas);

         }
      });
   }

   public void ocultarSeleccion() {
      buttonHistorial.setStyle("-fx-text-fill: black");
      buttonCanciones.setStyle("-fx-text-fill: black");
      buttonAlbumes.setStyle("-fx-text-fill: black");
      buttonArtistas.setStyle("-fx-text-fill: black");
      buttonGeneros.setStyle("-fx-text-fill: black");
      buttonAgregarLocal.setStyle("-fx-text-fill: black");
      buttonListas.setStyle("-fx-text-fill: black");
      buttonContenido.setStyle("-fx-text-fill: black");
   }

   public void ocultarContenidos() {
      paneCanciones.setVisible(false);
      paneAlbumes.setVisible(false);
      paneArtistas.setVisible(false);
      paneSubirContenido.setVisible(false);
      paneAgregarLocal.setVisible(false);
      paneListas.setVisible(false);
   }

   private void cargarTablaCanciones(List<Cancion> canciones) {
      ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones
      );
      tcColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
      tcColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
      tcColumnCalificacion.setCellValueFactory(new PropertyValueFactory<>("Calificacion"));
      tableCanciones.setItems(obsCanciones);
   }

   private void cargarTablaArtistas(List<Cancion> canciones) {
      ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones
      );
      taColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
      tableArtistas.setItems(obsCanciones);
   }

   private void verificarCreador() {
      if (usuario.getTipoUsuario().equals("Creador")) {
         buttonContenido.setVisible(true);
      }
   }

   private void cargarTablaCancionesArtista(List<Cancion> cancionesArtista) {
      //Ir o recargar las canciones del artista
      ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(cancionesArtista
      );
      tcaColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
      tableCancionesArtista.setItems(obsCanciones);
   }

   private List<Cancion> seleccionarCancionesArtista(String artista) {
      labelPaneArtNombre.setText(artista);
      List<Cancion> cancionesSeleccionadas = new ArrayList<>();
      for (int i = 0; i < canciones.size(); i++) {
         if (canciones.get(i).getArtista().equals(artista)) {
            cancionesSeleccionadas.add(canciones.get(i));
         }
      }
      return cancionesSeleccionadas;
   }

   public void abrirIGUBiblioteca(Stage stageActual, Usuario usuario) {
      try {
         BorderPane rootPane;
         Stage stagePrincipal = new Stage();
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBiblioteca.fxml"));

         IGUBibliotecaController controlador = new IGUBibliotecaController();
         controlador.setUsuario(usuario);
         fxmlLoader.setController(controlador);
         Servidor.iniciarEscuchaDeMensajes(controlador);
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
         case "cancionesServidor":
            Biblioteca bibliotecaServ = (Biblioteca) mensaje.getObjeto();
            canciones = bibliotecaServ.getCanciones();
            System.out.println("canciones Rec " + canciones.get(0).toString());
            cargarTablaCanciones(canciones);
            break;
         case "albumesRecuperados":
            
            break;

      }

   }

}
