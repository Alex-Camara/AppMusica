/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.conexion;

import java.io.Serializable;

/**
 *
 * @author Alex Cámara
 */
public class Mensaje implements Serializable {

   private String asunto;
   private Object objeto;

   public Mensaje() {
   }
   
   public Mensaje(String asunto) {
      this.asunto = asunto;
   }

   public String getAsunto() {
      return asunto;
   }

   public void setAsunto(String asunto) {
      this.asunto = asunto;
   }

   public Object getObjeto() {
      return objeto;
   }

   public void setObjeto(Object objeto) {
      this.objeto = objeto;
   }

}
