package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.Sesion;
import co.edu.uniquindio.uniEventos.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutionException;

public class IniciarSesionController {
    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    Sesion sesion = controladorPrincipal.getInstanciaSesion();
    @FXML
    TextField email;
    @FXML
    PasswordField password;

    public void iniciarSesion(){
        String emailString = email.getText();
        String passwordString = password.getText();
        try {
            Usuario usuario = controladorPrincipal.iniciarSesion(emailString, passwordString);
            if (usuario != null){
                sesion.setUsuario(usuario);
                if (!usuario.isActivo()){
                    controladorPrincipal.navegarVentana("/activacionCuenta.fxml", "Activacion de cuenta");
                    controladorPrincipal.cerrarVentana(email);
                }else {
                    controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Panel usuario");
                    controladorPrincipal.cerrarVentana(email);
                }
            }else if(emailString.equals("admin@unieventos.com") && passwordString.equals("admin123")){
                controladorPrincipal.navegarVentana("panelAdmin.fxml", "Panel administrador");
                controladorPrincipal.cerrarVentana(email);
            }else{
                controladorPrincipal.mostrarAlerta("Datos incorrectos, intente nuevamente", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(email);
    }
}
