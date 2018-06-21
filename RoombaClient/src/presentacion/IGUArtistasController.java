
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
import logica.Cancion;

/**
 * Clase del controlador para mostrar las canciones por artista
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class IGUArtistasController implements Initializable {

    @FXML
    private Pane paneArtistas;
    @FXML
    private TableView<Cancion> tableArtistas;
    @FXML
    private TableColumn<?, ?> taColumnArtista;
    @FXML
    private Label labelPaneArtistaNombre;
    @FXML
    private TableView<Cancion> tableCancionesArtista;
    @FXML
    private TableColumn<?, ?> tcaColumnNombre;
    private IGUBarraReproduccionController controladorBarraReproduccion;

   /**
    * Método para asignar el controlador de la barra de reproducción
    * @param controladorBarraReproduccion IGUBarraReproduccionController inicializado en clase ajena
    */
   public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }

   /**
    * Método par asignar el estatus del pane del controller
    * @param estatus Boolean del estatus
    */
   public void setVisibilidad(boolean estatus) {
        paneArtistas.setVisible(estatus);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Agrega los listener de los eventos de la tabla de artistas.
     */
    public void agregarListenersTablaArtistas(List<Cancion> canciones) {
        tableArtistas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelPaneArtistaNombre.setText(newSelection.getArtista());
                List<Cancion> cancionesSeleccionadas = seleccionarCancionesArtista(newSelection.getArtista(), canciones);
                cargarTablaCancionesArtista(cancionesSeleccionadas);
            }
        });
    }

   /**
    * Método para cargar la tabla de artistas.
    * @param canciones Lista de Cancion que se encuentran vinculadas a un artísta
    */
   public void cargarTablaArtistas(List<Cancion> canciones) {
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones);
        taColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
        tableArtistas.setItems(obsCanciones);
        agregarListenersTablaArtistas(canciones);
    }

   private void cargarTablaCancionesArtista(List<Cancion> cancionesArtista) {
        //Ir o recargar las canciones del artista
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(cancionesArtista);
        tcaColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableCancionesArtista.setItems(obsCanciones);
        agregarListenersTablaCanciones();
    }
    
   /**
    * Método para agregar los listener de la tabla de canciones.
    */
   public void agregarListenersTablaCanciones() {
        tableCancionesArtista.setRowFactory(
                new Callback<TableView<Cancion>, TableRow<Cancion>>() {
            @Override
            public TableRow<Cancion> call(TableView<Cancion> tableView) {
                final TableRow<Cancion> row = new TableRow<>();

                //SELECCIÓN DE UNA CANCIÓN
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Cancion cancion = row.getItem();
                        controladorBarraReproduccion.recuperarCancionYReproducir(cancion, false);
                        controladorBarraReproduccion.cargarBarraReproduccion(cancion);
                    }
                });
                return row;
            }
        });
    }

   /**
    * Método para seleccionar las canciones de acuerdo a un artísta
    * @param artista Nombre del artista a buscar coincidencias
    * @param canciones Lista de Cancion
    * @return Lista de Cancion que cumplen con la coincidencia del artista
    */
   public List<Cancion> seleccionarCancionesArtista(String artista, List<Cancion> canciones) {
        labelPaneArtistaNombre.setText(artista);
        List<Cancion> cancionesSeleccionadas = new ArrayList<>();
        for (int i = 0; i < canciones.size(); i++) {
            if (canciones.get(i).getArtista().equals(artista)) {
                cancionesSeleccionadas.add(canciones.get(i));
            }
        }
        return cancionesSeleccionadas;
    }

   /**
    * Método para cargar el controller de la clase.
    * @return Pane con la asignación del controller
    */
   public Pane abrirIGUArtistas() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUArtistas.fxml"));
            fxmlLoader.setController(this);
            paneArtistas = fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneArtistas;
    }
}
