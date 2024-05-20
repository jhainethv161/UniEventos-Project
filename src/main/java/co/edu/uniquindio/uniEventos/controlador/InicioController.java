package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;

import java.awt.*;
import javafx.scene.control.Button;


public class InicioController {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrarme;


    public void iniciarSesion(){
        controladorPrincipal.navegarVentana("/iniciarSesion.fxml", "Iniciar sesión");
        controladorPrincipal.cerrarVentana(btnRegistrarme);
    }

}
