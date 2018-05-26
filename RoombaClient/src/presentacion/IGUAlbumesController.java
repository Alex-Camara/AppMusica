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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import logica.Album;
import logica.Cancion;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
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

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }
    
    public void setVisibilidad(boolean estatus){
        paneAlbumes.setVisible(estatus);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void agregarListenersTablaAlbumes(List<Cancion> canciones) {
        tableAlbumes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                labelPaneAlbumNombre.setText(newSelection.getNombre());
                List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(newSelection.getIdAlbum(), canciones);
                cargarTablaCancionesAlbum(cancionesSeleccionadas);
            }
        });
    }
    
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
    }
     
     public void cargarTablaAlbumes(List<Album> albumes, List<Cancion> canciones) {
        labelPaneAlbumNombre.setText(albumes.get(0).getNombre());
        ObservableList<Album> obsAlbumes = FXCollections.observableArrayList(albumes);
        talColumnAlbum.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableAlbumes.setItems(obsAlbumes);
        agregarListenersTablaAlbumes(canciones);
        //List<Cancion> cancionesSeleccionadas = recuperarCancionesPorAlbum(albumes.get(0).getIdAlbum(), canciones);
        //cargarTablaCancionesAlbum(cancionesSeleccionadas);
    }

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
