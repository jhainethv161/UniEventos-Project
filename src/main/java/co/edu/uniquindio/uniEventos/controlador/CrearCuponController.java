package co.edu.uniquindio.uniEventos.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CrearCuponController {
    private final ControladorPrincipal controladorPrincipal;
    public CrearCuponController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private TextField txtPorcentaje;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;

    public void crearCupon(){
        try {
            float porcentaje = 0;
            try {
                porcentaje = Float.parseFloat(txtPorcentaje.getText());
            }catch (Exception e){
                controladorPrincipal.mostrarAlerta("Asegurate que el porcentaje sea un dato numerico", Alert.AlertType.ERROR);
            }
                controladorPrincipal.crearCupon(porcentaje, fechaInicio.getValue(), fechaFin.getValue());
                controladorPrincipal.mostrarAlerta("Cupon creado con exito, los usuarios ya han sido notificados", Alert.AlertType.INFORMATION);
                txtPorcentaje.clear();
                fechaFin.setValue(null);
                fechaInicio.setValue(null);
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void  volver(){
        controladorPrincipal.cerrarVentana(fechaInicio);
    }


}
