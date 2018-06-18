/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.conexion;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Cancion;

public class ClienteFormatos {

    static Socket socket;
    static int PORT = 9999;
    private static final String DIRECCION_IP = "192.168.0.34";

    public static void abrirConexion() throws IOException {
        System.out.println("Abriendo puerto\n");

        socket = new Socket(DIRECCION_IP, PORT);

    }

    public static void enviarArchivo(Cancion cancion, File archivo) {

        FileInputStream fis = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        DataInputStream in = null;

        try {

            //leer el archivo que quiere recuperar el cliente
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int longitudArchivo = (int) archivo.length();

            System.out.println("archivo: " + archivo);
            System.out.println("longitud archivo: " + longitudArchivo);

            //El servidor envia al cliente el directorio del archivo
            out.println(cancion.getRuta() + "\n");
            out.flush();

            //El servidor envia al cliente el nombre del archivo
            out.println(archivo.getName());
            out.flush();

            //El servidor envia al cliente el archivo
            byte[] buffer = new byte[(int) longitudArchivo];
            System.out.println("buffer = " + Arrays.toString(buffer));

            fis = new FileInputStream(archivo);

            bis = new BufferedInputStream(fis);
            bis.read(buffer, 0, buffer.length);
            os = socket.getOutputStream();
            System.out.println("enviando archivo " + archivo + "(" + buffer.length + " bytes)");
            os.write(buffer, 0, buffer.length);
            os.flush();

        } catch (IOException ex) {
            Logger.getLogger(ClienteFormatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    public static void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException ex) {

        }

    }
}
