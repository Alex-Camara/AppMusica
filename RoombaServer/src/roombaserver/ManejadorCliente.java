package roombaserver;

import db.AlbumBD;
import db.AlbumDao;
import db.BibliotecaBD;
import db.BibliotecaDao;
import db.CancionBD;
import db.CancionDao;
import db.GeneroBD;
import db.GeneroDao;
import db.UsuarioBD;
import db.UsuarioDao;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Genero;
import logica.conexion.Mensaje;
import logica.Usuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alex Cámara
 */
public class ManejadorCliente extends Thread {

   private final Socket cliente;
   private ObjectInputStream entrada;
   private ObjectOutputStream salida;

   public ManejadorCliente(Socket socket) {
      this.cliente = socket;
      try {
         salida = new ObjectOutputStream(cliente.getOutputStream());
         entrada = new ObjectInputStream(cliente.getInputStream());
      } catch (IOException ex) {
         Logger.getLogger(ManejadorCliente.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   @Override
   public void run() {
      boolean continuar = true;
      do {
         try {
            continuar = escucharMensajes();
         } catch (SQLException ex) {
            Logger.getLogger(ManejadorCliente.class.getName()).log(Level.SEVERE, null, ex);
         }
      } while (continuar);

      try {
         cerrarConexión();
      } catch (IOException ex) {
         Logger.getLogger(ManejadorCliente.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public boolean escucharMensajes() throws SQLException {
      boolean continuar = true;

      try {
         Mensaje mensajeRecibido = (Mensaje) entrada.readObject();

         String asunto = mensajeRecibido.getAsunto();
         System.out.println("asun " + asunto);
         switch (asunto) {
            case "recuperar usuario":
               Usuario usuarioRecibido = (Usuario) mensajeRecibido.getObjeto();
               UsuarioDao usuario = new UsuarioBD();
               Usuario usuarioObtenido = usuario.recuperarUsuario(usuarioRecibido.getCorreo(), usuarioRecibido.getClave());
               salida.writeObject(usuarioObtenido);
               break;
            case "cancionesServidor":
               BibliotecaDao biblioteca = new BibliotecaBD();
               Biblioteca bibliotecaServ = biblioteca.recuperarBilioteca();
               Mensaje mensaje1 = new Mensaje("canciones");
               mensaje1.setObjeto(bibliotecaServ);
               salida.writeObject(mensaje1);
               
               List<Album> albumesCanciones = recuperarAlbumes(bibliotecaServ);
               Mensaje mensaje2 = new Mensaje("albumes");
               mensaje2.setObjeto(albumesCanciones);
               salida.writeObject(mensaje2);
               
               List<Genero> generoCanciones = recuperarGeneros(albumesCanciones);
               Mensaje mensaje3 = new Mensaje("generos");
               mensaje3.setObjeto(generoCanciones);
               salida.writeObject(mensaje3);
               break;
            case "recuperarHistorial":
               Usuario usuarioHistorial = (Usuario)mensajeRecibido.getObjeto();
               CancionDao canciones = new CancionBD();
               List<Cancion> cancionesHistorial = canciones.recuperarCancionesHistorial(
                   usuarioHistorial.getIdUsuario());
               Mensaje mensaje4 = new Mensaje("historial");
               mensaje4.setObjeto(cancionesHistorial);
               salida.writeObject(mensaje4);
               break;
            case "recuperarCatalogoGeneros":
               GeneroDao genero = new GeneroBD();
               List<Genero> generos = genero.recuperarCatalogo();
               Mensaje mensaje5 = new Mensaje("catalogoGeneros");
               mensaje5.setObjeto(generos);
               salida.writeObject(mensaje5);
            default:
               break;
         }
      } catch (IOException | ClassNotFoundException ex) {
         Logger.getLogger(ManejadorCliente.class.getName()).log(Level.SEVERE, null, ex);
      }
      return continuar;
   }

   public void cerrarConexión() throws IOException {
      System.out.println("Cerrando la conexión con el cliente ...");
      cliente.close();
   }

   private List<Album> recuperarAlbumes(Biblioteca bibliotecaServ) throws SQLException {
      List<Album> albumes = new ArrayList<>();
      List<Integer> listaIdAlbumes = new ArrayList<>();
      int idAlbum;

      for (int i = 0; i < bibliotecaServ.getCanciones().size(); i++) {
         idAlbum = bibliotecaServ.getCanciones().get(i).getAlbum_idAlbum();
         if (!listaIdAlbumes.contains(idAlbum)) {

            listaIdAlbumes.add(idAlbum);
            AlbumDao album = new AlbumBD();
            Album albumServ = album.recuperarAlbumes(idAlbum);
            albumes.add(albumServ);
         }
      }
      return albumes;
   }

   private List<Genero> recuperarGeneros(List<Album> albumesCanciones) throws SQLException {
      List<Genero> generos = new ArrayList<>();
      List<Integer> listaIdGeneros = new ArrayList<>();
      int idGenero;
      for (int i = 0; i < albumesCanciones.size(); i++) {
         idGenero = albumesCanciones.get(i).getIdGenero();
         if (!listaIdGeneros.contains(idGenero)) {
            GeneroDao genero = new GeneroBD();
            Genero generoServ = genero.recuperarGenero(idGenero);
            generos.add(generoServ);
            listaIdGeneros.add(idGenero);   
         }
      }
      return generos;
   }
}
