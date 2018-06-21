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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
public class IGURadioController implements Initializable {

    @FXML
    private Pane paneCanciones;
    @FXML
    private TableView<Cancion> tableCanciones;
    @FXML
    private TableColumn<?, ?> tcColumnNombre;
    @FXML
    private TableColumn<?, ?> tcColumnArtista;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void cargarTablaCanciones(List<Cancion> canciones, IGUBarraReproduccionController controladorBarraReproduccion) {
        tableCanciones.refresh();
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones);

        tcColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tcColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));

        tableCanciones.setItems(obsCanciones);
        agregarListenersTablaCanciones(controladorBarraReproduccion);
    }

    public void agregarListenersTablaCanciones(IGUBarraReproduccionController controladorBarraReproduccion) {
        tableCanciones.setRowFactory(
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
    
    public Pane abrirIGURadio() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGURadio.fxml"));
            fxmlLoader.setController(this);
            paneCanciones = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneCanciones;
    }
}
