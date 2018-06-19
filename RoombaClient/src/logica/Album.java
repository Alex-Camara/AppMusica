/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;

/**
 * Clase Album con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Album implements Serializable {
    private int idAlbum;
    private String nombre;
    private String estudio;
    private int anioLanzamiento;
    private int idUsuario;
    private int idGenero;

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

   public int getIdGenero() {
      return idGenero;
   }

   public void setIdGenero(int idGenero) {
      this.idGenero = idGenero;
   }
      
    @Override
   public String toString(){
       return nombre;
   }
  
}
