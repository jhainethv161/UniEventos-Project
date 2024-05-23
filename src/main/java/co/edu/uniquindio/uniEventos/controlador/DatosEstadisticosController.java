package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.EstadisticasEvento;
import co.edu.uniquindio.uniEventos.modelo.VentaPorLocalidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DatosEstadisticosController implements Initializable {

    private final ControladorPrincipal controladorPrincipal;

    public DatosEstadisticosController() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private TextField txtCodigo;
    @FXML
    private Label ganancias;
    @FXML
    private TableView<VentaPorLocalidad> tablaEstadisticas;
    @FXML
    private TableColumn<VentaPorLocalidad, String> localidad;
    @FXML
    private TableColumn<VentaPorLocalidad, Float> porcentaje;

    public void consultarEstadisticas() {
        try {
            EstadisticasEvento estadisticasEvento = controladorPrincipal.obtenerEstadisticasEvento(txtCodigo.getText());
            HashMap<String, Float> ventasPorLocalidad = estadisticasEvento.getVentasPorLocalidad();
            double totalGanadoPorVentas = estadisticasEvento.getTotalGanadoPorVentas();

            // Mostrar el total ganado por ventas en el Label
            ganancias.setText(String.format("Total Ganado: %.2f", totalGanadoPorVentas));

            // Convertir HashMap a ObservableList
            ObservableList<VentaPorLocalidad> data = FXCollections.observableArrayList();
            for (Map.Entry<String, Float> entry : ventasPorLocalidad.entrySet()) {
                data.add(new VentaPorLocalidad(entry.getKey(), entry.getValue()));
            }

            // Asignar los datos a la TableView
            tablaEstadisticas.setItems(data);
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
        }
    }

    public  void volver(){
        controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel administrador");
        controladorPrincipal.cerrarVentana(txtCodigo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        localidad.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        porcentaje.setCellValueFactory(new PropertyValueFactory<>("ventas"));
    }
}
