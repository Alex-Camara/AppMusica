/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombaServer.usuarioBD;

import db.UsuarioBD;
import java.sql.SQLException;
import logica.Usuario;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Valdivia
 * @author Alex Cámara
 */
public class UsuarioBDTest {
   UsuarioBD usuarioBD = new UsuarioBD();
   
   public UsuarioBDTest() {
   }
   
   @Test
   public void registrarUsuario() throws SQLException{
      /**
       * Este test incluye a recuperarUsuario el cual está incluido en el método
       */
      String correo = "1";
      String clave = "12";
      Usuario check = new Usuario();
      check.setCorreo(correo);
      check.setClave(clave);
      check.setIdBiblioteca(1);
      check.setIdUsuario(1);
      check.setMaterno("Ruiz");
      check.setNombre("José Alí");
      check.setPaterno("Valdivia");
      Usuario usuario = usuarioBD.registrarUsuario(check);
      Assert.assertEquals(check, usuario);  
   }
   @Test
   public void actualizarUsuario() throws SQLException{

      int check = usuarioBD.actualizarUsuario(1, "21JUN");
      Assert.assertEquals(1, check);  
   }
   
}
