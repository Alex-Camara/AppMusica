/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.IGUBibliotecaController;

/**
 * Clase cliente para interactuar con el servidor de base de datos
 *
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Cliente {

   private static final int PUERTO = 1234;
   private static final String DIRECCION_IP = "192.168.43.130";
   public static ObjectInputStream entradaRed;
   private static ObjectOutputStream salidaRed;
   private static Socket socket = null;

   /**
    * Método para iniciar la recepción y envio de datos con el servidor.
    */
   public static void iniciarConversacion() throws IOException {
      socket = new Socket(DIRECCION_IP, PUERTO);
      System.out.println("Conexión establecida con servidor de BD");
      salidaRed = new ObjectOutputStream(socket.getOutputStream());
      entradaRed = new ObjectInputStream(socket.getInputStream());
   }

   /**
    * Método para envía un mensaje al servidor.
    *
    * @param mensajeEnviar Mensaje con la información del tipo de solicitud y el contenido
    */
   public static void enviarMensaje(Mensaje mensajeEnviar) {
      new Thread(() -> {
         try {
            salidaRed.writeObject(mensajeEnviar);
            salidaRed.flush();
         } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
         }
      }).start();
   }

   /**
    * Método para validar a un usuario.
    *
    * @param mensajeAEnviar Mensaja con la información del usuario
    * @return Objeto para castear después a Usuario
    */
   public static Object enviarUsuario(Mensaje mensajeAEnviar) {
      Object respuesta = null;
      try {
         salidaRed.writeObject(mensajeAEnviar);
         respuesta = entradaRed.readObject();
      } catch (IOException ex) {
         Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
         Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
      }
      return respuesta;
   }

   /**
    * Método para iniciar la recepción atenta del servidor
    *
    * @param bibliotecaController
    */
   public static void iniciarEscuchaDeMensajes(IGUBibliotecaController bibliotecaController) {
      EscuchaMensajes hiloEscuchaMensajes = new EscuchaMensajes(bibliotecaController);
      hiloEscuchaMensajes.start();
   }

   /**
    * Método para cerrar la conexión con el servidor.
    */
   public static void cerrarConexion() {
      try {
         System.out.println("\nCerrando la conexión");
         socket.close();
         System.exit(0);
      } catch (IOException ex) {
         System.out.println("¡Imposible desconectarse!");
         System.exit(1);
      }
   }
}
