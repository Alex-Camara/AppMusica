
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

/**
 * Clase del controlador para mostrar las canciones por álbumes
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class IGUAlbumesController implements Initializable {

    @FXML
    private Pane paneAlbumes;
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
    private List<Cancion> canciones;
    private List<Album> albumes;
    private IGUBarraReproduccionController controladorBarraReproduccion;

   /**
    * Método apra asignar el controlador de la barra de reproducción
    * @param controladorBarraReproduccion IGUBarraReproduccionController inicializado
    */
   public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }

   /**
    * Método para recuperar la lista de Canciones.
    * @return Lista de Cancion
    */
   public List<Cancion> getCanciones() {
        return canciones;
    }

   /**
    * Método para asignar la lista de canciones
    * @param canciones lista de Cancion
    */
   public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

   /**
    * Método para recuperar los álbumes
    * @return lista de Álbum
    */
   public List<Album> getAlbumes() {
        return albumes;
    }

   /**
    * Método para asignar la lista de Album
    * @param albumes
    */
   public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }
    /**
    * Método par asignar el estatus del pane del controller
    * @param estatus Boolean del estatus
    */
    public void setVisibilidad(boolean estatus) {
        paneAlbumes.setVisible(estatus);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

   /**
    * Método para agregar los listener a eventos en la tabla de albumes
    * @param canciones lista de Cancion
    */
   public void agregarListenersTablaAlbumes(List<Cancion> canciones) {
        tableAlbumes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelPaneAlbumNombre.setText(newSelection.getNombre());
                List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(newSelection.getIdAlbum(), canciones);
                cargarTablaCancionesAlbum(cancionesSeleccionadas);
            }
        });
    }

   /**
    * Método para recuperar las canciones por álbum
    * @param idAlbum int del identificador del Album
    * @param canciones lista de Cancion
    * @return lista de Cancion que coindicen con el Album
    */
   public List<Cancion> recuperarCancionesPorAlbum(int idAlbum, List<Cancion> canciones) {
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
        agregarListenersTablaCanciones();
    }

   /**
    * Método para agregar los listeners a los eventos de la tabla de canciones
    */
   public void agregarListenersTablaCanciones() {
        tableCancionesAlbum.setRowFactory(
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
    * Métod para cargar la tabla de albumes
    * @param albumes Lista de album
    * @param canciones Lista de Cancion que se encuentran en albumes
    */
   public void cargarTablaAlbumes(List<Album> albumes, List<Cancion> canciones) {
        labelPaneAlbumNombre.setText(albumes.get(0).getNombre());
        ObservableList<Album> obsAlbumes = FXCollections.observableArrayList(albumes);
        talColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableAlbumes.setItems(obsAlbumes);
        agregarListenersTablaAlbumes(canciones);
    }

   /**
    * Método para recuperar la ventana y asignar el controller
    * @return Pane con el Controller asignado
    */
   public Pane abrirIGUAlbumes() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUAlbumes.fxml"));

            fxmlLoader.setController(this);
            paneAlbumes = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneAlbumes;
    }

}
