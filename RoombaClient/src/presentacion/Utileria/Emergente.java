/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.Utileria;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

/**
 *
 * @author javr
 */
public class Emergente {

    public static void cargarEmergente(String titulo, String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
        confirmacion.setTitle(titulo);
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(mensaje);
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
    }

    public static String cargarTextInputDialog(String titulo, String mensaje, String mensajeDefault) {
        String resultado = null;
        TextInputDialog dialog = new TextInputDialog(mensajeDefault);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setTitle(titulo);
        dialog.setHeaderText(null);
        dialog.setContentText(mensaje);
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            resultado = result.get();
        } 
        return resultado;
    }

}
