/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import logica.conexion.Mensaje;
import logica.conexion.Cliente;
import static logica.conexion.Cliente.enviarUsuario;
import static logica.conexion.Cliente.iniciarConversacion;
import logica.Usuario;
import logica.conexion.ClienteFormato;
import presentacion.Utileria.Emergente;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
 */
public class IGUInicioSesionController implements Initializable {

    @FXML
    private JFXTextField tFieldCorreo;
    @FXML
    private JFXPasswordField tFieldClave;

    Cliente server;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //server = new Cliente();
        //ServidorFormato.abrirConexion();
        //ServidorFormato.iniciarConversacion();
    }

    @FXML
    private void ingresar() {
        String correo = tFieldCorreo.getText();
        String clave = tFieldClave.getText();
        if (correo.isEmpty() || correo.trim() == null || clave.isEmpty() || clave.trim() == null) {
            Emergente.cargarEmergente("Aviso", "Por favor, ingresa todos los datos");
        } else {
            iniciarConversacion();
            Usuario usuario = new Usuario();
            usuario.setCorreo(correo);
            usuario.setClave(clave);
            Mensaje mensaje = new Mensaje();
            mensaje.setAsunto("recuperar usuario");
            mensaje.setObjeto(usuario);
            Usuario usuarioRecibido = (Usuario) enviarUsuario(mensaje);

            if (usuarioRecibido.getNombre() == null) {
                Emergente.cargarEmergente("Aviso", "Datos incorrectos");
            } else {
                Stage mainWindow = (Stage) tFieldCorreo.getScene().getWindow();
                IGUBibliotecaController controlador = new IGUBibliotecaController();
                controlador.abrirIGUBiblioteca(mainWindow, usuarioRecibido);
            }
        }
    }

    @FXML
    private void registrar() {

    }

}
