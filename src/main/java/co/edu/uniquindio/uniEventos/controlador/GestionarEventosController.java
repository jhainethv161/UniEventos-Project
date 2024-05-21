package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.controlador.observador.Observable;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.ArrayList;

public class GestionarEventosController implements Observable {
    private final ControladorPrincipal controladorPrincipal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtImagen;
    @FXML
    private TextField txtDireccion;
    @FXML
    private DatePicker fecha;
    @FXML
    private ComboBox<TipoEvento> tipoEvento;
    @FXML
    private Button btnLocalidades;
    private ArrayList<Localidad> localidades;

    @FXML
    private TableView<Localidad> tablaLocalidades;
    @FXML
    private TableColumn<Localidad, String> nombre;
    @FXML
    private TableColumn<Localidad, Integer> capacidad;
    @FXML
    private TableColumn<Localidad, Float> precio;


    public GestionarEventosController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
        localidades = controladorPrincipal.getLocalidadesEvento();
    }

    public void crearLocalidades() {
        FXMLLoader loader = controladorPrincipal.navegarVentana("/localidad.fxml", "Crear localidad");
        if (loader != null) {
            LocalidadesController controlador = loader.getController();
            if (controlador != null) {
                controlador.inicializarObservable(this);
            } else {
                System.err.println("Error: No se pudo obtener el controlador.");
            }
        } else {
            System.err.println("Error: No se pudo cargar el archivo FXML.");
        }
    }


    public void crearEvento(){
        try {
            controladorPrincipal.crearEvento(txtNombre.getText(), txtCiudad.getText(), txtDescripcion.getText(), tipoEvento.getValue(), txtImagen.getText(), fecha.getValue(), txtDireccion.getText(), localidades);
            controladorPrincipal.mostrarAlerta("Evento creado de manera exitosa", Alert.AlertType.INFORMATION);
            txtCiudad.clear();
            txtDescripcion.clear();
            txtDireccion.clear();
            txtImagen.clear();
            txtNombre.clear();
            fecha.setValue(null);
            tipoEvento.setValue(null);
            controladorPrincipal.getLocalidadesEvento().clear();
            notificar();
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @Override
    public void notificar() {
        localidades = controladorPrincipal.getLocalidadesEvento();
        consultarLocalidades();
    }

    private void consultarLocalidades(){
        tablaLocalidades.setItems( FXCollections.observableArrayList(localidades) );
    }

    public void initialize() {
        // Inicializa las columnas de la tabla
        nombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        capacidad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidadMaxima()));
        precio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));

        tipoEvento.setItems(FXCollections.observableArrayList(TipoEvento.values()));

    }

}
