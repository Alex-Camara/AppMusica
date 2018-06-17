package roombaserver;

import db.AlbumBD;
import db.AlbumDao;
import db.BibliotecaBD;
import db.BibliotecaDao;
import db.CancionBD;
import db.CancionDao;
import db.GeneroBD;
import db.GeneroDao;
import db.ListasReproduccionBD;
import db.ListasReproduccionDao;
import db.UsuarioBD;
import db.UsuarioDao;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeArray.map;
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Genero;
import logica.ListaReproduccion;
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
    private int idBiblioteca;

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
                    UsuarioDao usuarioServidor = new UsuarioBD();
                    Usuario usuarioObtenido = usuarioServidor.recuperarUsuario(usuarioRecibido.getCorreo(), usuarioRecibido.getClave());
                    salida.writeObject(usuarioObtenido);
                    break;
                case "registrarUsuario":
                    usuarioRecibido = (Usuario) mensajeRecibido.getObjeto();
                    usuarioServidor = new UsuarioBD();
                    
                    usuarioObtenido = usuarioServidor.registrarUsuario(usuarioRecibido);
   
                    salida.writeObject(usuarioObtenido);
                    break;
                case "recuperarCanciones":
                    Usuario usuarioRecibido2 = (Usuario) mensajeRecibido.getObjeto();
                    BibliotecaDao biblioteca = new BibliotecaBD();
                    Biblioteca bibliotecaServidor = biblioteca.recuperarBiblioteca(usuarioRecibido2.getIdBiblioteca());
                    Mensaje mensaje = new Mensaje("canciones");
                    mensaje.setObjeto(bibliotecaServidor);
                    salida.writeObject(mensaje);
                    idBiblioteca = usuarioRecibido2.getIdBiblioteca();

                    List<Album> albumesCanciones = recuperarAlbumes(bibliotecaServidor);
                    mensaje = new Mensaje("albumes");
                    mensaje.setObjeto(albumesCanciones);
                    salida.writeObject(mensaje);

                    List<Genero> generoCanciones = recuperarGeneros(albumesCanciones);
                    mensaje = new Mensaje("generos");
                    mensaje.setObjeto(generoCanciones);
                    salida.writeObject(mensaje);

                    List<ListaReproduccion> listasReproduccion = new ArrayList<>();
                    ListasReproduccionDao listaServidor = new ListasReproduccionBD();
                    listasReproduccion = listaServidor.recuperarListas(bibliotecaServidor.getIdBiblioteca());
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    //System.out.println("listas recuepradas: " + listasReproduccion);
                    //System.out.println("biblioteca: " + bibliotecaServidor.getIdBiblioteca());
                    salida.writeObject(mensaje);
                    break;
                case "recuperarHistorial":
                    Usuario usuarioHistorial = (Usuario) mensajeRecibido.getObjeto();
                    CancionDao cancionesServidor = new CancionBD();
                    List<Cancion> cancionesHistorial = cancionesServidor.recuperarCancionesHistorial(
                            usuarioHistorial.getIdUsuario());
                    mensaje = new Mensaje("historial");
                    mensaje.setObjeto(cancionesHistorial);
                    salida.writeObject(mensaje);
                    break;
                case "recuperarCatalogoGeneros":
                    GeneroDao generoServidor = new GeneroBD();
                    List<Genero> generos = generoServidor.recuperarCatalogo();
                    mensaje = new Mensaje("catalogoGeneros");
                    mensaje.setObjeto(generos);
                    salida.writeObject(mensaje);
                    break;
                case "actualizarCalificación":
                    Cancion cancion = (Cancion) mensajeRecibido.getObjeto();
                    CancionDao cancionServidor = new CancionBD();
                    System.out.println("calificacion a actualizar: " + cancion.getCalificacion());
                    cancionServidor.actualizarCalificacion(cancion.getIdCancion(), cancion.getCalificacion());
                    break;
                case "agregarLista":
                    ListaReproduccion lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.agregarLista(lista, idBiblioteca);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    break;
                case "editarLista":
                    lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.editarLista(lista);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    break;
                case "eliminarLista":
                    lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.eliminarLista(lista);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    break;
                case "agregarCancionALista":
                    ListaReproduccion lista2 = null;
                    HashMap<ListaReproduccion, Cancion> hash = new HashMap<>();
                    hash = (HashMap<ListaReproduccion, Cancion>) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    Map.Entry<ListaReproduccion, Cancion> entry = hash.entrySet().iterator().next();
                    lista2 = entry.getKey();
                    Cancion cancion2 = entry.getValue();
                    listaServidor.agregarCancionALista(cancion2, lista2);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    lista2 = null;
                    break;
                case "recuperarCancionesExternas":
                    cancionesServidor = new CancionBD();
                    List<Cancion> cancionesExternas = cancionesServidor.recuperarCancionesExternas();
                    mensaje = new Mensaje("cancionesExternas");
                    mensaje.setObjeto(cancionesExternas);
                    salida.writeObject(mensaje);
                    
                    albumesCanciones = recuperarAlbumes(cancionesExternas);
                    mensaje = new Mensaje("albumesExternos");
                    mensaje.setObjeto(albumesCanciones);
                    salida.writeObject(mensaje);
                    
                    generoCanciones = recuperarGeneros(albumesCanciones);
                    mensaje = new Mensaje("generosExternos");
                    mensaje.setObjeto(generoCanciones);
                    salida.writeObject(mensaje);
                    break;
                case "cerrarConexión":
                    continuar = false;
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
        System.out.println("Cerrando la conexión con el RoombaClient ...");
        Mensaje mensajeSalida = new Mensaje("cerrarConexión");
        salida.writeObject(mensajeSalida);
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
    
    private List<Album> recuperarAlbumes(List<Cancion> cancionesExternas) throws SQLException {
        List<Album> albumes = new ArrayList<>();
        List<Integer> listaIdAlbumes = new ArrayList<>();
        int idAlbum;

        for (int i = 0; i < cancionesExternas.size(); i++) {
            idAlbum = cancionesExternas.get(i).getAlbum_idAlbum();
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
