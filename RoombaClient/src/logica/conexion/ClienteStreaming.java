package logica.conexion;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de Mensaje como objeto para interactuar con el servidor de base de datos
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class ClienteStreaming {

    private static final String HOST = "192.168.0.34";
    private static final int PUERTO = 1235;
    private static Socket cliente;
    private static PrintWriter salida;

    /**
     * Método para establecer la conexión con el servidor. Establece host,
     * puerto para un socket.
     */
    public static void abrirConexion() {
        try {
            InetAddress inetAddress = InetAddress.getByName(HOST);
            cliente = new Socket(inetAddress, PUERTO);
            System.out.println("Conexión establecida con servidor de streaming");
        } catch (UnknownHostException ex) {
            System.out.println("\n!ID de host no encontrado¡\n");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Imposible establecer la conexión");
            Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para solicitar los datos de una canción.
     *
     * @param ruta String de la ruta del archivo de la canción. Por ejemplo:
     * Grupo/Album/Cancion
     * @param calidad int de la calidad del audio deseado
     */
    public static void solicitarCancion(String ruta, int calidad) {
        abrirConexion();
        new Thread(() -> {
            try {
                final String rutaCompleta = ruta + "/" + calidad + ".mp3";
                salida = new PrintWriter(cliente.getOutputStream(), true);
                salida.print(rutaCompleta);
                salida.flush(); //Necesario para no añadir un salto en la direccón
                System.out.println("Canción solicitada");
            } catch (SocketException ex) {
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    /**
     * Método para solicitar los datos de una canción. Predefinida la calidad
     * más alta.
     *
     * @param ruta String de la ruta del archivo de la canción
     */
    public static void solicitarCancion(String ruta) {
        new Thread(() -> {
            try {
                final String rutaCompleta = ruta + "/0.mp3";
                salida = new PrintWriter(cliente.getOutputStream(), true);
                salida.print(rutaCompleta);
                salida.flush(); //Necesario para no añadir un salto en la direccón
                System.out.println("Canción solicitada");
            } catch (SocketException ex) {
                abrirConexion();
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                abrirConexion();
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    /**
     * Método para recuperar los datos de la canción por medio de la conexión
     * del socket antes establecida.
     *
     * @param ruta String de la ruta del archivo. Utilizada para guardarla
     * @param local Boolean para determinar si es un archivo caché o una
     * descarga a archivo local
     * @return Future<Integer> para validar la disponibilidad de la información
     * de la canción
     * @throws IOException
     */
    public static Future<Integer> recuperarCancion(String ruta, boolean local, int calidad) throws IOException {
        return Executors.newSingleThreadExecutor().submit(() -> {
            try {
                byte[] paquete = new byte[4096];
                byte[] bufferTotal = new byte[0];
                String userHome = System.getProperty("user.home");
                File fileCancion;
                if (local) {
                    fileCancion = new File(userHome + "/RombaFiles/local/" + ruta);
                } else {
                    fileCancion = new File(userHome + "/RombaFiles/cache/" + ruta);
                }
                fileCancion.mkdirs();
                FileOutputStream fos = new FileOutputStream(fileCancion + "/" + calidad + ".mp3");
                System.out.println("file cancion: " + fileCancion);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                InputStream is = cliente.getInputStream();
                int bytesRead = 0;
                while ((bytesRead = is.read(paquete)) != -1) {
                    System.out.println("bytes read: " + bytesRead);
                    bos.write(paquete, 0, bytesRead);
                }
                bos.flush();
                cerrarConexion();
                return 1;
            } catch (IOException ex) {
                abrirConexion();
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        });
    }

    /**
     * Método para cerrar la conexión con el servidor de Streaming
     */
    public static void cerrarConexion() {
        if (cliente != null) {
            try {
                System.out.println("\nCerrando la conexión...");
                cliente.close();
            } catch (IOException ex) {
                Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
