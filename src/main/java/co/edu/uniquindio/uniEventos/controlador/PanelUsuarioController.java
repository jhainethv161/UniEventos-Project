package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.controlador.observador.Observable;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelUsuarioController implements Initializable {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    @FXML
    private ComboBox<TipoEvento> tipoEvento;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoEvento.setItems(FXCollections.observableArrayList(TipoEvento.values()));
    }

    public void realizarCompra(){
        controladorPrincipal.navegarVentana("/realizarCompra.fxml", "Realizar compra");
        controladorPrincipal.cerrarVentana(tipoEvento);
    }

    public void cancelearCompra(){
        controladorPrincipal.navegarVentana("/cancelarCompra.fxml", "Cancelar compra");
        controladorPrincipal.cerrarVentana(tipoEvento);
    }

    public void cerrarSesion(){
        ButtonType respuesta = controladorPrincipal.mostrarAlerta("¿Esta seguro de cerrar la sesion?", Alert.AlertType.CONFIRMATION);
        if (respuesta == ButtonType.OK){
            controladorPrincipal.navegarVentana("/inicio.fxml", "Inicio");
            controladorPrincipal.cerrarVentana(tipoEvento);
            controladorPrincipal.cerrarSesion();
        }
    }
}
