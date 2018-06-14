/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombaclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static logica.conexion.Cliente.abrirConexion;

/**
 *
 * @author Alex Cámara
 */
public class RoombaClient extends Application {

    private Stage stagePrincipal;

    @Override
    public void start(Stage stagePrincipal) throws Exception {
        this.stagePrincipal = stagePrincipal;
        showMainWindows();
    }

    /**
     * Muestra la ventana principal del sistema
     */
    public void showMainWindows() {
        AnchorPane rootPane = new AnchorPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/IGUInicioSesion.fxml"));

        try {
            rootPane = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(RoombaClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(rootPane);

        stagePrincipal.setScene(scene);
        stagePrincipal.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        abrirConexion();
        launch(args);
    }

}
