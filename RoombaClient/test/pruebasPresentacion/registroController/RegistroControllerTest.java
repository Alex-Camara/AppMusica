
package pruebasPresentacion.registroController;

import org.junit.Assert;
import org.junit.Test;
import presentacion.IGURegistroController;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class RegistroControllerTest {
   IGURegistroController registroController = new IGURegistroController();
   
   public RegistroControllerTest() {
   }
   
   @Test
   public void testCamposVacios(){
      String nombre = "José";
      String paterno = "Petrikowski";
      String correo = "ale@camara.com";
      String clave = "1234";
      String confirmacion = "1234";
      Boolean check = false;
      Boolean vacios = registroController.camposVacios(nombre, paterno, correo, clave, confirmacion);
       Assert.assertEquals(vacios, check);
      confirmacion = " ";
      check = true;
      vacios = registroController.camposVacios(nombre, paterno, correo, clave, confirmacion);
       Assert.assertEquals(vacios, check);
   }
   @Test
   public void nombreCorrecto(){
      /**
       * Para realizar este test es necesario deshabilitar los elementos gráficos del método
       */
      String name =  "JOSE";
      Boolean check = true;
      Boolean correcto = registroController.nombreCorrecto(name);
      Assert.assertEquals(correcto, check);
      name =  "JOSE#";
      check = false;
      correcto = registroController.nombreCorrecto(name);
      Assert.assertEquals(correcto, check);
   }
   @Test
   public void validarFormatoCorreo(){
      String correo =  "jose@hotmail.com";
      Boolean check = true;
      Boolean correcto = registroController.validarFormatoCorreo(correo);
      Assert.assertEquals(correcto, check);
      correo =  "JOSE#cksk";
      check = false;
      correcto = registroController.validarFormatoCorreo(correo);
      Assert.assertEquals(correcto, check);
   }
   @Test
   public void confirmacionContraseñaCorrecta(){
      String clave =  "claveSUPER";
      String confirmacionClave = "claveSUPER";
      Boolean check = true;
      Boolean correcto = registroController.confirmacionContraseñaCorrecta(clave, confirmacionClave);
      Assert.assertEquals(correcto, check);
      clave =  "claveSUPER";
      confirmacionClave = "CLAVE";
      check = false;
      correcto = registroController.confirmacionContraseñaCorrecta(clave, confirmacionClave);
      Assert.assertEquals(correcto, check);
   }
   @Test
   public void contraseñaCorrecta(){
      /**
       * Para realizar este test es necesario deshabilitar los elementos gráficos del método
       */
      String clave =  "claveSUPER";
      Boolean check = true;
      Boolean correcto = registroController.contraseñaCorrecta(clave);
      Assert.assertEquals(correcto, check);
      clave =  "c";
      check = false;
      correcto = registroController.contraseñaCorrecta(clave);
      Assert.assertEquals(correcto, check);
   }
   @Test
   public void maternoCorrecto(){
      /**
       * Para realizar este test es necesario deshabilitar los elementos gráficos del método
       */
      String materno =  "Hernandez";
      Boolean check = true;
      Boolean correcto = registroController.maternoCorrecto(materno);
      Assert.assertEquals(correcto, check);
      materno =  "Hernand#z";
      check = false;
      correcto = registroController.maternoCorrecto(materno);
      Assert.assertEquals(correcto, check);
   }
   @Test
   public void paternoCorrecto(){
      /**
       * Para realizar este test es necesario deshabilitar los elementos gráficos del método
       */
      String paterno =  "Hernandez";
      Boolean check = true;
      Boolean correcto = registroController.paternoCorrecto(paterno);
      Assert.assertEquals(correcto, check);
      paterno =  "Hernand#z";
      check = false;
      correcto = registroController.maternoCorrecto(paterno);
      Assert.assertEquals(correcto, check);
   }
   
   
}
