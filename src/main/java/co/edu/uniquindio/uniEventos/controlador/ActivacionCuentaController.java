package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ActivacionCuentaController {
    private final ControladorPrincipal controladorPrincipal;

    public ActivacionCuentaController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private TextField codigoActivacion;

    public void volver(){
        controladorPrincipal.navegarVentana("/iniciarSesion.fxml", "Iniciar sesion");
        controladorPrincipal.cerrarVentana(codigoActivacion);
        controladorPrincipal.cerrarSesion();
    }

    public void activarCuenta(){
        String codigoActivacionString = codigoActivacion.getText();
        String email = controladorPrincipal.getInstanciaSesion().getUsuario().getEmail();
        try {
            boolean activacionExitosa = controladorPrincipal.activarCuenta(email, codigoActivacionString);
            if (activacionExitosa){
                controladorPrincipal.mostrarAlerta("Activacion de cuenta exitosa", Alert.AlertType.INFORMATION);
                controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Panel usuario");
                controladorPrincipal.cerrarVentana(codigoActivacion);
            }else{
                controladorPrincipal.mostrarAlerta("El codigo de activacion no coincide", Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
