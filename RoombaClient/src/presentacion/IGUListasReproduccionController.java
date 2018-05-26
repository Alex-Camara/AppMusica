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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logica.Cancion;
import logica.ListaReproduccion;
import presentacion.Utileria.MenuContextualListas;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
 */
public class IGUListasReproduccionController implements Initializable {

    @FXML
    private Pane paneListas;
    @FXML
    private TableView<Cancion> tableCanciones;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcArtista;
    @FXML
    private TableColumn<?, ?> tcCancion;
    @FXML
    private TableView<ListaReproduccion> tableListas;
    @FXML
    private TableColumn<ListaReproduccion, String> tcNombreLista;
    @FXML
    private TableColumn<Cancion, String> tcOpciones;

    public void setVisibilidad(boolean estatus) {
        paneListas.setVisible(estatus);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarTablaListas(List<ListaReproduccion> listasReproduccion, IGUBarraReproduccionController controladorBarraReproduccion) {

        ObservableList<ListaReproduccion> obsListasReproduccion = FXCollections.observableArrayList(listasReproduccion
        );

        tcNombreLista.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tcOpciones.setCellValueFactory(new PropertyValueFactory<>("op"));

        Callback<TableColumn<Cancion, String>, TableCell<Cancion, String>> cellFactory = new Callback<TableColumn<Cancion, String>, TableCell<Cancion, String>>() {
            @Override
            public TableCell call(TableColumn param) {
                return new MenuContextualListas();
                }
            
        };
        tcOpciones.setCellFactory(cellFactory);

        tableListas.setItems(obsListasReproduccion);
        //agregarListenersTablaAlbumes(canciones, controladorBarraReproduccion);
    }

    public Pane abrirIGUListasReproduccion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUListasReproduccion.fxml"));

            fxmlLoader.setController(this);
            paneListas = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneListas;
    }
}
