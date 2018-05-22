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
public class Album implements Serializable {
    private int idAlbum;
    private String nombre;
    private String estudio;
    private int anioLanzamiento;
    private int idUsuario;
    private Genero genero;

   public int getIdAlbum() {
      return idAlbum;
   }

   public String getNombre() {
      return nombre;
   }

   public String getEstudio() {
      return estudio;
   }

   public int getAnioLanzamiento() {
      return anioLanzamiento;
   }

   public int getIdUsuario() {
      return idUsuario;
   }

   public Genero getGenero() {
      return genero;
   }

   public void setIdAlbum(int idAlbum) {
      this.idAlbum = idAlbum;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public void setEstudio(String estudio) {
      this.estudio = estudio;
   }

   public void setAnioLanzamiento(int anioLanzamiento) {
      this.anioLanzamiento = anioLanzamiento;
   }

   public void setIdUsuario(int idUsuario) {
      this.idUsuario = idUsuario;
   }

   public void setGenero(Genero genero) {
      this.genero = genero;
   }
    
    
}
