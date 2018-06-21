package presentacion;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logica.Cancion;
import logica.ListaReproduccion;
import logica.conexion.Mensaje;
import logica.conexion.Cliente;
import presentacion.Utileria.Emergente;

/**
 * Clase del controlador para mostrar los géneros de las canciones.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class IGUCancionesController implements Initializable {

    @FXML
    private Pane paneCanciones;
    @FXML
    private TableView<Cancion> tableCanciones;
    @FXML
    private TableColumn<Cancion, String> tcColumnNombre;
    @FXML
    private TableColumn<?, ?> tcColumnArtista;
    @FXML
    private TableColumn<?, ?> tcColumnCalificacion;
    private List<ListaReproduccion> listasReproduccion;
    IGUListasReproduccionController controladorListas;
    Menu menuItemAgregarALista;
    List<MenuItem> menues = new ArrayList<>();
    boolean biblioteca;

    public void setBiblioteca(boolean biblioteca) {
        this.biblioteca = biblioteca;
    }

    /**
     * Método para asignar el controlador de las listas de reproducción
     *
     * @param controladorListas IGUListasReproduccionController inicializado
     */
    public void setControladorListas(IGUListasReproduccionController controladorListas) {
        this.controladorListas = controladorListas;
    }

    /**
     * Método para asignar la lista de elementos de ListaReproduccion.
     *
     * @param listasReproduccion List<ListaReproduccion> con elementos para
     * asignar
     */
    public void setListasReproduccion(List<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

    /**
     * Método par asignar el estatus del pane del controller.
     *
     * @param estatus Boolean del estatus
     */
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
        tableCanciones.refresh();
        ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones);

        tcColumnNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tcColumnArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
        tcColumnCalificacion.setCellValueFactory(new PropertyValueFactory<>("Calificacion"));

        tableCanciones.setItems(obsCanciones);
        agregarListenersTablaCanciones(controladorBarraReproduccion);
    }

    public void agregarListenersTablaCanciones(IGUBarraReproduccionController controladorBarraReproduccion) {
        tableCanciones.setRowFactory(
                new Callback<TableView<Cancion>, TableRow<Cancion>>() {
            @Override
            public TableRow<Cancion> call(TableView<Cancion> tableView) {
                final TableRow<Cancion> row = new TableRow<>();

                //MENU CONTEXTUAL
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem menuItemAgregarCancion = new MenuItem("Agregar canción a cola");
                MenuItem menuItemAgregarContinuacion = new MenuItem("Agregar canción a continuación");

                if (biblioteca) {

                    MenuItem menuItemRadio = new MenuItem("Crear radio");
                    MenuItem menuItemDescargar = new MenuItem("Descargar canción");
                    MenuItem menuItemEliminarCancion = new MenuItem("Eliminar canción de biblioteca");
                    menuItemAgregarALista = new Menu("Agregar a lista de reproducción");

                    seleccionMenuItem(row);

                    rowMenu.getStyleClass().add("/presentacion/estilos/EstiloMenuContextual.css");
                    rowMenu.getItems().addAll(menuItemAgregarCancion, menuItemAgregarContinuacion, menuItemRadio, menuItemDescargar, menuItemEliminarCancion, menuItemAgregarALista);

                    //Eventos
                    menuItemDescargar.setOnAction((ActionEvent) -> {
                        Cancion cancion = row.getItem();
                        IGUBarraReproduccionController.obtenerCancion(cancion, true);
                    });
                    menuItemEliminarCancion.setOnAction((ActionEvent) -> {
                        Cancion cancion = row.getItem();
                        eliminarCancionDeBiblioteca(cancion.getIdCancion());
                    });

                } else {
                    MenuItem menuItemAgregarABiblioteca = new MenuItem("Agregar canción a biblioteca");
                    rowMenu.getItems().addAll(menuItemAgregarABiblioteca, menuItemAgregarCancion, menuItemAgregarContinuacion);
                    
                    //Eventos
                    menuItemAgregarABiblioteca.setOnAction((ActionEvent) -> {
                        Cancion cancion = row.getItem();
                        agregarABiblioteca(row.getItem());
                    });
                }

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));

                //SELECCIÓN DE UNA CANCIÓN
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Cancion cancion = row.getItem();
                        controladorBarraReproduccion.recuperarCancionYReproducir(cancion, false);
                        controladorBarraReproduccion.cargarBarraReproduccion(cancion);
                    }
                });

                menuItemAgregarCancion.setOnAction((ActionEvent event) -> {
                    Cancion cancion = row.getItem();
                    controladorBarraReproduccion.reajustarDatosColaReproduccion(cancion, true);
                });
                menuItemAgregarContinuacion.setOnAction((ActionEvent) -> {
                    Cancion cancion = row.getItem();
                    controladorBarraReproduccion.reajustarDatosColaReproduccion(cancion, false);
                });
                return row;
            }
        });
    }

    public void seleccionMenuItem(TableRow<Cancion> row) {
        //System.out.println("tamaño: + " + listasReproduccion.size());

        for (int i = 0; i < listasReproduccion.size(); i++) {
            MenuItem menuItem = new MenuItem(listasReproduccion.get(i).getNombre());
            menues.add(menuItem);

            menuItemAgregarALista.getItems().add(menuItem);
        }

        for (int i = 0; i < menuItemAgregarALista.getItems().size(); i++) {
            ListaReproduccion lista = listasReproduccion.get(i);

            menuItemAgregarALista.getItems().get(i).setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    agregarCancionALista(row.getItem(), lista);
                }
            });
        }

        MenuItem menuItemAgregarLista = new MenuItem("Agregar nueva lista");
        menuItemAgregarLista.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                agregarNuevaLista();
            }
        });
        menuItemAgregarALista.getItems().add(menuItemAgregarLista);
    }

    private void agregarNuevaLista() {
        String tituloLista = Emergente.cargarTextInputDialog(null, "Introduce el nombre de la lista:", null);
        ListaReproduccion nuevaLista = new ListaReproduccion();
        nuevaLista.setNombre(tituloLista);
        listasReproduccion.add(nuevaLista);
        Mensaje mensajeAgregarLista = new Mensaje("agregarLista");
        mensajeAgregarLista.setObjeto(nuevaLista);
        Cliente.enviarMensaje(mensajeAgregarLista);
    }

    public void agregarCancionALista(Cancion cancion, ListaReproduccion lista) {
        boolean duplicada = verificarDuplicada(cancion, lista, listasReproduccion);

        if (!duplicada) {
            HashMap<ListaReproduccion, Cancion> hash = new HashMap<>();
            hash.put(lista, cancion);
            Mensaje mensajeAgregarCancionALista = new Mensaje("agregarCancionALista");
            mensajeAgregarCancionALista.setObjeto(hash);
            Cliente.enviarMensaje(mensajeAgregarCancionALista);

        } else {
            Emergente.cargarEmergente("Advertencia", "La canción ya se encuentra en la lista");
        }
    }

    /**
     * Método para verificar que la canción que se encuentra en una lista de
     * reproducción no se encuentre en las listas de reproducción
     *
     * @param cancion Canción a agregar
     * @param lista Lista de reproducción a agregar la canción
     * @param listas Listas de reproducción ya guardadas
     * @return Boolean de la existencia de la canción
     */
    public boolean verificarDuplicada(Cancion cancion, ListaReproduccion lista, List<ListaReproduccion> listas) {
        boolean duplicada = false;

        for (int i = 0; i < listasReproduccion.size(); i++) {
            if (listasReproduccion.get(i).getIdListaReproduccion() == lista.getIdListaReproduccion()) {
                for (int j = 0; j < listasReproduccion.get(i).getCanciones().size(); j++) {
                    if (listasReproduccion.get(i).getCanciones().get(j).getNombre().equals(cancion.getNombre())) {
                        duplicada = true;
                    }
                }
            }
        }
        return duplicada;
    }
    
    public void agregarABiblioteca(Cancion cancion){
        Mensaje mensaje = new Mensaje("agregarABiblioteca");
        mensaje.setObjeto(cancion);
        Cliente.enviarMensaje(mensaje);
    }
    
    public void eliminarCancionDeBiblioteca(Integer idCancion){
        boolean eliminar = Emergente.cargarEmergenteConOpciones("Eliminar canción", "¿Estás seguro de eliminar esta canción de tu biblioteca?");
        System.out.println("respuesta: " + eliminar);
        Mensaje mensaje = new Mensaje("eliminarCancion");
        System.out.println("id cancion: " + idCancion);
        mensaje.setObjeto(idCancion);
        Cliente.enviarMensaje(mensaje);
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
