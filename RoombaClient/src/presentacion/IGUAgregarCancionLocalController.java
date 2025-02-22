package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import logica.Album;
import logica.Cancion;
import logica.Genero;
import logica.Usuario;
import logica.conexion.Cliente;
import logica.conexion.ClienteFormatos;
import logica.conexion.Mensaje;
import presentacion.Utileria.Emergente;

/**
 * Clase del controlador para mostrar las canciones por álbumes
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class IGUAgregarCancionLocalController implements Initializable {

    @FXML
    private Pane paneAgregarLocal;
    @FXML
    private JFXButton buttonSeleccionarArchivo;
    @FXML
    private JFXTextField tFieldArtistaArchivo;
    @FXML
    private JFXTextField tFieldNombreArchivo;
    @FXML
    private JFXTextField tFieldAlbumArchivo;
    @FXML
    private JFXComboBox<Genero> comboGenero;
    @FXML
    private ListView<Cancion> listArchivos;
    @FXML
    private Label labelPaneAgregarCancionNombre;
    private Usuario artista;
    private boolean subirDesdeCreador = false;
    Cancion CancionASubir = new Cancion();
    FileChooser fileChooser;
    boolean esCreador;
    File file;
    List<Cancion> listaArchivos = new ArrayList<>();
    HashMap<Cancion, File> hashmapCanciones = new HashMap<>();

    @FXML
    void ClicSeleccionarArchivoLocal(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Música para añadir a biblioteca");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
        file = fileChooser.showOpenDialog(buttonSeleccionarArchivo.getScene().getWindow());
        if (file != null) {
            labelPaneAgregarCancionNombre.setText(file.getName());
        }
    }

    @FXML
    void clicAgregarArchivoLocal(ActionEvent event) {
        if (!tFieldNombreArchivo.getText().trim().isEmpty() && !tFieldArtistaArchivo.getText().trim().isEmpty()
                && !tFieldAlbumArchivo.getText().trim().isEmpty() && !comboGenero.getSelectionModel().isEmpty()
                && file != null) {

            Cancion cancion = new Cancion();
            Album album = new Album();

            cancion.setNombre(tFieldNombreArchivo.getText().trim());
            cancion.setArtista(tFieldArtistaArchivo.getText().trim());
            album.setNombre(tFieldAlbumArchivo.getText().trim());
            album.setIdGenero(comboGenero.getSelectionModel().getSelectedItem().getIdGenero());
            cancion.setAlbum(album);
            String directorioAGuardar = cancion.getArtista()
                    + "/" + cancion.getAlbum() + "/" + cancion.getNombre();
            cancion.setRuta(directorioAGuardar);
            hashmapCanciones.put(cancion, file);

            listaArchivos.add(cancion);
            llenarLista();
            limpiarCampos();
        } else {
            Emergente.cargarEmergente("Advertencia", "Ingresa todos los datos");
        }
    }

    private void llenarLista() {
        ObservableList<Cancion> obsArchivos = FXCollections.observableArrayList(listaArchivos);
        listArchivos.setItems(obsArchivos);
    }

    private void limpiarCampos() {
        if (!subirDesdeCreador) {
            tFieldArtistaArchivo.setText("");
            tFieldArtistaArchivo.setDisable(false);
        }
        tFieldNombreArchivo.setText("");
        tFieldAlbumArchivo.setText("");
        labelPaneAgregarCancionNombre.setText("");
        comboGenero.getSelectionModel().clearSelection();
    }

    @FXML
    void agregarCanciones(ActionEvent event) {
        if (!listaArchivos.isEmpty()) {
            Thread recibir = new Thread() {
                public void run() {
                    try {
                        for (int i = 0; i < listaArchivos.size(); i++) {
                            ClienteFormatos clienteFormatos = new ClienteFormatos();
                            clienteFormatos.abrirConexion();
                            System.out.println("abrir conexión");
                            Cancion cancion = listaArchivos.get(i);
                            File archivo = hashmapCanciones.get(cancion);
                            clienteFormatos.enviarArchivo(cancion, archivo);
                            clienteFormatos.cerrarConexion();
                        }

                    } catch (IOException ex) {
                        Platform.runLater(() -> {
                            Emergente.cargarEmergente("Aviso", "Hubo un error al subir la canción");
                        });

                        Logger.getLogger(IGUAgregarCancionLocalController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {

                    }

                }
            };
            recibir.start();
            try {
                recibir.join();
                guardarCanciones();
                Platform.runLater(() -> {

                    Emergente.cargarEmergente("Aviso", "Canciones subidas");
                    resetPane(esCreador);
                    listaArchivos.clear();
                    hashmapCanciones.clear();
                    listArchivos.getItems().clear();
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(IGUAgregarCancionLocalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Emergente.cargarEmergente("Advertencia", "Sin canciones que agregar");
        }
    }

    /**
     * Método para agregar las canciones seleccionadas al servidor
     */
    public void guardarCanciones() {
        System.out.println("se guardo una vez");
        System.out.println("tipo de usuario: " + artista);
        if (artista != null) {
            //System.out.println("entro al 1");
            List<Cancion> listaAEnviar = new ArrayList<>();
            for (int i = 0; i < listaArchivos.size(); i++) {
                listaAEnviar.add(listaArchivos.get(i));
            }

            Mensaje mensaje = new Mensaje("guardarCancionesCreador");
            System.out.println("canciones a enviar: " + listaArchivos);
            mensaje.setObjeto(listaAEnviar);
            Cliente.enviarMensaje(mensaje);

            System.out.println("lista de archivos resultantes: " + listaArchivos);
        } else {
            //System.out.println("entro al 2");
            List<Cancion> listaAEnviar = new ArrayList<>();
            for (int i = 0; i < listaArchivos.size(); i++) {
                listaAEnviar.add(listaArchivos.get(i));
            }
            Mensaje mensaje = new Mensaje("guardarCanciones");
            System.out.println("canciones a enviar: " + listaArchivos);
            mensaje.setObjeto(listaAEnviar);
            Cliente.enviarMensaje(mensaje);

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarListenerTabla();
    }

    /**
     * Método para cargar el combo de géneros con los elementos recuperados de
     * la base de datos
     *
     * @param generos Lista de Genero recuperados
     */
    public void cargarComboGeneros(List<Genero> generos) {
        ObservableList<Genero> obsGeneros = FXCollections.observableArrayList(generos);
        comboGenero.setItems(obsGeneros);
    }

    /**
     * Método para abrir el FXML y asignar el controller
     *
     * @return Pane con las asignaciones
     */
    public Pane abrirIGUAgregarCancionLocal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUAgregarCancionLocal.fxml"));
            fxmlLoader.setController(this);
            paneAgregarLocal = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneAgregarLocal;
    }

    void setArtista(Usuario usuario) {
        artista = usuario;
        tFieldArtistaArchivo.setText(artista.getNombreArtistico());
        tFieldArtistaArchivo.setDisable(true);
    }

    void resetPane(boolean desdeCreador) {
        this.esCreador = desdeCreador;
        if (desdeCreador != subirDesdeCreador) {
            subirDesdeCreador = desdeCreador;
            limpiarCampos();
            listaArchivos.clear();
            hashmapCanciones.clear();
            listArchivos.getItems().clear();
        }
    }

    private void agregarListenerTabla() {
        listArchivos.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                if (listArchivos.getSelectionModel().getSelectedItem() != null) {
                    Cancion seleccionada = listArchivos.getSelectionModel().getSelectedItem();
                    Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                    String btnNomCancelar = "Cancelar";
                    String btnNomEliminar = "Eliminar";
                    confirmacion.setTitle("Eliminar");
                    confirmacion.setHeaderText("¿Estás seguro de eliminar este archivo de la lista?");
                    ButtonType btnEliminar = new ButtonType(btnNomEliminar);
                    ButtonType btnCancelar = new ButtonType(btnNomCancelar, ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmacion.getButtonTypes().setAll(btnEliminar, btnCancelar);
                    Optional<ButtonType> eleccion = confirmacion.showAndWait();
                    if (eleccion.get() == btnEliminar) {
                        eliminarCancionLista(seleccionada);
                    }
                }
            }
        });
    }

    private void eliminarCancionLista(Cancion seleccionada) {
        listaArchivos.remove(seleccionada);
        hashmapCanciones.remove(seleccionada);
        listArchivos.getItems().remove(seleccionada);
    }
}
