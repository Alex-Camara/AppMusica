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
import logica.Genero;

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
    private ListView<?> listArchivos;
    @FXML
    private JFXButton buttonAgregarListArchivos;
    @FXML
    private Label labelPaneAgregarCancionNombre;

    public void setVisibilidad(boolean estatus) {
        paneAgregarLocal.setVisible(estatus);
    }
    
    @FXML
    void ClicSeleccionarArchivoLocal(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Música para añadir a biblioteca");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
        File file = fileChooser.showOpenDialog(buttonSeleccionarArchivo.getScene().getWindow());
        labelPaneAgregarCancionNombre.setText(file.getName());

    }

    @FXML
    void clicAgregarArchivoLocal(ActionEvent event) {

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
