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
import logica.Album;
import logica.Cancion;
import logica.Genero;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
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

    public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }
    
    public void setVisibilidad(boolean estatus) {
        paneGeneros.setVisible(estatus);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
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
    
    public void cargarTablaGeneros(List<Genero> generos, List<Album> albumes, List<Cancion> canciones) {
        labelPaneGeneroNombre.setText(generos.get(0).getNombre());
        ObservableList<Genero> obsGeneros = FXCollections.observableArrayList(generos);
        tgColumnGenero.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableGeneros.setItems(obsGeneros);
        agregarListenersTablaGeneros(canciones, albumes);
        //List<Album> albumesDeGenero = recuperarAlbumesDeGenero(albumes.get(0).getIdGenero(), albumes);
        //List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(albumesDeGenero, canciones);
        //cargarTablaCancionesGenero(cancionesSeleccionadas);
    }

    private void cargarTablaCancionesGenero(List<Cancion> cancionesAlbum) {
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(cancionesAlbum);
        tcgColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableCancionesGenero.setItems(obsCanciones);
        agregarListenersTablaCanciones();
    }
    
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
                        controladorBarraReproduccion.recuperarCancionYReproducir(cancion, false);
                        controladorBarraReproduccion.cargarBarraReproduccion(cancion);
                    }
                });
                return row;
            }
        });
    }
    
    private List<Album> recuperarAlbumesDeGenero(int generoSeleccionado, List<Album> albumes) {
        List<Album> albumesDeGenero = new ArrayList<>();
        for (int i = 0; i < albumes.size(); i++) {
            if (albumes.get(i).getIdGenero() == generoSeleccionado) {
                albumesDeGenero.add(albumes.get(i));
            }
        }
        return albumesDeGenero;
    }
    
    private List<Cancion> recuperarCancionesPorAlbum(List<Album> albumesDeGenero, List<Cancion> canciones) {
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
