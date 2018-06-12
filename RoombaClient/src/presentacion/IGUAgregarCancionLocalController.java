/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import logica.Album;
import logica.Cancion;
import logica.Genero;
import presentacion.Utileria.Emergente;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
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
    private JFXButton buttonAgregarArchivo;
    @FXML
    private JFXComboBox<Genero> comboGenero;
    @FXML
    private ListView<Cancion> listArchivos;
    @FXML
    private JFXButton buttonAgregarListArchivos;
    @FXML
    private Label labelPaneAgregarCancionNombre;
    Cancion CancionASubir = new Cancion();
    FileChooser fileChooser;
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
            
            cancion.setNombre(tFieldNombreArchivo.getText());
            cancion.setArtista(tFieldArtistaArchivo.getText());
            album.setNombre(tFieldAlbumArchivo.getText());
            album.setIdGenero(comboGenero.getSelectionModel().getSelectedItem().getIdGenero());
            cancion.setAlbum(album);
            hashmapCanciones.put(cancion, file);
            
            listaArchivos.add(cancion);
            llenarLista();
            limpiarCampos();
        } else {
            Emergente.cargarEmergente("Advertencia", "Debes llenar todos los campos");
        }

    }

    private void llenarLista() {
        ObservableList<Cancion> obsArchivos = FXCollections.observableArrayList(listaArchivos);
        listArchivos.setItems(obsArchivos);
    }
    
    private void limpiarCampos(){
        tFieldNombreArchivo.setText("");
        tFieldAlbumArchivo.setText("");
        tFieldArtistaArchivo.setText("");
        comboGenero.getSelectionModel().clearSelection();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void cargarComboGeneros(List<Genero> generos) {
        ObservableList<Genero> obsGeneros = FXCollections.observableArrayList(generos);
        comboGenero.setItems(obsGeneros);
    }

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

}
