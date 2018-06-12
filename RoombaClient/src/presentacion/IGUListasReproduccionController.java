/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
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
import logica.conexion.Servidor;
import presentacion.Utileria.Emergente;

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
    private TableColumn<?, ?> tcCalificacion;
    @FXML
    private TableView<ListaReproduccion> tableListas;
    @FXML
    private TableColumn<ListaReproduccion, String> tcNombreLista;
    @FXML
    private JFXButton buttonRegresar;
    @FXML
    private JFXButton buttonAgregarLista;
    private List<ListaReproduccion> listasReproduccion;
    private IGUBarraReproduccionController controladorBarraReproduccion;

    public void setControladorBarraReproduccion(IGUBarraReproduccionController controladorBarraReproduccion) {
        this.controladorBarraReproduccion = controladorBarraReproduccion;
    }

    public void setListasReproduccion(List<ListaReproduccion> listasReproduccion) {
        this.listasReproduccion = listasReproduccion;
    }

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

    public void cargarTablaListas() {

        ObservableList<ListaReproduccion> obsListasReproduccion = FXCollections.observableArrayList(listasReproduccion
        );

        tcNombreLista.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tableListas.setItems(obsListasReproduccion);
        agregarListenersTablaListas();
    }

    private void agregarListenersTablaListas() {
        tableListas.setRowFactory(
                new Callback<TableView<ListaReproduccion>, TableRow<ListaReproduccion>>() {
            @Override
            public TableRow<ListaReproduccion> call(TableView<ListaReproduccion> tableView) {
                final TableRow<ListaReproduccion> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem menuItemEditar = new MenuItem("   Editar   ");
                MenuItem menuItemEliminar = new MenuItem("   Eliminar   ");
                menuItemEliminar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        tableListas.getItems().remove(row.getItem());
                        eliminarLista(row.getItem());
                    }
                });

                menuItemEditar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        editarLista(row.getItem());
                    }
                });
                rowMenu.getStyleClass().add("/presentacion/estilos/EstiloMenuContextual.css");
                rowMenu.getItems().addAll(menuItemEditar, menuItemEliminar);

                // only display context menu for non-null items:
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));

                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        ListaReproduccion listaSeleccionadata = row.getItem();
                        habilitarCanciones(listaSeleccionadata);
                    }
                });
                return row;
            }
        });
    }

    public void habilitarCanciones(ListaReproduccion listaSeleccionadata) {

        tableListas.setVisible(false);
        tableCanciones.setVisible(true);
        buttonRegresar.setVisible(true);
        buttonAgregarLista.setVisible(false);
        cargarTablaCanciones(listaSeleccionadata.getCanciones(), controladorBarraReproduccion);
    }

    public void cargarTablaCanciones(List<Cancion> canciones, IGUBarraReproduccionController controladorBarraReproduccion) {
        if (canciones != null) {
            ObservableList<Cancion> obsCanciones = FXCollections.observableArrayList(canciones);
            tcNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            tcArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
            tcCalificacion.setCellValueFactory(new PropertyValueFactory<>("Calificacion"));

            tableCanciones.setItems(obsCanciones);
            agregarListenersTablaCanciones();
        }
    }

    public void agregarListenersTablaCanciones() {
        tableCanciones.setRowFactory(
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

    @FXML
    void regresar(ActionEvent event) {
        tableListas.setVisible(true);
        tableCanciones.setVisible(false);
        buttonRegresar.setVisible(false);
        buttonAgregarLista.setVisible(true);
    }

    @FXML
    void agregarLista(ActionEvent event) {
        String tituloLista = Emergente.cargarTextInputDialog(null, "Introduce el nombre de la lista:", null);
        ListaReproduccion nuevaLista = new ListaReproduccion();
        nuevaLista.setNombre(tituloLista);
        listasReproduccion.add(nuevaLista);
        Mensaje mensajeAgregarLista = new Mensaje("agregarLista");
        mensajeAgregarLista.setObjeto(nuevaLista);
        Servidor.enviarMensaje(mensajeAgregarLista);
        cargarTablaListas();
    }

    private void editarLista(ListaReproduccion lista) {
        String tituloLista = Emergente.cargarTextInputDialog(null, "Introduce el nombre de la lista:", lista.getNombre());

        if (tituloLista != null) {
            lista.setNombre(tituloLista);
            Mensaje mensajeEditarLista = new Mensaje("editarLista");
            mensajeEditarLista.setObjeto(lista);
            Servidor.enviarMensaje(mensajeEditarLista);
            for (int i = 0; i < listasReproduccion.size(); i++) {
                if (listasReproduccion.get(i).getIdListaReproduccion() == lista.getIdListaReproduccion()) {
                    listasReproduccion.get(i).setNombre(tituloLista);
                }
            }
            cargarTablaListas();
        }
    }

    private void eliminarLista(ListaReproduccion lista) {
        Mensaje mensajeEliminarLista = new Mensaje("eliminarLista");
        mensajeEliminarLista.setObjeto(lista);
        Servidor.enviarMensaje(mensajeEliminarLista);
        for (int i = 0; i < listasReproduccion.size(); i++) {
            if (listasReproduccion.get(i).getIdListaReproduccion() == lista.getIdListaReproduccion()) {
                listasReproduccion.remove(i);
            }
        }
        cargarTablaListas();
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
