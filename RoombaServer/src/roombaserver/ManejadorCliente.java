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
import logica.Album;
import logica.Biblioteca;
import logica.Cancion;
import logica.Genero;
import logica.ListaReproduccion;
import logica.conexion.Mensaje;
import logica.Usuario;

/**
 * Clase para interactuar con el cliente de base de datos. Esto es lanzado con
 * cada conexión.
 *
 * @author José Valdivia
 * @author Alejandro Cámara
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

    /**
     * Método para escuhar las solicitudes del cliente. Recibe un objeto Mensaje
     * con la información del tipo de solicitud y en caso de ser necesario con
     * el objeto a utilizar.
     *
     * @return Boolean en caso de no recibir un mensaje de cierre de conexión
     * @throws SQLException En caso de encontrar un fallo en la base de datos
     */
    public boolean escucharMensajes() throws SQLException {
        boolean continuar = true;

        try {
            Mensaje mensajeRecibido = (Mensaje) entrada.readObject();

            String asunto = mensajeRecibido.getAsunto();
            System.out.println("Peticion: " + asunto);
            switch (asunto) {
                case "recuperar usuario":
                    Usuario usuarioRecibido = (Usuario) mensajeRecibido.getObjeto();
                    UsuarioDao usuarioServidor = new UsuarioBD();
                    Usuario usuarioObtenido = usuarioServidor.recuperarUsuario(usuarioRecibido.getCorreo(), usuarioRecibido.getClave());
                    salida.writeObject(usuarioObtenido);
                    salida.flush();
                    break;
                case "registrarUsuario":
                    usuarioRecibido = (Usuario) mensajeRecibido.getObjeto();
                    usuarioServidor = new UsuarioBD();
                    usuarioObtenido = usuarioServidor.registrarUsuario(usuarioRecibido);
                    salida.writeObject(usuarioObtenido);
                    salida.flush();
                    break;
                case "recuperarCanciones":
                    Usuario usuarioRecibido2 = (Usuario) mensajeRecibido.getObjeto();
                    BibliotecaDao biblioteca = new BibliotecaBD();
                    Biblioteca bibliotecaServidor = biblioteca.recuperarBiblioteca(usuarioRecibido2.getIdBiblioteca());
                    Mensaje mensaje = new Mensaje("canciones");
                    mensaje.setObjeto(bibliotecaServidor);
                    salida.writeObject(mensaje);
                    salida.flush();
                    idBiblioteca = usuarioRecibido2.getIdBiblioteca();

                    List<Album> albumesCanciones = recuperarAlbumes(bibliotecaServidor);
                    mensaje = new Mensaje("albumes");
                    mensaje.setObjeto(albumesCanciones);
                    salida.writeObject(mensaje);
                    salida.flush();

                    List<Genero> generoCanciones = recuperarGeneros(albumesCanciones);
                    mensaje = new Mensaje("generos");
                    mensaje.setObjeto(generoCanciones);
                    salida.writeObject(mensaje);
                    salida.flush();

                    List<ListaReproduccion> listasReproduccion = new ArrayList<>();
                    ListasReproduccionDao listaServidor = new ListasReproduccionBD();
                    listasReproduccion = listaServidor.recuperarListas(bibliotecaServidor.getIdBiblioteca());
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "recuperarHistorial":
                    Usuario usuarioHistorial = (Usuario) mensajeRecibido.getObjeto();
                    CancionDao cancionesServidor = new CancionBD();
                    List<Cancion> cancionesHistorial = cancionesServidor.recuperarCancionesHistorial(
                            usuarioHistorial.getIdUsuario());
                    mensaje = new Mensaje("historial");
                    mensaje.setObjeto(cancionesHistorial);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "recuperarCatalogoGeneros":
                    GeneroDao generoServidor = new GeneroBD();
                    List<Genero> generos = generoServidor.recuperarCatalogo();
                    mensaje = new Mensaje("catalogoGeneros");
                    mensaje.setObjeto(generos);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "actualizarCalificación":
                    Integer[] calificacionEnvio = new Integer[2];
                    calificacionEnvio = (Integer[]) mensajeRecibido.getObjeto();
                    CancionDao cancionServidor = new CancionBD();
                    System.out.println("calificacion a actualizar: " + calificacionEnvio[1]);
                    cancionServidor.actualizarCalificacion(calificacionEnvio[0], calificacionEnvio[1]);
                    mensaje = new Mensaje("calificacionGuardada");
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "agregarLista":
                    ListaReproduccion lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.agregarLista(lista, idBiblioteca);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "editarLista":
                    lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.editarLista(lista);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "eliminarLista":
                    lista = (ListaReproduccion) mensajeRecibido.getObjeto();
                    listaServidor = new ListasReproduccionBD();
                    listaServidor.eliminarLista(lista);
                    listasReproduccion = listaServidor.recuperarListas(idBiblioteca);
                    mensaje = new Mensaje("listasReproduccion");
                    mensaje.setObjeto(listasReproduccion);
                    salida.writeObject(mensaje);
                    salida.flush();
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
                    salida.flush();
                    lista2 = null;
                    break;
                case "recuperarCancionesExternas":
                    cancionesServidor = new CancionBD();
                    List<Cancion> cancionesExternas = cancionesServidor.recuperarCancionesExternas();
                    mensaje = new Mensaje("cancionesExternas");
                    mensaje.setObjeto(cancionesExternas);
                    salida.writeObject(mensaje);
                    salida.flush();

                    albumesCanciones = recuperarAlbumes(cancionesExternas);
                    mensaje = new Mensaje("albumesExternos");
                    mensaje.setObjeto(albumesCanciones);
                    salida.writeObject(mensaje);
                    salida.flush();

                    generoCanciones = recuperarGeneros(albumesCanciones);
                    mensaje = new Mensaje("generosExternos");
                    mensaje.setObjeto(generoCanciones);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "guardarCanciones":
                    List<Cancion> cancionesAGuardar = (List<Cancion>) mensajeRecibido.getObjeto();
                    System.out.println("canciones: " + cancionesAGuardar);
                    guardarCanciones(cancionesAGuardar);
                    break;
                case "hacerCreador":
                    Usuario usuario = (Usuario) mensajeRecibido.getObjeto();
                    usuarioServidor = new UsuarioBD();
                    usuarioServidor.actualizarUsuario(usuario.getIdUsuario(), usuario.getNombreArtistico());
                    break;
                case "cerrarConexión":
                    continuar = false;
                    break;
                case "agregarABiblioteca":
                    Cancion cancion = (Cancion) mensajeRecibido.getObjeto();
                    cancionesServidor = new CancionBD();
                    cancionesServidor.agregarCancionABiblioteca(idBiblioteca, cancion);
                    mensaje = new Mensaje("cancionAgregadaABiblioteca");
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "eliminarCancion":
                    int idCancion = (Integer) mensajeRecibido.getObjeto();
                    cancionesServidor = new CancionBD();
                    cancionesServidor.eliminarCancionLocal(idBiblioteca, idCancion);
                    mensaje = new Mensaje("cancionEliminada");
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                case "crearRadio":
                    int idGenero = (Integer) mensajeRecibido.getObjeto();

                    List<Album> albumes = recuperarAlbumesPorGenero(idGenero);
                    List<Cancion> cancionesRadio = recuperarCancionesAlbum(albumes);
                    
                    mensaje = new Mensaje("cancionesRadio");
                    mensaje.setObjeto(cancionesRadio);
                    salida.writeObject(mensaje);
                    salida.flush();
                    break;
                default:
                    break;
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ManejadorCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return continuar;
    }

    /**
     * Método para cerrar la conexión con el cliente.
     *
     * @throws IOException En caso de no poder hacerlo
     */
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

    private List<Album> recuperarAlbumesPorGenero(int idGenero) throws SQLException {
        List<Album> albumes = new ArrayList<>();
        AlbumDao albumServidor = new AlbumBD();
        albumes = albumServidor.recuperarAlbumGenero(idGenero);
        return albumes;
    }

    public List<Cancion> recuperarCancionesAlbum(List<Album> albumes) throws SQLException {
        List<Cancion> canciones = new ArrayList<>();
        CancionDao cancionesServidor = new CancionBD();
        for (int i = 0; i < albumes.size(); i++) {
            List<Cancion> cancionesTemp = cancionesServidor.recuperarCancionesAlbum(albumes.get(i).getIdAlbum());
            canciones.addAll(cancionesTemp);
        }
        return canciones;
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

    private void guardarCanciones(List<Cancion> cancionesAGuardar) throws SQLException {
        for (int i = 0; i < cancionesAGuardar.size(); i++) {
            Cancion cancion = cancionesAGuardar.get(i);

            AlbumDao albumServidor = new AlbumBD();
            Album albumGuardado = albumServidor.guardarAlbum(cancion.getAlbum());
            cancion.setAlbum_idAlbum(albumGuardado.getIdAlbum());
            CancionDao cancionServidor = new CancionBD();
            boolean privada = true;
            cancionServidor.guardarCancion(idBiblioteca, cancion, privada);
        }
    }
}
