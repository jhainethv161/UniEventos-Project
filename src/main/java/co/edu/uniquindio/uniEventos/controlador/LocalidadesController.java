package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.controlador.observador.Observable;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.Sesion;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LocalidadesController {
    private Observable observable;
    private ControladorPrincipal controladorPrincipal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCapacidad;
    @FXML
    private TextField txtPrecio;

    public LocalidadesController() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }


    public void agregarLocalidad() {
        try {
            int capacidad = 0;
            float precio = 0;
            try {
                capacidad = Integer.parseInt(txtCapacidad.getText());
                precio = Float.parseFloat(txtPrecio.getText());

            }catch (Exception e){
                throw new Exception("Asegurate de que los datos del precio y la capacidad sean numericos");
            }


            Localidad nuevaLocalidad = controladorPrincipal.crearLocalidad(txtNombre.getText(), capacidad, precio);
            controladorPrincipal.getLocalidadesEvento().add(nuevaLocalidad);
            System.out.println(controladorPrincipal.getLocalidadesEvento());
            ButtonType respuesta = controladorPrincipal.mostrarAlerta(
                    "El paquete se creó exitosamente, \n¿Desea agregar otro paquete al envío?",
                    Alert.AlertType.CONFIRMATION
            );

            observable.notificar();

            if(respuesta == ButtonType.OK) {
                txtNombre.clear();
                txtCapacidad.clear();
                txtPrecio.clear();
            }else {
                controladorPrincipal.cerrarVentana(txtCapacidad);
            }

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public  void volver(){
        controladorPrincipal.cerrarVentana(txtNombre);
    }

    public void inicializarObservable(Observable observable) {
        this.observable = observable;
    }


}
