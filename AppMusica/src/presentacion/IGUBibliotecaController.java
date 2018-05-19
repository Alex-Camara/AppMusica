/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logica.Cancion;
import logica.Usuario;

/**
 * FXML Controller class
 *
 * @author javr
 */
public class IGUBibliotecaController implements Initializable {
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
   private JFXButton buttonSubirContenido;
   @FXML
   private ImageView iconoSubirContenido;
   @FXML
   private JFXButton buttonReproducir;
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
   private boolean biblioteca = true;
   private Usuario usuario = null;
   List<Cancion> canciones = new ArrayList<>(); //Prueba

   @Override
   public void initialize(URL url, ResourceBundle rb) {
      Image image = new Image(getClass().getResourceAsStream("/presentacion/recursos/botonPlay.png"));
      buttonReproducir.setGraphic(new ImageView(image));
      Cancion cancion = new Cancion();
      cancion.setNombre("Happy If You're Happy");
      cancion.setArtista("Matt & Kim");
      cancion.setCalificacion(5);
      canciones.add(cancion);
      Cancion cancion2 = new Cancion();
      cancion2.setNombre("La Vie En Rose (cover)");
      cancion2.setArtista("Cassey");
      canciones.add(cancion2);
      //Aquí debe ir a recuperar la lista de canciones de la biblioteca
      cargarTablaCanciones(canciones);
      //verificarCreador();
   }

   public void cargarUsuario(Usuario usuario) {
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
         cargarTablaCanciones(canciones);
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
      agregarListenersTablaArtistas();

   }

   @FXML
   private void clicHistorial() {

   }

   @FXML
   private void clicAlbumes() {

   }

   @FXML
   private void clicGeneros() {

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

   }
   /**
     * Agrega los eventos ante los cuales respondera la tabla.
   */
   public void agregarListenersTablaArtistas() {
      tableArtistas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
         if (newSelection != null) {
            System.out.println("nuEVA");
            labelPaneArtNombre.setText(newSelection.getArtista());
            cargarCancionesArtista(newSelection.getArtista());
            labelPaneArtNombre.setVisible(true);
            tableCancionesArtista.setVisible(true);
         }
      });
   }

   public void ocultarSeleccion() {
      buttonCanciones.setStyle("-fx-text-fill: white");
      buttonCanciones.setStyle("-fx-text-fill: white");
      buttonAlbumes.setStyle("-fx-text-fill: white");
      buttonArtistas.setStyle("-fx-text-fill: white");
      buttonGeneros.setStyle("-fx-text-fill: white");
      buttonAgregarLocal.setStyle("-fx-text-fill: white");
      buttonListas.setStyle("-fx-text-fill: white");
      buttonSubirContenido.setStyle("-fx-text-fill: white");
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
         iconoSubirContenido.setVisible(true);
         buttonSubirContenido.setVisible(true);
      }
   }

   private void cargarCancionesArtista(String artista) {
      //Ir o recargar las canciones del artista
      ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones
      );
      tcaColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
      tableCancionesArtista.setItems(obsCanciones);
      
   }

}
