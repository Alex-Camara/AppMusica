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

/**
 *
 * @author Alex Cámara
 */
public class ClienteFormato {

    private static InetAddress host;
    private static final int PUERTO = 2000;
    public static ObjectInputStream entradaRed;
    private static ObjectOutputStream salidaRed;
    static Socket socket;

    public static void abrirConexion() {
        try {
            host = InetAddress.getLocalHost();
            System.out.println("se inicio conversación");
            System.out.println("host: " + host);
            //socket = new Socket(host, 2000);
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
            System.out.println("EL SERVIDOR ESTA CAIDO");
            ex.printStackTrace();
        }
    }
}
