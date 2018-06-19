
package logica;

import java.io.Serializable;

/**
 * Clase Cancion con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Cancion implements Serializable {

    private int idCancion;
    private String nombre;
    private double duracion;
    private String artista;
    private int calificacion;
    private int album_idAlbum;
    private String ruta;
    private Album album;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getAlbum_idAlbum() {
        return album_idAlbum;
    }

    public void setAlbum_idAlbum(int album_idAlbum) {
        this.album_idAlbum = album_idAlbum;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String toString() {
        return nombre;
    }

}
