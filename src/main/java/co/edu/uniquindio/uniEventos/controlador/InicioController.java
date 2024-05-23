package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.ImageTableCell;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class InicioController implements Initializable {
    private final ControladorPrincipal controladorPrincipal;
    private List<Evento> eventos; // Lista de eventos para filtrar

    public InicioController() {
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrarme;
    @FXML
    private ComboBox<TipoEvento> tipoEvento;

    @FXML
    private TextField nombreEvento;

    @FXML
    private TextField ciudadEvento;

    @FXML
    private TableView<Evento> tablaFiltro;

    @FXML
    private TableColumn<Evento, String> nombreColumn;

    @FXML
    private TableColumn<Evento, String> direccionColumn;

    @FXML
    private TableColumn<Evento, String> codigoColumn;

    @FXML
    private TableColumn<Evento, LocalDate> fechaColumn;

    @FXML
    private TableColumn<Evento, String> ciudadColumn;

    @FXML
    private TableColumn<Evento, File> imagenColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoEvento.setItems(FXCollections.observableArrayList(TipoEvento.values()));
        try {
            eventos = controladorPrincipal.listarEventos(); // Método para obtener todos los eventos
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            eventos = FXCollections.observableArrayList(); // Inicializar como lista vacía en caso de error
        }

        // Configurar la celda de la columna de imagen para mostrar imágenes
        imagenColumn.setCellFactory(col -> new ImageTableCell());
        nombreColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        direccionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDireccion()));
        codigoColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCodigo()));
        fechaColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
        ciudadColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCiudad()));
        imagenColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getImagen()));
    }

    public void registro() {
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro");
        controladorPrincipal.cerrarVentana(btnRegistrarme);
    }

    public void iniciarSesion() {
        controladorPrincipal.navegarVentana("/iniciarSesion.fxml", "Iniciar sesión");
        controladorPrincipal.cerrarVentana(btnRegistrarme);
    }

    @FXML
    public void filtrarEventos() {
        String nombre = nombreEvento.getText().toLowerCase();
        String ciudad = ciudadEvento.getText().toLowerCase();
        TipoEvento tipo = tipoEvento.getValue();
        List<Evento> eventosFiltrados = null;
        try {
            eventosFiltrados = controladorPrincipal.filtrarEventos(nombre, tipo, ciudad);
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            return;
        }
        ObservableList<Evento> observableEventos = FXCollections.observableArrayList(eventosFiltrados);
        tablaFiltro.setItems(observableEventos);
    }
}
