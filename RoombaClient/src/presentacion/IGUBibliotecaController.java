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
    private TableView<Album> tableAlbumes;
    @FXML
    private TableColumn<?, ?> talColumnAlbum;
    @FXML
    private Label labelPaneAlbumNombre;
    @FXML
    private TableView<Cancion> tableCancionesAlbum;
    @FXML
    private TableColumn<?, ?> tcaColumnNombreAlbum;
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
    void resaltarEstrella(MouseEvent event) {
        ImageView imagen = (ImageView) (event.getSource());
        imagen.setImage(new Image("/presentacion/recursos/iconos/estrellaHover.png"));
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
    void resetSalida(MouseEvent event) {
        imageSalir.setImage(new Image("/presentacion/recursos/iconos/salir.png"));
    }

    @FXML
    void resaltarSalida(MouseEvent event) {
        imageSalir.setImage(new Image("/presentacion/recursos/iconos/salirSeleccionado.png"));
    }

    private static final String RESALTADO = "-fx-background-color:#FFD7B4;";
    private static final String COLOR_TOGGLEBUTTON_NORMAL = "-fx-background-color: #F0EFF7;";
    private static final String COLOR_TEXTO_NORMAL = "-fx-text-fill: black;";
    private static final String COLOR_TEXTO_RESALTADO = "-fx-text-fill: #DB981A;";
    private boolean biblioteca = true;
    private Usuario usuario = null;
    List<Cancion> canciones = new ArrayList<>();
    List<Album> albumes = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clicCanciones();
        verificarCreador();

        agregarListenersTablaArtistas();
        agregarListenersTablaAlbumes();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void clicExplorar() {
        biblioteca = false;
        buttonExplorar.setStyle(RESALTADO);
        buttonBiblioteca.setStyle(COLOR_TOGGLEBUTTON_NORMAL);
        ocultarContenidos();
        paneCanciones.setVisible(true);
        //Debe recuperar la lista del servirodr
//      cargarTablaCanciones(canciones);

    }

    @FXML
    private void clicBiblioteca() {
        biblioteca = true;
        buttonBiblioteca.setStyle(RESALTADO);
        buttonExplorar.setStyle(COLOR_TOGGLEBUTTON_NORMAL);
        ocultarContenidos();
        paneCanciones.setVisible(true);
        //Debe recuperar la lista de la biblioteca
//      cargarTablaCanciones(canciones);

    }

    @FXML
    private void clicCanciones() {
        ocultarSeleccion();
        buttonCanciones.setStyle(COLOR_TEXTO_RESALTADO);
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
        buttonArtistas.setStyle(COLOR_TEXTO_RESALTADO);
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

        labelPaneArtNombre.setVisible(true);

    }

    @FXML
    private void clicHistorial() {
        ocultarSeleccion();
        buttonHistorial.setStyle(COLOR_TEXTO_RESALTADO);

    }

    @FXML
    private void clicAlbumes() {
        ocultarSeleccion();
        buttonAlbumes.setStyle(COLOR_TEXTO_RESALTADO);
        ocultarContenidos();
        if (biblioteca) {
            cargarTablaAlbumes(albumes);
            paneAlbumes.setVisible(true);
        } else {
//         //ir por Artistas sin repeticon (canciones) del servidor
//         cargarTablaArtistas(canciones);
//         paneArtistas.setVisible(true);
        }
    }

    @FXML
    private void clicGeneros() {
        ocultarSeleccion();
        buttonGeneros.setStyle(COLOR_TEXTO_RESALTADO);
        if (albumes != null) {

        }
        Servidor.enviarMensaje(new Mensaje("recuperarAlbumes"));
        paneAlbumes.setVisible(true);

    }

    @FXML
    private void clicSubirLocal() {
        ocultarSeleccion();
        buttonAgregarLocal.setStyle(COLOR_TEXTO_RESALTADO);
        ocultarContenidos();
        paneAgregarLocal.setVisible(true);

    }

    @FXML
    private void clicListas() {
        ocultarSeleccion();
        buttonListas.setStyle(COLOR_TEXTO_RESALTADO);
    }

    @FXML
    private void clicSubirContenido() {
        ocultarSeleccion();
        buttonContenido.setStyle(COLOR_TEXTO_RESALTADO);
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

    public void agregarListenersTablaAlbumes() {
        tableAlbumes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("listener: " + newSelection.getNombre());
                labelPaneAlbumNombre.setText(newSelection.getNombre());
                List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(newSelection.getIdAlbum());
                for (int i = 0; i < cancionesSeleccionadas.size(); i++) {
                    System.out.println(cancionesSeleccionadas.get(i).getNombre());
                }
                cargarTablaCancionesAlbum(cancionesSeleccionadas);
            }
        });
    }

    public List<Cancion> recuperarCancionesPorAlbum(int idAlbum) {
        List<Cancion> cancionesAlbum = new ArrayList<>();
        for (int i = 0; i < canciones.size(); i++) {
            if (canciones.get(i).getAlbum_idAlbum() == idAlbum) {
                cancionesAlbum.add(canciones.get(i));
            }
        }
        return cancionesAlbum;
    }

    private void cargarTablaCancionesAlbum(List<Cancion> cancionesAlbum) {
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(cancionesAlbum
        );
        tcaColumnNombreAlbum.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableCancionesAlbum.setItems(obsCanciones);
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

    private void cargarTablaAlbumes(List<Album> albumes) {
        ObservableList<Album> obsAlbumes = FXCollections.observableArrayList(albumes
        );
        talColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableAlbumes.setItems(obsAlbumes);
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
            case "albumesServidor":
                albumes = (List<Album>) mensaje.getObjeto();
                System.out.println("albumes Rec " + albumes.get(0).getNombre());
                System.out.println("albumes Rec " + albumes.get(0).getIdAlbum());
                break;

        }

    }

}
