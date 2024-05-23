package co.edu.uniquindio.uniEventos.controlador;

public class PanelAdminController {
    private final ControladorPrincipal controladorPrincipal;
    public PanelAdminController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void gestionarEventos(){
        controladorPrincipal.navegarVentana("/gestionarEventos.fxml", "Gestion de eventos");
    }

    public void crearCupon(){
        controladorPrincipal.navegarVentana("/crearCupon.fxml", "Creacion de cupones");

    }

    public void datosEstadisticos(){
        controladorPrincipal.navegarVentana("/datosEstadisticos.fxml", "Datos estadisticos");
    }
}
