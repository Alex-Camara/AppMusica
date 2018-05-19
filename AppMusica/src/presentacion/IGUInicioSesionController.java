/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import presentacion.recursos.Emergente;

/**
 * FXML Controller class
 *
 * @author Alex Cámara
 */
public class IGUInicioSesionController implements Initializable {

   @FXML
   private JFXTextField tFieldCorreo;
   @FXML
   private JFXTextField tFieldClave;

   /**
    * Initializes the controller class.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      // TODO
   }

   @FXML
   private void ingresar() {
      String correo = tFieldCorreo.getText();
      String clave = tFieldClave.getText();
      //Aquí también podrías incorporar tu regex de correo
      if (correo.isEmpty() || correo.trim() == null || clave.isEmpty() || clave.trim() == null) {
         Emergente.cargarEmergente("Aviso", "Por favor, ingresa todos los datos");
      } else {
//         Usuario usuario = Método para recuperar al usuario
//         if (usuario == null) {
//            Emergente.cargarEmergente("Aviso", "Datos incorrectos");
//         } else {
//            cargarVenana (Creo que tú abres la ventana con un método diferente para que aparezca con
//                un efecto)
//         }
      }
   }

   @FXML
   private void registrar() {

   }

}
