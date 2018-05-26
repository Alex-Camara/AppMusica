/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logica.Cancion;
import presentacion.Utileria.MenuContextual;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
 */
public class IGUCancionesController implements Initializable {

    @FXML
    private Pane paneCanciones;
    @FXML
    private TableView<Cancion> tableCanciones;
    @FXML
    private TableColumn<?, ?> tcColumnNombre;
    @FXML
    private TableColumn<?, ?> tcColumnArtista;
    @FXML
    private TableColumn<?, ?> tcColumnCalificacion;
    @FXML
    private TableColumn<Cancion, String> tcOpciones;

    public void setVisibilidad(boolean estatus) {
        paneCanciones.setVisible(estatus);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarTablaCanciones(List<Cancion> canciones, IGUBarraReproduccionController controladorBarraReproduccion) {
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones
        );
        tcColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tcColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
        tcColumnCalificacion.setCellValueFactory(new PropertyValueFactory<>("Calificacion"));
        tcOpciones.setCellValueFactory(new PropertyValueFactory<>("op"));

        Callback<TableColumn<Cancion, String>, TableCell<Cancion, String>> cellFactory = new Callback<TableColumn<Cancion, String>, TableCell<Cancion, String>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new MenuContextual();
                }
            
        };
        tcOpciones.setCellFactory(cellFactory);

        tableCanciones.setItems(obsCanciones);
        agregarListenersTablaAlbumes(canciones, controladorBarraReproduccion);
    }

    public void agregarListenersTablaAlbumes(List<Cancion> canciones, IGUBarraReproduccionController controladorBarraReproduccion) {
        tableCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                controladorBarraReproduccion.setCancion(newSelection);
                controladorBarraReproduccion.cargarBarraReproduccion();
            }
        });
    }

    public Pane abrirIGUCanciones() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUCanciones.fxml"));

            fxmlLoader.setController(this);
            paneCanciones = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneCanciones;
    }
}
