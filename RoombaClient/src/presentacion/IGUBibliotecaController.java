/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    private Button buttonReproducir;
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
    private Button buttonSiguiente;
    @FXML
    private Button buttonAnterior;
    @FXML
    private Button buttonConfiguracion;
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
    private JFXButton buttonSalir;
    @FXML
    private ImageView imageSalir;
    @FXML
    private JFXButton buttonContenido;
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
    void resaltarGeneros(MouseEvent  event) {
        buttonGeneros.setStyle(RESALTADO);
    }

    @FXML
    void resaltarHistorial(MouseEvent  event) {
        buttonHistorial.setStyle(RESALTADO);
    }

    @FXML
    void resaltarListas(MouseEvent  event) {
        buttonListas.setStyle(RESALTADO);
    }

    @FXML
    void resetAgregarCancion(MouseEvent  event) {
        buttonAgregarLocal.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetAlbumes(MouseEvent  event) {
        buttonAlbumes.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetArtistas(MouseEvent event) {
        buttonArtistas.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetCanciones(MouseEvent  event) {
        buttonCanciones.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetContenido(MouseEvent  event) {
        buttonContenido.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetGeneros(MouseEvent  event) {
        buttonGeneros.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetHistorial(MouseEvent  event) {
        buttonHistorial.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resetListas(MouseEvent  event) {
        buttonListas.setStyle(COLOR_NORMAL);
    }

    @FXML
    void resaltarArtistas(MouseEvent  event) {
        buttonArtistas.setStyle(RESALTADO);
    }

    @FXML
    void resaltarCanciones(MouseEvent  event) {
        buttonCanciones.setStyle(RESALTADO);
    }

    @FXML
    void resaltarContenido(MouseEvent  event) {
        buttonContenido.setStyle(RESALTADO);
    }

    @FXML
    void resaltarAgregarCancion(MouseEvent  event) {
        buttonAgregarLocal.setStyle(RESALTADO);
    }

    @FXML
    void resaltarAlbumes(MouseEvent  event) {
        buttonAlbumes.setStyle(RESALTADO);
    }

    @FXML
    void resetSalida(MouseEvent  event) {

    }

    @FXML
    void resaltarSalida(MouseEvent  event) {

    }

    private boolean biblioteca = true;
    private Usuario usuario = null;
    List<Cancion> canciones = new ArrayList<>(); //Prueba

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void abrirIGUBiblioteca(Stage stageActual, Usuario usuario) {
        try {
            BorderPane rootPane;
            Stage stagePrincipal = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBiblioteca.fxml"));

            IGUBibliotecaController controlador = new IGUBibliotecaController();
            controlador.setUsuario(usuario);
            fxmlLoader.setController(controlador);

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

}
