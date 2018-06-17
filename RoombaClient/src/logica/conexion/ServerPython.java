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

public class ServerPython {
    static Socket socket;
    static int PORT = 1233;

    public static void main(String args[]) {
        System.out.println("Abriendo puerto\n");

        try {
            socket = new Socket("localhost", PORT);
            run();
        } catch (SocketException ex) {
            System.out.println("Error al vincularse al puerto!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error al crear el socket!");
            System.exit(1);
        }

    }

    public static void run() {
        FileInputStream fis = null;
        OutputStream os = null;
        BufferedInputStream bis = null;

        try {

            //leer el archivo que quiere recuperar el cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //Crear el archivo solicitado
            String nombreArchivo = "HP.mp3";
            String nombreCancion = "Believer";
            String nombreArtista = "Imagine Dragons";
            String nombreAlbum = "Imagine Dragons";

            String directorioAGuardar = "/musica/" + nombreArtista + "/" + nombreAlbum + "/" + nombreCancion + "/";

            //se va a quitar
            File archivo = new File(nombreArchivo);
            int longitudArchivo = (int) archivo.length();
            //String longArchivo = String.valueOf(longitudArchivo);

            System.out.println("archivo: " + archivo);
            System.out.println("longitud archivo: " + longitudArchivo);

            //El servidor envia al cliente el directorio del archivo
            out.println(directorioAGuardar + "\n");
            out.flush();

            //El servidor envia al cliente el nombre del archivo
            out.println(nombreArchivo);
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
            
            String respuesta = in.readLine();
            System.out.println("respuesta: " + respuesta);
        } catch (IOException ex) {
            Logger.getLogger(ServerPython.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {

            }
        }
    }
}
