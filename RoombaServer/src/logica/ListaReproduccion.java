
package logica;

import java.io.Serializable;
import java.util.List;

/**
 * Clase ListaReproduccion con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class ListaReproduccion implements Serializable{
    private int idListaReproduccion;
    private int idBiblioteca;
    private String nombre;
    private List<Cancion> canciones;

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public int getIdListaReproduccion() {
        return idListaReproduccion;
    }

    public void setIdListaReproduccion(int idListaReproduccion) {
        this.idListaReproduccion = idListaReproduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
