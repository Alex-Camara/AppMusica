package logica.conexion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class ClienteStreaming implements Runnable {
   
   //Este es un caso en el cual se utiliza bajo un hilo

   private static InetAddress host;
   private int port;
   private SourceDataLine sLine;
   private AudioFormat audioFormat;
   /*Esto me ha dado muchas vueltas. He leído en algunos lados que 2200 debería ser lo mejor porque 
   el tamaño del buffer no es tan grande ni pequeño. En otro lados dicen que debería ser mejor usar 
   un buffer de 4096 para que tenga la suficiente información el buffer para reproducir y otro dicen
   que debería ser mejor uno de 1024 porque en caso de ser un archivo pequeño podría tardar en llenar
   un buffer grande. He probado las 3 y no he escuchado diferencias :l
   */
   private static byte[] buffer = new byte[2200];
   private static DatagramPacket packet;

   ClienteStreaming(String host, int port) throws UnknownHostException {
      this.host = InetAddress.getByName(host);
      this.port = port;
      init();
      
   }
   //Se encarga de inicializar las variables para la salida del audio
   public void init() {
      audioFormat = new AudioFormat(16000, 16, 2, true, false);
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

      try {
         System.out.println(info);
         sLine = (SourceDataLine) AudioSystem.getLine(info);
         System.out.println(sLine.getLineInfo() + " - sample rate : " + audioFormat.getSampleRate());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public void run() {
      //Inicia el cliente. Abre la linea del audio
      System.out.println("Client started");
      try {
         sLine.open(audioFormat);
      } catch (Exception e) {
         e.printStackTrace();
      }
      sLine.start();
      System.out.println("Line started");
      //Envía la ruta de la canción. Esta deberá ser pasada como parámetro pero por ahora es fija
      try {
         String ruta = "Yeah Yeah Yeahs/Fever/maps.mp3";
         DatagramSocket client = new DatagramSocket();
         DatagramPacket paqueteSalida = new DatagramPacket(ruta.getBytes(), ruta.length(), host, port);
         //Envía un datagrama con la ruta de la canción
         client.send(paqueteSalida);
         do {
            try {
               //Espera el flujo de información 
               packet = new DatagramPacket(buffer, buffer.length);
               client.receive(packet);
               //Pasa la información cruda (bytes[]) a un buffer
               buffer = packet.getData();
               System.out.println("obtuvo");

               
               //Toma la información del buffer y la reproduce
               sLine.write(buffer, 0, buffer.length);
            } catch (UnknownHostException e) {
               e.printStackTrace();
            } catch (IOException e) {
               e.printStackTrace();
            }
         } while(packet != null);

      } catch (SocketException e) {
         e.printStackTrace();
      } catch (IOException ex) {
         Logger.getLogger(ClienteStreaming.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

   public static void main(String[] args) throws UnknownHostException {
      //Inicia la ejecución del hilo
      Thread task = new Thread(new ClienteStreaming("127.0.0.1", 1234));
      task.run();
   }
}
