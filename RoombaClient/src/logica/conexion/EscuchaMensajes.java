package logica.conexion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.IGUBibliotecaController;

/**
 * Clase para recibir las respuestas del servidor de base de datos
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class EscuchaMensajes extends Thread {

   IGUBibliotecaController iguBiblioteca;

   /**
    * Método para asignar el controllado con el cual se comunicará la clase
    * @param iguBiblioteca
    */
   public EscuchaMensajes(IGUBibliotecaController iguBiblioteca) {
      this.iguBiblioteca = iguBiblioteca;
   }
   

   @Override
   public void run() {
      Object respuesta = null;
      do {
         try {
            System.out.println("Esperando...");
            respuesta = Cliente.entradaRed.readObject();
         } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
         }
         iguBiblioteca.recibirMensaje(respuesta);
      } while (true);

   }

}
