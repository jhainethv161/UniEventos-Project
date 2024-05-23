package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PanelAdminController {
    private final ControladorPrincipal controladorPrincipal;
    @FXML
    private Button cerrarSesion;
    public PanelAdminController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void gestionarEventos(){
        controladorPrincipal.navegarVentana("/gestionarEventos.fxml", "Gestion de eventos");
    }

    public void crearCupon(){
        controladorPrincipal.cerrarVentana(cerrarSesion);
        controladorPrincipal.navegarVentana("/crearCupon.fxml", "Creacion de cupones");

    }

    public  void  cerrarSesion(){
        controladorPrincipal.cerrarVentana(cerrarSesion);
        controladorPrincipal.navegarVentana("/inicio.fxml", "Creacion de cupones");

    }

    public void datosEstadisticos(){
        controladorPrincipal.cerrarVentana(cerrarSesion);
        controladorPrincipal.navegarVentana("/datosEstadisticos.fxml", "Datos estadisticos");
    }
}
