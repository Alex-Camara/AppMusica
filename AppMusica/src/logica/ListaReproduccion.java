/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;

/**
 *
 * @author Alex Cámara
 */
public class ListaReproduccion {
    private int idListaReproduccion;
    private String nombre;
    private List<Cancion> canciones;

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
    
    
}
