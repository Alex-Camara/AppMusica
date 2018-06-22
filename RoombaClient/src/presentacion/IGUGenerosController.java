package presentacion;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logica.Album;
import logica.Cancion;
import logica.Genero;

/**
 * Clase del controlador para mostrar los géneros de las canciones.
 *
 * @author José Valdivia
 * @author Alex Cámara
 *
 */
public class IGUGenerosController implements Initializable {

    @FXML
    private Pane paneGeneros;
    @FXML
    private TableView<Genero> tableGeneros;
    @FXML
    private TableColumn<?, ?> tgColumnGenero;
    @FXML
    private Label labelPaneGeneroNombre;
    @FXML
    private TableView<Cancion> tableCancionesGenero;
    @FXML
    private TableColumn<?, ?> tcgColumnNombre;
    private IGUBarraReproduccionController controladorBarraReproduccion;

    /**
     * Método para asignar el controlado de la barra de reproducción.
     *
     * @param controladorBarraReproduccion IGUBarraReproduccionController creado
     * para asignar
     */
    public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }

    /**
     * Método par asignar el estatus del pane del controller
     *
     * @param estatus Boolean del estatus
     */
    public void setVisibilidad(boolean estatus) {
        paneGeneros.setVisible(estatus);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Método para agregar los listener de la tabla de géneros
     *
     * @param canciones Lista de Cancion para recuperar géneros
     * @param albumes Lista de Album de las canciones
     */
    public void agregarListenersTablaGeneros(List<Cancion> canciones, List<Album> albumes) {
        tableGeneros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelPaneGeneroNombre.setText(newSelection.getNombre());
                List<Album> albumesDeGenero = recuperarAlbumesDeGenero(newSelection.getIdGenero(), albumes);
                List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(albumesDeGenero, canciones);
                cargarTablaCancionesGenero(cancionesSeleccionadas);
            }
        });
    }

    /**
     * Método para cargar la tabla de géneros con sus elementos
     *
     * @param generos lista de Genero de las canciones
     * @param albumes lista de Album de las canciones
     * @param canciones lista de Cancion
     */
    public void cargarTablaGeneros(List<Genero> generos, List<Album> albumes, List<Cancion> canciones) {
        if (!generos.isEmpty()) {
            labelPaneGeneroNombre.setText(generos.get(0).getNombre());
            ObservableList<Genero> obsGeneros = FXCollections.observableArrayList(generos);
            tgColumnGenero.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            tableGeneros.setItems(obsGeneros);
            agregarListenersTablaGeneros(canciones, albumes);
        }

    }

    private void cargarTablaCancionesGenero(List<Cancion> cancionesAlbum) {
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(cancionesAlbum);
        tcgColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableCancionesGenero.setItems(obsCanciones);
        agregarListenersTablaCanciones();
    }

    /**
     * Método para agregar los listener de la tabla de las canciones.
     */
    public void agregarListenersTablaCanciones() {
        tableCancionesGenero.setRowFactory(
                new Callback<TableView<Cancion>, TableRow<Cancion>>() {
            @Override
            public TableRow<Cancion> call(TableView<Cancion> tableView) {
                final TableRow<Cancion> row = new TableRow<>();

                //SELECCIÓN DE UNA CANCIÓN
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Cancion cancion = row.getItem();
                        controladorBarraReproduccion.recuperarCancionYReproducir(cancion);
                        controladorBarraReproduccion.cargarBarraReproduccion(cancion);
                    }
                });
                return row;
            }
        });
    }

    /**
     * Método para recuperar los álbumes que se encuentran en un género.
     *
     * @param generoSeleccionado Genero seleccionado
     * @param albumes Lista de Album
     * @return Lista de Albumes que se encuentran en el genero recibido
     */
    public List<Album> recuperarAlbumesDeGenero(int generoSeleccionado, List<Album> albumes) {
        List<Album> albumesDeGenero = new ArrayList<>();
        for (int i = 0; i < albumes.size(); i++) {
            if (albumes.get(i).getIdGenero() == generoSeleccionado) {
                albumesDeGenero.add(albumes.get(i));
            }
        }
        return albumesDeGenero;
    }

    /**
     * Método para recuperas las canciones que se encuentran en un Àlbum
     *
     * @param albumesDeGenero Lista de Album de un Genero
     * @param canciones Lista de Cancion
     * @return Lista de Cancion que se encuentran en dicho Album
     */
    public List<Cancion> recuperarCancionesPorAlbum(List<Album> albumesDeGenero, List<Cancion> canciones) {
        List<Cancion> cancionesAlbum = new ArrayList<>();
        for (int i = 0; i < albumesDeGenero.size(); i++) {
            for (int j = 0; j < canciones.size(); j++) {
                if (canciones.get(j).getAlbum_idAlbum() == albumesDeGenero.get(i).getIdAlbum()) {
                    cancionesAlbum.add(canciones.get(j));
                }
            }
        }
        return cancionesAlbum;
    }

    /**
     * Método para abrir la ventana de la clase.
     *
     * @return Pane de la clase
     */
    public Pane abrirIGUGeneros() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUGeneros.fxml"));

            fxmlLoader.setController(this);
            paneGeneros = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneGeneros;
    }
}
