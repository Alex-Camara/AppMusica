/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.conexion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.IGUBibliotecaController;

/**
 *
 * @author javr
 */
public class EscuchaMensajes extends Thread {

   IGUBibliotecaController iguBiblioteca;

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
