/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;

/**
 *
 * @author Alex Cámara
 */
public class Cancion implements Serializable {
    private int idCancion;
    private String nombre;
    private double duracion;
    private String artista;
    private int calificacion;
    private int album_idAlbum;
    private String ruta;

   public int getIdCancion() {
      return idCancion;
   }

   public String getNombre() {
      return nombre;
   }

   public double getDuracion() {
      return duracion;
   }

   public String getArtista() {
      return artista;
   }

   public int getCalificacion() {
      return calificacion;
   }

   public int getAlbum_idAlbum() {
      return album_idAlbum;
   }

   public String getRuta() {
      return ruta;
   }

   public void setIdCancion(int idCancion) {
      this.idCancion = idCancion;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public void setDuracion(double duracion) {
      this.duracion = duracion;
   }

   public void setArtista(String artista) {
      this.artista = artista;
   }

   public void setCalificacion(int calificacion) {
      this.calificacion = calificacion;
   }

   public void setAlbum_idAlbum(int album_idAlbum) {
      this.album_idAlbum = album_idAlbum;
   }

   public void setRuta(String ruta) {
      this.ruta = ruta;
   }



    
    
}
