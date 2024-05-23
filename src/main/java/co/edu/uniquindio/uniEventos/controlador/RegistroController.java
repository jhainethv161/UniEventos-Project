package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {
    private final ControladorPrincipal controladorPrincipal;
    public RegistroController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    public  void registrar(){
        try {
            controladorPrincipal.registrarUsuario(txtCedula.getText(), txtCedula.getText(), txtTelefono.getText(), txtEmail.getText(), txtPassword.getText());
            controladorPrincipal.mostrarAlerta("Registro exitoso, a su correo ha sido enviado un codigo para la activacion de su cuenta.", Alert.AlertType.INFORMATION);
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(txtEmail);
    }

}
