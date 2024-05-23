package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.controlador.observador.Observable;
import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.ImageTableCell;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionarEventosController implements Observable {
    private final ControladorPrincipal controladorPrincipal;
    //CREACION DE EVENTO
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtDireccion;
    @FXML
    private DatePicker fecha;
    @FXML
    private ComboBox<TipoEvento> tipoEvento;
    private ArrayList<Localidad> localidades;
    @FXML
    private TableView<Localidad> tablaLocalidades;
    @FXML
    private TableColumn<Localidad, String> nombre;
    @FXML
    private TableColumn<Localidad, Integer> capacidad;
    @FXML
    private TableColumn<Localidad, Float> precio;

    //ACTUALIZACION DE EVENTO
    @FXML
    private TextField txtNombreAc;
    @FXML
    private TextField txtCiudadAc;
    @FXML
    private TextField txtDescripcionAc;
    @FXML
    private TextField txtImagenAc;
    @FXML
    private TextField txtDireccionAc;
    @FXML
    private TextField txtCodigo;
    @FXML
    private DatePicker fechaAc;
    @FXML
    private ComboBox<TipoEvento> tipoEventoAc;

    //ELIMINACION DE EVENTO
    @FXML
    private TextField txtCodigoEl;

    //FILTRO DE EVENTO
    @FXML
    private TextField txtNombreFl;
    @FXML
    private TextField txtCiudadFl;
    @FXML
    private ComboBox<TipoEvento> tipoEventoFl;
    @FXML
    private TableView<Evento> tablaEventos;
    @FXML
    private TableColumn<Evento, String> colNombre;
    @FXML
    private TableColumn<Evento, String> colCiudad;
    @FXML
    private TableColumn<Evento, String> colDescripcion;
    @FXML
    private TableColumn<Evento, TipoEvento> colTipoEvento;
    @FXML
    private TableColumn<Evento, String> colFecha;
    @FXML
    private TableColumn<Evento, String> colDireccion;
    @FXML
    private TableColumn<Evento, File> colImagen;


    //FILE CHOOSER
    @FXML
    private ImageView imagenSeleccionada;
    private File imagen;

    private ObservableList<Evento> eventos;

    public GestionarEventosController() {
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

    public void crearEvento() {
        try {
            if (imagen == null) {
                throw new Exception("Debe seleccionar una imagen para el evento.");
            }
            Evento evento = controladorPrincipal.crearEvento(txtNombre.getText(), txtCiudad.getText(), txtDescripcion.getText(), tipoEvento.getValue(), fecha.getValue(), txtDireccion.getText(), localidades);
            evento.setImagen(imagen);
            System.out.println(evento);
            System.out.println(evento.getImagen());

            controladorPrincipal.mostrarAlerta("Evento creado de manera exitosa", Alert.AlertType.INFORMATION);
            txtCiudad.clear();
            txtDescripcion.clear();
            txtDireccion.clear();
            txtNombre.clear();
            fecha.setValue(null);
            tipoEvento.setValue(null);
            controladorPrincipal.getLocalidadesEvento().clear();
            notificar();
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificar() {
        localidades = controladorPrincipal.getLocalidadesEvento();
        consultarLocalidades();
    }

    private void consultarLocalidades() {
        tablaLocalidades.setItems(FXCollections.observableArrayList(localidades));
    }

    public void initialize() {
        // Inicializa las columnas de la tabla de localidades
        nombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        capacidad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCapacidadMaxima()));
        precio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPrecio()));

        tipoEvento.setItems(FXCollections.observableArrayList(TipoEvento.values()));
        tipoEventoAc.setItems(FXCollections.observableArrayList(TipoEvento.values()));
        tipoEventoFl.setItems(FXCollections.observableArrayList(TipoEvento.values()));

        // Inicializa las columnas de la tabla de eventos
        colImagen.setCellFactory(col -> new ImageTableCell());
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCiudad()));
        colDescripcion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescripcion()));
        colTipoEvento.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTipoEvento()));
        colFecha.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFecha().toString()));
        colDireccion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        colImagen.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getImagen()));

        // Inicializa la tabla de eventos con todos los eventos
        tablaEventos.setItems(eventos);
    }

    public void actualizarEvento() {
        try {
            controladorPrincipal.modificarEvento(txtCodigo.getText(), txtNombreAc.getText(), txtCiudadAc.getText(), txtDescripcionAc.getText(), tipoEventoAc.getValue(), txtImagenAc.getText(), fechaAc.getValue(), txtDireccionAc.getText());
            controladorPrincipal.mostrarAlerta("Evento actualizado de manera exitosa", Alert.AlertType.INFORMATION);
            txtCiudadAc.clear();
            txtDescripcionAc.clear();
            txtDireccionAc.clear();
            txtImagenAc.clear();
            txtNombreAc.clear();
            fechaAc.setValue(null);
            tipoEventoAc.setValue(null);
            txtCodigo.clear();
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void eliminarEvento() {
        try {
            controladorPrincipal.eliminarEvento(txtCodigoEl.getText());
            controladorPrincipal.mostrarAlerta("EVENTO ELIMINADO CON EXITO", Alert.AlertType.INFORMATION);
            txtCodigoEl.clear();
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void filtrarEventos() {
        String nombreFiltro = txtNombreFl.getText().toLowerCase();
        String ciudadFiltro = txtCiudadFl.getText().toLowerCase();
        TipoEvento tipoFiltro = tipoEventoFl.getValue();
        List<Evento> eventosFiltrados = null;
        try {
            eventosFiltrados = controladorPrincipal.filtrarEventos(nombreFiltro, tipoFiltro, ciudadFiltro);
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }



        tablaEventos.setItems(FXCollections.observableArrayList(eventosFiltrados));
    }

    public void abrirFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        imagen = fileChooser.showOpenDialog(null);

        System.out.println(imagen);

        if (imagen != null) {
            imagenSeleccionada.setImage(new javafx.scene.image.Image(imagen.toURI().toString()));
        }
    }
}
