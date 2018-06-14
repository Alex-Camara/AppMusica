/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.IGUBibliotecaController;

/**
 *
 * @author Alex Cámara
 */
public class Cliente {

    private static InetAddress host;
    private static final int PUERTO = 1234;
    public static ObjectInputStream entradaRed;
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

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void enviarMensaje(Mensaje mensajeEnviar) {
        new Thread(() -> {
            try {
                //System.out.println("asuntoEnviar " + mensajeEnviar.getAsunto());

                salidaRed.writeObject(mensajeEnviar);
                
                //System.out.println("Enviado en Cliente");
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

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

    public static void iniciarEscuchaDeMensajes(IGUBibliotecaController bibliotecaController) {

        EscuchaMensajes hiloEscuchaMensajes = new EscuchaMensajes(bibliotecaController);
        hiloEscuchaMensajes.start();
    }

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
