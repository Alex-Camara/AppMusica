/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * FXML Controller class
 *
 * @author Alex Cámara
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

    public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }

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
     * Agrega los eventos ante los cuales respondera la tabla.
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

    public void cargarTablaArtistas(List<Cancion> canciones) {
        System.out.println("lista canciones: " + canciones);
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
                        controladorBarraReproduccion.setCancion(cancion);
                        controladorBarraReproduccion.cargarBarraReproduccion();
                    }
                });
                return row;
            }
        });
    }

    private List<Cancion> seleccionarCancionesArtista(String artista, List<Cancion> canciones) {
        labelPaneArtistaNombre.setText(artista);
        List<Cancion> cancionesSeleccionadas = new ArrayList<>();
        for (int i = 0; i < canciones.size(); i++) {
            if (canciones.get(i).getArtista().equals(artista)) {
                cancionesSeleccionadas.add(canciones.get(i));
            }
        }
        return cancionesSeleccionadas;
    }

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
