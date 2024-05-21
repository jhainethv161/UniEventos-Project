package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CancelarCompraController {
    private final ControladorPrincipal controladorPrincipal;
    @FXML
    private TextField txtCodigoFactura;

    public CancelarCompraController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void cancelarCompra(){
        try {
            controladorPrincipal.cancelarCompra(txtCodigoFactura.getText());
            controladorPrincipal.mostrarAlerta("Compra cancelada", Alert.AlertType.INFORMATION);
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver(){
        controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Inicio");
        controladorPrincipal.cerrarVentana(txtCodigoFactura);
    }
}
