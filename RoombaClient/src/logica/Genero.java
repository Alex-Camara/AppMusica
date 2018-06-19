/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;

/**
 * Clase Genero con los atributos homólogos de la base de datos.
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Genero implements Serializable{
    private int idGenero;
    private String nombre;

   public int getIdGenero() {
      return idGenero;
   }

   public String getNombre() {
      return nombre;
   }

   public void setIdGenero(int idGenero) {
      this.idGenero = idGenero;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   @Override
   public String toString() {
      return nombre;
   } 
}
