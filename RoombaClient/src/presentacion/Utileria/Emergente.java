/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.Utileria;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

/**
 * Clase para mostrar mensajes emergentes.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 *
 */
public class Emergente {

    /**
     * Método para cargar un mensaje emergente.
     *
     * @param titulo String del título de la ventana
     * @param mensaje String del mensaje del aviso emergente
     */
    public static void cargarEmergente(String titulo, String mensaje) {
        Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
        confirmacion.setTitle(titulo);
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(mensaje);
        ButtonType btAceptar = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmacion.getButtonTypes().setAll(btAceptar);
        confirmacion.showAndWait();
    }

    /**
     * Método para cargar una ventana emergente para ingresar texto.
     *
     * @param titulo String del título de la ventana
     * @param mensaje String del mensaje a mostrar en el cuerpo
     * @param mensajeDefault Strinf del mensaje de ingreso de información
     * @return
     */
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

    /**
     * Método para cargar un mensaje emergente.
     *
     * @param titulo String del título de la ventana
     * @param mensaje String del mensaje del aviso emergente
     */
    public static boolean cargarEmergenteConOpciones(String titulo, String mensaje) {
        boolean respuesta;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            respuesta = true;
        } else {
            respuesta = false;
        }
        return respuesta;
    }

}
