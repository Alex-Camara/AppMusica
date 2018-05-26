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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logica.Cancion;
import logica.conexion.Mensaje;
import logica.conexion.Servidor;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
 */
public class IGUBarraReproduccionController implements Initializable {

    @FXML
    private Pane paneBarraReproduccion;
    @FXML
    private Label labelTituloCancion;
    @FXML
    private Button buttonConfiguracion;
    @FXML
    private ImageView imageEngrane;
    @FXML
    private Button buttonSiguiente;
    @FXML
    private ImageView imageSiguiente;
    @FXML
    private Button buttonAnterior;
    @FXML
    private ImageView imageAnterior;
    @FXML
    private Button buttonReproducir;
    @FXML
    private ImageView imageReproducir;
    @FXML
    private Label labelCalificacion;
    @FXML
    private ImageView imageEstrella1;
    @FXML
    private ImageView imageEstrella2;
    @FXML
    private ImageView imageEstrella3;
    @FXML
    private ImageView imageEstrella4;
    @FXML
    private ImageView imageEstrella5;
    @FXML
    private Label labelArtista;
    @FXML
    private Label labelTituloArtista;

    List<ImageView> estrellas = new ArrayList<>();
    private Cancion cancion;

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    @FXML
    void resaltarEstrella(MouseEvent event) {
        ImageView imagen = (ImageView) (event.getSource());
        imagen.setImage(new Image("/presentacion/recursos/iconos/estrellaC.png"));
    }

    @FXML
    void resetEstrella(MouseEvent event) {
        ImageView imagen = (ImageView) (event.getSource());
        imagen.setImage(new Image("/presentacion/recursos/iconos/estrella.png"));
    }

    @FXML
    void resaltarAnterior(MouseEvent event) {
        imageAnterior.setImage(new Image("/presentacion/recursos/iconos/rewindSeleccionado.png"));
    }

    @FXML
    void resaltarEngrane(MouseEvent event) {
        imageEngrane.setImage(new Image("/presentacion/recursos/iconos/settingsSeleccionado.png"));
    }

    @FXML
    void resaltarReproducir(MouseEvent event) {
        imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducirSeleccionado.png"));
    }

    @FXML
    void resaltarSiguiente(MouseEvent event) {
        imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forwardSeleccionado.png"));
    }

    @FXML
    void resetAnterior(MouseEvent event) {
        imageAnterior.setImage(new Image("/presentacion/recursos/iconos/rewind.png"));
    }

    @FXML
    void resetEngrane(MouseEvent event) {
        imageEngrane.setImage(new Image("/presentacion/recursos/iconos/settings.png"));
    }

    @FXML
    void resetReprouducir(MouseEvent event) {
        imageReproducir.setImage(new Image("/presentacion/recursos/iconos/reproducir.png"));
    }

    @FXML
    void resetSiguiente(MouseEvent event) {
        imageSiguiente.setImage(new Image("/presentacion/recursos/iconos/forward.png"));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estrellas.add(imageEstrella1);
        estrellas.add(imageEstrella2);
        estrellas.add(imageEstrella3);
        estrellas.add(imageEstrella4);
        estrellas.add(imageEstrella5);
    }

    public void cargarBarraReproduccion() {
        labelTituloCancion.setText(cancion.getNombre());
        labelTituloArtista.setText(cancion.getArtista());

        labelTituloCancion.setVisible(true);
        labelCalificacion.setVisible(true);
        labelArtista.setVisible(true);
        labelTituloArtista.setVisible(true);

        imageEstrella1.setVisible(true);
        imageEstrella2.setVisible(true);
        imageEstrella3.setVisible(true);
        imageEstrella4.setVisible(true);
        imageEstrella5.setVisible(true);

        iluminarEstrellas(cancion.getCalificacion());
    }

    @FXML
    void calificarCancion(MouseEvent event) {
        ImageView imagen = (ImageView) (event.getSource());
        String idImagen = imagen.getId();
        String stringCalificacion = idImagen.substring(idImagen.length() - 1);
        int calificacion = Integer.valueOf(stringCalificacion);
        System.out.println("calificacion: " + calificacion);
        cancion.setCalificacion(calificacion);
        iluminarEstrellas(calificacion);
        
        Mensaje mensajeEnviar = new Mensaje("actualizarCalificación");
        mensajeEnviar.setObjeto(cancion);
        Servidor.enviarMensaje(mensajeEnviar);
    }

    private void iluminarEstrellas(int calificacion) {
        for (int i = 0; i < estrellas.size(); i++) {
            if (i < calificacion) {
                estrellas.get(i).setImage(new Image("/presentacion/recursos/iconos/estrellaC.png"));
            } else {
                estrellas.get(i).setImage(new Image("/presentacion/recursos/iconos/estrella.png"));
            }

        }
    }

    @FXML
    void clicConfigurar(ActionEvent event) {

    }

    public Pane abrirIGUBarraReproduccion() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBarraReproduccion.fxml"));

            fxmlLoader.setController(this);
            paneBarraReproduccion = fxmlLoader.load();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paneBarraReproduccion;
    }
}
