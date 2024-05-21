package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class InicioController implements Initializable {
    private final ControladorPrincipal controladorPrincipal;
    public InicioController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrarme;
    @FXML
    private ComboBox<TipoEvento> tipoEvento;


    public void registro(){
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro");
        controladorPrincipal.cerrarVentana(btnRegistrarme);
    }

    public void iniciarSesion(){
        controladorPrincipal.navegarVentana("/iniciarSesion.fxml", "Iniciar sesión");
        controladorPrincipal.cerrarVentana(btnRegistrarme);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoEvento.setItems(FXCollections.observableArrayList(TipoEvento.values()));
    }
}
