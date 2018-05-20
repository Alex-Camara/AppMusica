/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex Cámara
 */
public class Servidor {

    private static InetAddress host;
    private static final int PUERTO = 1234;
    private static ObjectInputStream entradaRed;
    private static ObjectOutputStream salidaRed;
    private static Socket socket = null;

    public static void abrirConexion() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            System.out.println("\n!ID de host no encontrado¡\n");
            System.exit(1);
        }
    }
    
    public static void iniciarConversacion() {

        try {
            socket = new Socket(host, PUERTO);
            salidaRed = new ObjectOutputStream(socket.getOutputStream());
            entradaRed = new ObjectInputStream(socket.getInputStream());
            
            //iniciarEscuchaDeMensajes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object enviarMensaje(Mensaje mensajeAEnviar) {
        Object respuesta = null;
        try {
            salidaRed.writeObject(mensajeAEnviar);
            respuesta = entradaRed.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    
    public static void iniciarEscuchaDeMensajes() {

        Thread hiloEscuchaMensajes = new Thread(new Runnable() {
            @Override
            public void run() {
               
            }
        });
        hiloEscuchaMensajes.start();
    }

    public static void cerrarConexion(Socket socket) {
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
