
package roombaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Clase del servidor para la administración de la información en la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class RoombaServer {

    private static ServerSocket socketServer;
    private static final int PUERTO = 1234;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        
         try {

            socketServer = new ServerSocket(PUERTO);
            System.out.println("Servidor arriba");
        } catch (IOException ex) {
            System.out.println("!Imposible conectarse al puerto!");
            System.exit(1);
        }
        
        do {
            Socket cliente = socketServer.accept();
            System.out.println("Conectado con un RoombaClient");
            
            ManejadorCliente manejadorCliente = new ManejadorCliente(cliente);
            executor.execute(manejadorCliente);
        } while (true);
        
    }

}
