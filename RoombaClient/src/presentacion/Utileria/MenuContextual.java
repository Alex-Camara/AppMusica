/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.Utileria;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logica.Cancion;

/**
 *
 * @author Alex Cámara
 */
public class MenuContextual extends TableCell<XYChart.Data, String> {

    final MenuButton botonMenu = new MenuButton();

    public MenuContextual() {

    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);

        } else {
            final ContextMenu contextMenu = new ContextMenu();

            setGraphic(botonMenu);
            Image image = new Image(getClass().getResourceAsStream("/presentacion/recursos/iconos/tresPuntos.png"));
            botonMenu.setStyle("-fx-background-color:transparent;");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(10);
            imageView.setFitWidth(10);
            botonMenu.setGraphic(imageView);
            botonMenu.setContentDisplay(ContentDisplay.CENTER);
            botonMenu.setMinHeight(10);
            setText(null);

            MenuItem menuItemAgregarCancion = new MenuItem("Agregar canción a cola");
            MenuItem menuItemAgregarContinuacion = new MenuItem("Agregar canción a continuación");
            MenuItem menuItemRadio = new MenuItem("Crear radio");
            MenuItem menuItemDescargar = new MenuItem("Descargar canción");
            MenuItem menuItemEliminarCancion = new MenuItem("Eliminar canción de biblioteca");
            botonMenu.getStylesheets().add("/presentacion/estilos/EstiloMenuContextual.css");
            botonMenu.getItems().addAll(menuItemAgregarCancion, menuItemAgregarContinuacion, menuItemRadio, menuItemDescargar, menuItemEliminarCancion);
        }
    }
};
