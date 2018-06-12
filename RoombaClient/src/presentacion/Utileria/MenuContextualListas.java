/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.Utileria;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import logica.Cancion;

/**
 *
 * @author Alex Cámara
 */
public class MenuContextualListas extends TableCell<Pair<String, Object>, Object> {

    final MenuButton botonMenu = new MenuButton();

    public MenuContextualListas() {

    }

    @Override
    public void updateItem(Object item, boolean empty) {
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

            MenuItem menuItemEditar = new MenuItem("   Editar   ");
            MenuItem menuItemEliminar = new MenuItem("   Eliminar   ");
            
            menuItemEditar.setOnAction(new EventHandler<ActionEvent>() {  
                @Override  
                public void handle(ActionEvent event) {  
                    System.out.println("item elegido: " + item.toString());
                    Cancion cancion = (Cancion) item;
                    System.out.println("nombre: " + cancion.getNombre());
                }  
            }); 
            
            botonMenu.getStylesheets().add("/presentacion/estilos/EstiloMenuContextual.css");
            botonMenu.getItems().addAll(menuItemEditar, menuItemEliminar);
        }
    }
};
