package logica.conexion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author javr
 */
public class ClienteStreaming2 {
   
   //Este caso utiliza las acciones de manera modular. No utiliza un hilo sino un ciclo 

   private static InetAddress host;
   private static final int BUFFER_SIZE = 2200;
   private static final int PUERTO = 1234;
   private static DatagramSocket socketDatagrama;
   private static DatagramPacket paqueteEntrada, paqueteSalida;
   private static byte[] buffer = new byte[BUFFER_SIZE];
   private static final AudioFormat FORMAT = getAudioFormat();
   private static SourceDataLine sourceSpeakerLine;
   //Realmente no la abre. Solo asigna el host
   public static void abrirConexion() {
      try {
         host = InetAddress.getByName("127.0.0.1");
         System.out.println("host: " + host);
      } catch (UnknownHostException ex) {
         System.out.println("\n!ID de host no encontrado¡\n");
         System.exit(1);
      }
   }

   public static void solicitarCancion(String ruta) {
      try {
         //Crea el datagrama y lo llena con los bytes del nombre de la ruta de la canción
         socketDatagrama = new DatagramSocket();
         paqueteSalida = new DatagramPacket(ruta.getBytes(), ruta.length(), host, PUERTO);
         //Envía el datagrama
         socketDatagrama.send(paqueteSalida);
         System.out.println("Solicitada");
         
         //Configura la linea para la salida el audio, con un formato y un buffer fijo
         DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, FORMAT);
         sourceSpeakerLine = (SourceDataLine) AudioSystem.getLine(speakerInfo);
         sourceSpeakerLine.open(FORMAT, BUFFER_SIZE);
         sourceSpeakerLine.start();
         System.out.println("Información de la fuente de audio:\n"+sourceSpeakerLine.getLineInfo() +
             " - Rate : " + FORMAT.getSampleRate()); 
      } catch (SocketException ex) {
         Logger.getLogger(ClienteStreaming2.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException | LineUnavailableException ex) {
         Logger.getLogger(ClienteStreaming2.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public static byte[] recuperarCancion() {
      try {
         //Crea un datagrama en blanco y lo rellena con la infromación que obtiene del flujo
         paqueteEntrada = new DatagramPacket(buffer, buffer.length);
         socketDatagrama.receive(paqueteEntrada);
         //Regresa la información el crudo (bytes[])
         return paqueteEntrada.getData();
      } catch (IOException ex) {
         Logger.getLogger(ClienteStreaming2.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
   }

   public static void reproducir(byte data[]) {
      try {
         //En este caso se crea un array de InputStream para crear un AudioInputStream.
         //En la otra clase que hice no añadí estas variables porque no creaban diferencia en el sonido
         //aunque se supone que deberían estar 
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         AudioInputStream ais = new AudioInputStream(bais, FORMAT, data.length);
         int bytesRead = 0;
         //Lee el flujo mientras haya información
         if ((bytesRead = ais.read(data)) != -1) {
            System.out.println("Obtuvo de :" + bytesRead);
            //Pasa la información en crudo a la linea de salida de audio
            //Aquí en donde pensé que no era encesario hacer el bais y AIS porque solo se usan para 
            //verificar que exista información nunca se pasan a la línea y no se pueden pasar porque
            //la línea necesita un byte[]
            sourceSpeakerLine.write(data, 0, bytesRead);
         }

         //ais.close();
         //bais.close();

      } catch (Exception e) {
         System.out.println("not working in speakers ");
      }
      //sourceSpeakerLine.drain();
      //sourceSpeakerLine.close();
   }

   public static AudioFormat getAudioFormat() {
      float sampleRate = 16000.0F;
      //8000,11025,16000,22050,44100
      int sampleSizeInBits = 16;
      //8,16
      int channels = 1;
      //1,2
      boolean signed = true;
      //true,false
      boolean bigEndian = false;
      //true,false
      return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
   }

   public static void cerrarConexion() {
      System.out.println("\nCerrando la conexión...");
      socketDatagrama.close();
      System.exit(1);

   }

   public static void main(String[] args) {
      abrirConexion();
      solicitarCancion("Yeah Yeah Yeahs/Fever/maps.mp3");
      while (true) {
         reproducir(recuperarCancion());
      }

   }

}

