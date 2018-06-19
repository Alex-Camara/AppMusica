
package logica.conexion;

import java.io.Serializable;

/**
 * Clase de Mensaje como objeto para interactuar con el cliente de base de datos
 * @author José Valdivia
 * @author Alejandro Cámara
 */
public class Mensaje implements Serializable{
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
