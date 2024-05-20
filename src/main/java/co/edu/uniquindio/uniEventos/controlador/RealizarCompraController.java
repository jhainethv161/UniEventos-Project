package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RealizarCompraController implements Initializable {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private ComboBox<String> opcionesLocalidad;
    @FXML
    private TextField txtIdEvento;
    @FXML
    private TextField txtEntradas;
    @FXML
    private TextField txtCupon;

    public void realizarCompra() {
        // Implementación del método realizarCompra
    }

    public void volver() {
        controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Iniciar sesion");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Añadir un listener a txtIdEvento para detectar cambios en su texto
        txtIdEvento.textProperty().addListener((observable, oldValue, newValue) -> {
            cargarLocalidades();
        });
    }

    public void cargarLocalidades() {
        try {
            String codigoEvento = txtIdEvento.getText();
            Evento evento = controladorPrincipal.buscarEventoCodigo(codigoEvento);
            ArrayList<String> nombreLocalidades = new ArrayList<>();
            if (evento != null) {
                for (Localidad localidad : evento.getLocalidades()) {
                    nombreLocalidades.add(localidad.getNombre());
                }
            }

            opcionesLocalidad.setItems(FXCollections.observableArrayList(nombreLocalidades));
        } catch (Exception e) {
            //mostrarMensajeError("Error al cargar localidades: " + e.getMessage());
        }
    }
}
