
package logica;

import java.io.Serializable;
import java.util.List;

/**
 * Clase Biblioteca con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Biblioteca implements Serializable{
    private int idBiblioteca;
    private List<Cancion> canciones;

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
    
    
}
