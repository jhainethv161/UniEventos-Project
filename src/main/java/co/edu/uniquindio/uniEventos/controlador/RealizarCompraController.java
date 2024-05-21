package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.Compra;
import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.Sesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RealizarCompraController implements Initializable {
    private final ControladorPrincipal controladorPrincipal;
    private Sesion sesion;

    public RealizarCompraController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
        sesion = controladorPrincipal.getInstanciaSesion();
    }
    @FXML
    private ComboBox<String> opcionesLocalidad;
    @FXML
    private TextField txtIdEvento;
    @FXML
    private TextField txtEntradas;
    @FXML
    private TextField txtCupon;

    public void realizarCompra() {
        try {
            if(txtEntradas.getText() == null || txtEntradas.getText().isBlank()){
                throw new Exception("Se requiere la cantidad de entrdas");
            }

            int cantidadEntradas = Integer.parseInt(txtEntradas.getText());
            Compra compra = controladorPrincipal.realizarCompra(sesion.getUsuario().getEmail(), txtIdEvento.getText(), opcionesLocalidad.getValue(), cantidadEntradas, txtCupon.getText());
            controladorPrincipal.mostrarAlerta("Compra realizada con exito. Al correo " + sesion.getUsuario().getEmail() + " se han enviado los detalles de la misma", Alert.AlertType.INFORMATION);

        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volver() {
        controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Iniciar sesion");
        controladorPrincipal.cerrarVentana(txtCupon);
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
            System.out.println("Error al cargar localidades: " + e.getMessage());
        }
    }


}
