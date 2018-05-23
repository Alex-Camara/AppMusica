package roombaserver;

import db.AlbumBD;
import db.AlbumDao;
import db.BibliotecaBD;
import db.BibliotecaDao;
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
                    Mensaje mensaje1 = new Mensaje("cancionesServidor");
                    mensaje1.setObjeto(bibliotecaServ);
                    salida.writeObject(mensaje1);

                    List<Album> albumes = new ArrayList<>();
                    List<Integer> listaIdAlbumes = new ArrayList<>();

                    for (int i = 0; i < bibliotecaServ.getCanciones().size(); i++) {
                        int idAlbum = bibliotecaServ.getCanciones().get(i).getAlbum_idAlbum();
                        if (!listaIdAlbumes.contains(idAlbum)) {

                            listaIdAlbumes.add(idAlbum);
                            AlbumDao album = new AlbumBD();
                            Album albumServ = album.recuperarAlbumes(idAlbum);
                            albumes.add(albumServ);
                        }
                    }
                    Mensaje mensaje2 = new Mensaje("albumesServidor");
                    mensaje2.setObjeto(albumes);
                    salida.writeObject(mensaje2);
                    break;
                case "recuperar albumes personales":

                    break;
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
}
