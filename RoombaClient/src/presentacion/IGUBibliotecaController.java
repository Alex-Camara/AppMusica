package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Genero;
import logica.ListaReproduccion;
import logica.Usuario;
import logica.conexion.Mensaje;
import logica.conexion.Cliente;
import logica.conexion.ClienteStreaming;
import org.apache.commons.io.FileUtils;
import presentacion.Utileria.Emergente;

/**
 * Clase del controlador principal administra la visibilidad de los pane que
 * alberga y de la función principal.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class IGUBibliotecaController implements Initializable {

    @FXML
    private BorderPane borderPanePrincipal;
    @FXML
    private Pane paneBarraReproduccion;
    @FXML
    private JFXButton buttonBiblioteca;
    @FXML
    private JFXButton buttonExplorar;
    @FXML
    private JFXButton buttonHistorial;
    @FXML
    private JFXButton buttonCanciones;
    @FXML
    private JFXButton buttonAlbumes;
    @FXML
    private JFXButton buttonArtistas;
    @FXML
    private JFXButton buttonGeneros;
    @FXML
    private JFXButton buttonAgregarLocal;
    @FXML
    private JFXButton buttonListas;
    @FXML
    private JFXButton buttonContenido;
    @FXML
    private JFXTextField tFieldBuscar;
    @FXML
    private ImageView imageSalir;
    @FXML
    private ImageView imageCancelarBusqueda;

    private static final String RESALTADO = "-fx-background-color:#FFD7B4;";
    private static final String COLOR_TOGGLEBUTTON_NORMAL = "-fx-background-color: #F0EFF7;";
    private static final String COLOR_TEXTO_NORMAL = "-fx-text-fill: black;";
    private static final String COLOR_TEXTO_RESALTADO = "-fx-text-fill: #DB981A;";
    private boolean biblioteca = true;
    private Usuario usuario = null;
    private int idBiblioteca;
    List<Cancion> cancionesExternas = new ArrayList<>();
    List<Album> albumesExternos = new ArrayList<>();
    List<Genero> generosExternos = new ArrayList<>();
    List<Cancion> canciones = new ArrayList<>();
    List<Album> albumes = new ArrayList<>();
    List<Genero> generos = new ArrayList<>();
    List<ListaReproduccion> listasReproduccion = new ArrayList<>();

    private Pane paneAlbumes;
    private Pane paneArtistas;
    private Pane paneGeneros;
    private Pane paneAgregarLocal;
    private Pane paneCanciones;
    private Pane paneListasReproduccion;

    IGUAlbumesController controladorAlbumes;
    IGUArtistasController controladorArtistas;
    IGUGenerosController controladorGeneros;
    IGUAgregarCancionLocalController controladorAgregarLocal;
    IGUCancionesController controladorCanciones;
    IGUBarraReproduccionController controladorBarraReproduccion;
    IGUListasReproduccionController controladorListas;

    @FXML
    void resetSalida(MouseEvent event) {
        imageSalir.setImage(new Image("/presentacion/recursos/iconos/salir.png"));
    }

    @FXML
    void resaltarSalida(MouseEvent event) {
        imageSalir.setImage(new Image("/presentacion/recursos/iconos/salirSeleccionado.png"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPanes();
        clicCanciones();
        verificarCreador();
        crearDirectorio();

    }

    /**
     * Método para asignar el usuario que ha ingresado al sistema
     *
     * @param usuario Usuario que ha ingreado al sistema
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private void cargarPanes() {
        controladorAlbumes = new IGUAlbumesController();
        paneAlbumes = controladorAlbumes.abrirIGUAlbumes();

        controladorArtistas = new IGUArtistasController();
        paneArtistas = controladorArtistas.abrirIGUArtistas();

        controladorGeneros = new IGUGenerosController();
        paneGeneros = controladorGeneros.abrirIGUGeneros();

        controladorAgregarLocal = new IGUAgregarCancionLocalController();
        paneAgregarLocal = controladorAgregarLocal.abrirIGUAgregarCancionLocal();

        controladorCanciones = new IGUCancionesController();
        paneCanciones = controladorCanciones.abrirIGUCanciones();

        controladorListas = new IGUListasReproduccionController();
        paneListasReproduccion = controladorListas.abrirIGUListasReproduccion();

        controladorBarraReproduccion = new IGUBarraReproduccionController();
        paneBarraReproduccion = controladorBarraReproduccion.abrirIGUBarraReproduccion(usuario);
        borderPanePrincipal.setBottom(paneBarraReproduccion);
    }

    @FXML
    private void clicExplorar() {
        biblioteca = false;
        buttonExplorar.setStyle(RESALTADO);
        buttonBiblioteca.setStyle(COLOR_TOGGLEBUTTON_NORMAL);

        clicCanciones();

    }

    @FXML
    private void clicBiblioteca() {
        biblioteca = true;
        buttonBiblioteca.setStyle(RESALTADO);
        buttonExplorar.setStyle(COLOR_TOGGLEBUTTON_NORMAL);

        clicCanciones();
    }

    @FXML
    private void clicCanciones() {
        ocultarSeleccion();
        buttonCanciones.setStyle(COLOR_TEXTO_RESALTADO);
        if (biblioteca) {
            Mensaje mensajeCanciones = new Mensaje("recuperarCanciones");
            mensajeCanciones.setObjeto(usuario);
            idBiblioteca = usuario.getIdBiblioteca();
            Cliente.enviarMensaje(mensajeCanciones);
            controladorCanciones.setBiblioteca(biblioteca);
            borderPanePrincipal.setCenter(paneCanciones);
        } else {
            Mensaje mensajeRecuperarCanciones = new Mensaje("recuperarCancionesExternas");
            Cliente.enviarMensaje(mensajeRecuperarCanciones);
            controladorCanciones.setBiblioteca(biblioteca);
            borderPanePrincipal.setCenter(paneCanciones);
        }

    }

    @FXML
    private void clicArtistas() {
        ocultarSeleccion();
        buttonArtistas.setStyle(COLOR_TEXTO_RESALTADO);

        borderPanePrincipal.setCenter(paneArtistas);
        controladorArtistas.setControladorBarraReproduccion(controladorBarraReproduccion);

        if (biblioteca) {
            controladorArtistas.cargarTablaArtistas(canciones);
        } else {
            controladorArtistas.cargarTablaArtistas(cancionesExternas);
        }
    }

    @FXML
    private void clicHistorial() {
        ocultarSeleccion();
        buttonHistorial.setStyle(COLOR_TEXTO_RESALTADO);
        Mensaje mensaje = new Mensaje("recuperarHistorial");
        mensaje.setObjeto(usuario);
        Cliente.enviarMensaje(mensaje);
        paneCanciones.setVisible(true);

    }

    @FXML
    private void clicAlbumes() {
        ocultarSeleccion();
        buttonAlbumes.setStyle(COLOR_TEXTO_RESALTADO);

        borderPanePrincipal.setCenter(paneAlbumes);
        controladorAlbumes.setControladorBarraReproduccion(controladorBarraReproduccion);
        if (biblioteca) {
            controladorAlbumes.cargarTablaAlbumes(albumes, canciones);
        } else {
            controladorAlbumes.cargarTablaAlbumes(albumes, cancionesExternas);
        }

    }

    @FXML
    private void clicGeneros() {
        ocultarSeleccion();
        buttonGeneros.setStyle(COLOR_TEXTO_RESALTADO);
        borderPanePrincipal.setCenter(paneGeneros);
        controladorGeneros.setControladorBarraReproduccion(controladorBarraReproduccion);

        if (biblioteca) {
            controladorGeneros.cargarTablaGeneros(generos, albumes, canciones);
        } else {
            controladorGeneros.cargarTablaGeneros(generosExternos, albumesExternos, cancionesExternas);
        }

    }

    @FXML
    private void clicSubirLocal() {
        clicBiblioteca();
        ocultarSeleccion();
        Cliente.enviarMensaje(new Mensaje("recuperarCatalogoGeneros"));
        buttonAgregarLocal.setStyle(COLOR_TEXTO_RESALTADO);

        borderPanePrincipal.setCenter(paneAgregarLocal);
    }

    @FXML
    private void clicListas() {
        clicBiblioteca();
        ocultarSeleccion();
        buttonListas.setStyle(COLOR_TEXTO_RESALTADO);
        borderPanePrincipal.setCenter(paneListasReproduccion);
        controladorListas.setListasReproduccion(listasReproduccion);
        controladorListas.setControladorBarraReproduccion(controladorBarraReproduccion);
        controladorListas.cargarTablaListas();
    }

    @FXML
    private void clicSubirContenido() {
        ocultarSeleccion();
        buttonContenido.setStyle(COLOR_TEXTO_RESALTADO);
        if (usuario.getTipoUsuario().equals("Creador")) {
            System.out.println("es creador");
        } else {
            String nombreArtistico = Emergente.cargarTextInputDialog("Hazte creador", "Ingresa tu nombre artistico", null);
            Mensaje mensaje = new Mensaje("hacerCreador");
            Usuario usuario = new Usuario();
            usuario.setNombreArtistico(nombreArtistico);
            mensaje.setObjeto(usuario);
            Cliente.enviarMensaje(mensaje);
            buttonContenido.setText("Subir contenido");
            usuario.setTipoUsuario("Creador");
        }
    }

    @FXML
    private void buscarCancion(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String nombre = tFieldBuscar.getText();
            if (!nombre.isEmpty()) {
                ocultarSeleccion();
                List<Cancion> coincidencias = buscarCoindicencias(nombre);
                controladorCanciones.cargarTablaCanciones(coincidencias, controladorBarraReproduccion);
                imageCancelarBusqueda.setVisible(true);
                borderPanePrincipal.setCenter(paneCanciones);
            }
        }
    }

    @FXML
    private void clicSalir() {
        Mensaje mensajeSalida = new Mensaje("cerrarConexión");
        Cliente.enviarMensaje(mensajeSalida);
        ClienteStreaming.cerrarConexion();
        Cliente.cerrarConexion();
        String userHome = System.getProperty("user.home");
        String finalPath = userHome + "RombaFiles/cache/";
        File file = new File(finalPath);
        limpiarCache(file);

    }

    @FXML
    private void limpiarBusqueda() {
        tFieldBuscar.clear();
        ocultarSeleccion();
        controladorCanciones.cargarTablaCanciones(canciones, controladorBarraReproduccion);
        imageCancelarBusqueda.setVisible(false);
        borderPanePrincipal.setCenter(paneCanciones);
    }

    private void ocultarSeleccion() {
        buttonHistorial.setStyle(COLOR_TEXTO_NORMAL);
        buttonCanciones.setStyle(COLOR_TEXTO_NORMAL);
        buttonAlbumes.setStyle(COLOR_TEXTO_NORMAL);
        buttonArtistas.setStyle(COLOR_TEXTO_NORMAL);
        buttonGeneros.setStyle(COLOR_TEXTO_NORMAL);
        buttonAgregarLocal.setStyle(COLOR_TEXTO_NORMAL);
        buttonListas.setStyle(COLOR_TEXTO_NORMAL);
        buttonContenido.setStyle(COLOR_TEXTO_NORMAL);
    }

    private void verificarCreador() {
        if (usuario.getTipoUsuario().equals("Creador")) {
            buttonContenido.setText("Subir contenido");
        } else {
            buttonContenido.setText("Hazte creador");
        }
    }

    private static void crearDirectorio() {
        String userHome = System.getProperty("user.home");
        File rommbaCache = new File(userHome + "/RombaFiles/cache");
        File rommbaLocal = new File(userHome + "/RombaFiles/local");
        boolean check = false;
        if (rommbaCache.isDirectory() && rommbaLocal.isDirectory()) {
            check = true;
        } else {
            if (!rommbaCache.isDirectory()) {
                check = rommbaCache.mkdirs();
            }
            if (!rommbaLocal.isDirectory()) {
                check = rommbaLocal.mkdirs();
            }
        }
        if (!check) {
            Emergente.cargarEmergente("Error", "Imposible crear las carpetas de la aplicación");
        }
    }

    /**
     * Método parar abrir la ventana de la clase.
     *
     * @param stageActual Stage del cual ha sido solicitado
     * @param usuario Usuario que ha ingresado al sistema
     */
    public void abrirIGUBiblioteca(Stage stageActual, Usuario usuario) {
        try {
            BorderPane rootPane;
            Stage stagePrincipal = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGUBiblioteca.fxml"));

            IGUBibliotecaController controlador = new IGUBibliotecaController();
            controlador.setUsuario(usuario);
            fxmlLoader.setController(controlador);
            Cliente.iniciarEscuchaDeMensajes(controlador);
            rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setScene(scene);
            stagePrincipal.setTitle("ROOMBA Biblioteca");
            stagePrincipal.show();
            stageActual.close();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para esperar asignaciones ante eventos que son recibidos en por el
     * hilo del cliente.
     *
     * @param respuesta Objeto posteriormente casteado a Mensaje que contiene la
     * información recibida
     */
    public void recibirMensaje(Object respuesta) {
        Mensaje mensaje = (Mensaje) respuesta;
        String asunto = mensaje.getAsunto();
        switch (asunto) {
            case "canciones":
                Biblioteca bibliotecaServ = (Biblioteca) mensaje.getObjeto();
                canciones = bibliotecaServ.getCanciones();
                break;
            case "albumes":
                albumes = (List<Album>) mensaje.getObjeto();
                break;
            case "generos":
                generos = (List<Genero>) mensaje.getObjeto();
                break;
            case "historial":
                List<Cancion> cancionesHistorial = (List<Cancion>) mensaje.getObjeto();
                controladorCanciones.cargarTablaCanciones(cancionesHistorial, controladorBarraReproduccion);
                break;
            case "catalogoGeneros":
                List<Genero> catalogoGeneros = (List<Genero>) mensaje.getObjeto();
                controladorAgregarLocal.cargarComboGeneros(catalogoGeneros);
                break;
            case "listasReproduccion":
                listasReproduccion = (List<ListaReproduccion>) mensaje.getObjeto();
                controladorCanciones.setListasReproduccion(listasReproduccion);
                controladorCanciones.cargarTablaCanciones(canciones, controladorBarraReproduccion);
                break;
            case "cancionesExternas":
                cancionesExternas = (List<Cancion>) mensaje.getObjeto();
                controladorCanciones.cargarTablaCanciones(cancionesExternas, controladorBarraReproduccion);
                break;
            case "albumesExternos":
                albumesExternos = (List<Album>) mensaje.getObjeto();
                break;
            case "generosExternos":
                generosExternos = (List<Genero>) mensaje.getObjeto();
                break;
            case "cancionAgregadaABiblioteca":
                Platform.runLater(() -> {
                    Emergente.cargarEmergente("Aviso", "Canción agregada a tu biblioteca local");
                });
                break;
            case "cancionEliminada":
                
                break;
            case "cerrarConexión":
                Cliente.cerrarConexion();
                break;
        }

    }

    /**
     * Método para eliminar las carpetas de caché. Gracias a Jeff Learman.
     *
     * @param file archivo con el path a eliminar
     */
    private void limpiarCache(File cache) {
        try {
            //No funciona :l
            FileUtils.deleteDirectory(cache);
            System.out.println("Limpio");
        } catch (IOException ex) {
            Logger.getLogger(IGUBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Cancion> buscarCoindicencias(String nombre) {
        List<Cancion> coincidencias = new ArrayList<>();
        for (int i = 0; i < canciones.size(); i++) {
            if (canciones.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                coincidencias.add(canciones.get(i));
            }
        }
        return coincidencias;
    }
}
