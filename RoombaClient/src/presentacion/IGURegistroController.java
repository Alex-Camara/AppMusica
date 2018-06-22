
package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import logica.Usuario;
import static logica.conexion.Cliente.enviarUsuario;
import static logica.conexion.Cliente.iniciarConversacion;
import logica.conexion.Mensaje;
import presentacion.Utileria.Emergente;

/**
 * Clase del controlador para registrar un nuevo usuario.
 * @author José Valdivia
 * @author Alejandro Cámara
 * */
public class IGURegistroController implements Initializable {

    @FXML
    private JFXTextField tFieldNombre;
    @FXML
    private JFXTextField tFieldPaterno;
    @FXML
    private JFXTextField tFieldMaterno;
    @FXML
    private JFXTextField tFieldCorreo;
    @FXML
    private JFXPasswordField tFieldContraseña;
    @FXML
    private JFXPasswordField tFieldConfirmacion;
    @FXML
    private JFXButton butttonRegistrar;

    private static final String SPECIAL_CHARACTERS = "hasSpecialCharacters";
    private static final String WRONG_LENGHT = "lenghtIsWrong";
    private static final String NO_ERROR = "noError";
    private static final String WRONG_FIELD_STYLE = "-fx-prompt-text-fill: orange; -fx-text-fill: black";
    private static final String DEFAULT_PASSWORD_STYLE = "-fx-prompt-text-fill: black; -fx-text-fill: black";
    private static final String PASSWORD_FIELD_FOCUS = "#77d2ff";
    private static final String FOCUS_COLOR = "orange";
    private static final String UNFOCUS_COLOR = "#e0def5";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }

    @FXML
    void verificarCampos(KeyEvent event) {
        String nombre;
        String paterno;
        String materno;
        String correo;
        String contraseña;
        String confirmacion;

        nombre = tFieldNombre.getText();
        paterno = tFieldPaterno.getText().toLowerCase();
        materno = tFieldMaterno.getText().toLowerCase();
        correo = tFieldCorreo.getText();
        contraseña = tFieldContraseña.getText();
        confirmacion = tFieldConfirmacion.getText();

        boolean estaVacio = camposVacios(nombre, paterno, correo, contraseña, confirmacion);
        boolean nombreCorrecto = nombreCorrecto(nombre);
        boolean paternoCorrecto = paternoCorrecto(paterno);
        boolean maternoCorrecto = maternoCorrecto(materno);
        boolean correoCorrecto = correoCorrecto(correo);
        boolean contraseñaCorrecta = contraseñaCorrecta(contraseña);
        boolean confirmacionContraseñaCorrecta = confirmacionContraseñaCorrecta(contraseña, confirmacion);

        if (!estaVacio && nombreCorrecto && paternoCorrecto && maternoCorrecto && correoCorrecto
                && contraseñaCorrecta && confirmacionContraseñaCorrecta) {
            butttonRegistrar.setDisable(false);
        } else {
            butttonRegistrar.setDisable(true);
        }
    }

    @FXML
    void registrar(ActionEvent event) {
        try {
            String nombre;
            String paterno;
            String materno;
            String correo;
            String contraseña;
            String confirmacion;
            
            nombre = tFieldNombre.getText();
            paterno = tFieldPaterno.getText().toLowerCase();
            materno = tFieldMaterno.getText().toLowerCase();
            correo = tFieldCorreo.getText();
            contraseña = tFieldContraseña.getText();
            confirmacion = tFieldConfirmacion.getText();
            
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setPaterno(paterno);
            nuevoUsuario.setMaterno(materno);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setClave(contraseña);
            
            Mensaje mensaje = new Mensaje();
            mensaje.setAsunto("registrarUsuario");
            mensaje.setObjeto(nuevoUsuario);
            iniciarConversacion();
            Usuario usuarioRecibido = (Usuario) enviarUsuario(mensaje);
            System.out.println("usuario recibido: " + usuarioRecibido.getNombre());
            
            Stage mainWindow = (Stage) tFieldCorreo.getScene().getWindow();
            IGUBibliotecaController controlador = new IGUBibliotecaController();
            controlador.abrirIGUBiblioteca(mainWindow, usuarioRecibido);
        } catch (IOException ex) {
            Logger.getLogger(IGURegistroController.class.getName()).log(Level.SEVERE, null, ex);
            Emergente.cargarEmergente("Error", "Sin servicio, intenta más tarde");
        }
    }

   /**
    * Método para verificar que no sean variables vacías.
    * @param nombre String del nombre del usuario
    * @param paterno String del apellido del usuario
    * @param correo String del correo del usuario
    * @param clave String de la contraseña del usuario
    * @param confirmacion String de la confirmación de la contraseña del usuario
    * @return Boolean de la completud
    */
   public boolean camposVacios(String nombre, String paterno, String correo, String clave, String confirmacion) {
        boolean isEmpty = true;
        if (!nombre.trim().isEmpty() && !paterno.trim().isEmpty() && !correo.trim().isEmpty()
                && !clave.trim().isEmpty() && !confirmacion.trim().isEmpty()) {
            isEmpty = false;
        }
        return isEmpty;
    }

   /**
    * Método para verificar que el nombre tenga una longitud sufiente, no tenga caracteres especiales
    * o mostrar el fallo
    * @param name String del nombre
    * @return Boolean del cumpliento de las reglas
    */
   public boolean nombreCorrecto(String name) {
        boolean nameIsReady = false;
        String result = verificarCadena(name);

        switch (result) {
            case SPECIAL_CHARACTERS:
                showTextFieldMessage("Nombres (No puedes ingresar caracteres especiales)", tFieldNombre);
                break;
            case WRONG_LENGHT:
                showTextFieldMessage("Nombres (Longitud inválida)", tFieldNombre);
                break;
            default:
                resetTextFieldMessage("Nombres", tFieldNombre);
                nameIsReady = true;
                break;
        }
        return nameIsReady;
    }
    /**
    * Método para verificar que el nombre tenga una longitud sufiente, no tenga caracteres especiales
    * o mostrar el fallo
    * @param paterno String del apellido paterno
    * @return Boolean del cumpliento de las reglas
    */
    public boolean paternoCorrecto(String paterno) {
        boolean nameIsReady = false;
        String result = verificarCadena(paterno);

        switch (result) {
            case SPECIAL_CHARACTERS:
                showTextFieldMessage("Paterno (No puedes ingresar caracteres especiales)", tFieldPaterno);
                break;
            case WRONG_LENGHT:
                showTextFieldMessage("Paterno (Longitud inválida)", tFieldPaterno);
                break;
            default:
                resetTextFieldMessage("Paterno", tFieldPaterno);
                nameIsReady = true;
                break;
        }
        return nameIsReady;
    }
    /**
    * Método para verificar que el nombre tenga una longitud sufiente, no tenga caracteres especiales
    * o mostrar el fallo
    * @param materno String del apellido materno
    * @return Boolean del cumpliento de las reglas
    */
    public boolean maternoCorrecto(String materno) {
        boolean nameIsReady = false;
        String result = verificarCadena(materno);

        switch (result) {
            case SPECIAL_CHARACTERS:
                showTextFieldMessage("Materno (No puedes ingresar caracteres especiales)", tFieldMaterno);
                break;
            default:
                resetTextFieldMessage("Materno", tFieldMaterno);
                nameIsReady = true;
                break;
        }
        return nameIsReady;
    }

   /**
    * Método para verificar que la cadena de texto no contena caracteres especiales.
    * @param cadena String de la cadena a verificar
    * @return Boolean de la ausencia de caracteres especiales
    */
   public String verificarCadena(String cadena) {
        String estatus = NO_ERROR;
        boolean hasNameSpecialCharacters = aplicarExpresionRegular(cadena, "^[\\p{L} .'-]+$");
        if (!hasNameSpecialCharacters) {
            estatus = SPECIAL_CHARACTERS;
        }
        boolean isLenghtOk = verificarLongitud(cadena, 2, 30);
        if (!isLenghtOk) {
            estatus = WRONG_LENGHT;
        }
        return estatus;
    }

   /**
    * Método para verificar que el correo electrónico mantenga un formato correcto o mostrar el fallo
    * @param email String del correo electrónico
    * @return Boolean del cumplimiento del formato
    */
   public boolean correoCorrecto(String email) {
        boolean emailIsReady = false;

        String result = verificarCorreo(email);

        switch (result) {
            case "invalidFormat":
                showTextFieldMessage("Correo (Formato inválido)", tFieldCorreo);
                break;
            case WRONG_LENGHT:
                showTextFieldMessage("Correo (Longitud inválida)", tFieldCorreo);
                break;
            default:
                resetTextFieldMessage("Correo", tFieldCorreo);
                emailIsReady = true;
                break;
        }
        return emailIsReady;
    }

   /**
    * Método para verificar que la contraseña cumpla con longitud correcta o mostrar el fallo
    * @param contraseña String de la contraseña
    * @return Boolean del cumpliento de la longitud
    */
   public boolean contraseñaCorrecta(String contraseña) {
        boolean passwordIsReady = false;

        String result = verificarCadena(contraseña);

        switch (result) {
            case WRONG_LENGHT:
                showPasswordMessage("Contraseña (Longitud incorrecta)", tFieldContraseña);
                break;
            default:
                resetPasswordMessage("Contraseña", tFieldContraseña);
                passwordIsReady = true;
                break;
        }
        return passwordIsReady;
    }

   /**
    * Método para verificar que la confirmación de la contraseña sea correcta o mostrar el fallo
    * @param contraseña String de la contraseña
    * @param confirmacion String de la confirmación de la contraseña
    * @return Boolean del cumplimiento de la conincidencia
    */
   public boolean confirmacionContraseñaCorrecta(String contraseña, String confirmacion) {
        boolean estatusConfirmacion = false;

        if (contraseña.equals(confirmacion)) {
            estatusConfirmacion = true;
        }
        if (estatusConfirmacion) {
            resetPasswordMessage("Confirma tu contraseña", tFieldConfirmacion);
        } else {
            showPasswordMessage("Confirma tu contraseña (No coincide la contraseña)", tFieldConfirmacion);
        }
        return estatusConfirmacion;
    }

   /**
    * Método para verificar que el correo electrónico cumpla con la longitud y formato
    * @param email
    * @return Boolean del cumplimiento de las reglas
    */
   public String verificarCorreo(String email) {
        String estatusCorreo = NO_ERROR;

        boolean formatIsOk = validarFormatoCorreo(email);
        if (!formatIsOk) {
            estatusCorreo = "invalidFormat";
        }

        boolean isLenghtOk = verificarLongitud(email, 7, 30);
        if (!isLenghtOk) {
            estatusCorreo = WRONG_LENGHT;
        }
        return estatusCorreo;
    }

   /**
    * Método para verificar la longitud de un arreglo de caracteres.
    * @param field String a verificar
    * @param minimo int del mínimo que debe cumplir
    * @param maximo int del máximo permitido
    * @return
    */
   public static boolean verificarLongitud(String field, int minimo, int maximo) {
        boolean isLenghtOk = false;
        int lenght = field.length();
        if (lenght <= maximo && lenght >= minimo) {
            isLenghtOk = true;
        }
        return isLenghtOk;
    }

   /**
    * Método apra verificar que el correo electrónico cumpla con el formato
    * @param emailField String del correo electrónico
    * @return Boolean del cumplimiento
    */
   public boolean validarFormatoCorreo(String emailField) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(emailField);
        return matcher.find();
    }

   /**
    * Método para aplicar un regex a un String
    * @param field String a verificar
    * @param regex String del Regex a aplicar
    * @return
    */
   public static boolean aplicarExpresionRegular(String field, String regex) {
        boolean flag;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(field);

        flag = m.find();
        return flag;
    }

   /**
    * Método para mostrar un aviso en un TextField
    * @param message String mensaje a mostrar
    * @param textField JFXTextField para mostrar el mensaje y el enfoque
    */
   public void showTextFieldMessage(String message, JFXTextField textField) {
        textField.setFocusColor(Paint.valueOf(FOCUS_COLOR));
        textField.setUnFocusColor(Paint.valueOf(FOCUS_COLOR));
        textField.setPromptText(message);
        textField.setStyle(WRONG_FIELD_STYLE);
    }

   /**
    * Método para el ajustar el mensaje y focus en un JFXTextField
    * @param message String del mensaje a mostar
    * @param textField JFXTextField parar mostrar el mensaje y enfoque
    */
   public void resetTextFieldMessage(String message, JFXTextField textField) {
        textField.setFocusColor(Paint.valueOf(PASSWORD_FIELD_FOCUS));
        textField.setUnFocusColor(Paint.valueOf(UNFOCUS_COLOR));
        textField.setPromptText(message);
        textField.setStyle(DEFAULT_PASSWORD_STYLE);
    }
   /**
    * Método para mostrar un aviso en un JFXPasswordField
    * @param message String mensaje a mostrar
    * @param passwordField JFXPasswordField para mostrar el mensaje y el enfoque
    */
    public void showPasswordMessage(String message, JFXPasswordField passwordField) {
        passwordField.setFocusColor(Paint.valueOf(FOCUS_COLOR));
        passwordField.setUnFocusColor(Paint.valueOf(FOCUS_COLOR));
        passwordField.setPromptText(message);
        passwordField.setStyle(WRONG_FIELD_STYLE);
    }
   /**
    * Método para el ajustar el mensaje y focus en un JFXTextField
    * @param message String del mensaje a mostar
    * @param passwordField JFXPasswordField parar mostrar el mensaje y enfoque
    */
    public void resetPasswordMessage(String message, JFXPasswordField passwordField) {
        passwordField.setFocusColor(Paint.valueOf(PASSWORD_FIELD_FOCUS));
        passwordField.setUnFocusColor(Paint.valueOf("#17a589"));
        passwordField.setPromptText(message);
        passwordField.setStyle(DEFAULT_PASSWORD_STYLE);
    }

    @FXML
    void iniciarSesion(MouseEvent event) {
        Stage mainWindow = (Stage) tFieldCorreo.getScene().getWindow();
        IGUInicioSesionController controlador = new IGUInicioSesionController();
        controlador.abrirIGUInicioSesion(mainWindow);
    }

   /**
    * Método para abrir la ventana del controlador con sus elementos.
    * @param stageActual Stage del cual ha sido solicitado
    */
   public void abrirIGURegistro(Stage stageActual) {
        try {
            AnchorPane rootPane;
            Stage stagePrincipal = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/presentacion/IGURegistro.fxml"));

            IGURegistroController controlador = new IGURegistroController();
            fxmlLoader.setController(controlador);
            rootPane = fxmlLoader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setScene(scene);
            stagePrincipal.setTitle("ROOMBA Registro");
            stagePrincipal.show();
            stageActual.close();

        } catch (IOException ex) {
            Logger.getLogger(IGUInicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
