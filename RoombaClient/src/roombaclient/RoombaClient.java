
package roombaclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logica.conexion.Cliente;

/**
 * Clase de cliente de la aplicación de música en streaming Roomba.
 * Esta aplicación permite la administración de música pública conservada en un servidor de área loca,
 * además de poder guardar canciones locales en diferentes calidades en dicho servidor.
 * La mayoría de los recursos de los icono fueron gracias a Freepik from www.flaticon.com 
 * 25/06/2018
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class RoombaClient extends Application {

    private Stage stagePrincipal;

    @Override
    public void start(Stage stagePrincipal) throws Exception {
        this.stagePrincipal = stagePrincipal;
        showMainWindows();
    }

    /**
     * Método para mostrar la ventana principal del sistema.
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
        Cliente.abrirConexion();
        launch(args);
    }

}
