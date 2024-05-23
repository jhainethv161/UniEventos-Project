package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.controlador.observador.Observable;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import com.github.aytchell.qrgen.QrGenerator;
import com.github.aytchell.qrgen.config.ErrorCorrectionLevel;
import com.github.aytchell.qrgen.config.ImageFileType;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;

import java.util.ArrayList;

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
    private TextField txtImagen;
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
    //IMAGEN Y QR
    private ImageView imagenSeleccionada;

    @FXML
    private ImageView qrGenerado;


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
        tipoEventoAc.setItems(FXCollections.observableArrayList(TipoEvento.values()));
        tipoEventoFl.setItems(FXCollections.observableArrayList(TipoEvento.values()));
    }

    public void actualizarEvento(){
        try {
            controladorPrincipal.modificarEvento(txtCodigo.getText(), txtNombreAc.getText(), txtCiudadAc.getText(), txtDescripcionAc.getText(), tipoEventoAc.getValue(),txtImagenAc.getText(), fechaAc.getValue(), txtDireccionAc.getText());
            controladorPrincipal.mostrarAlerta("Evento actualizado de manera exitosa", Alert.AlertType.INFORMATION);
            txtCiudadAc.clear();
            txtDescripcionAc.clear();
            txtDireccionAc.clear();
            txtImagenAc.clear();
            txtNombreAc.clear();
            fechaAc.setValue(null);
            tipoEventoAc.setValue(null);
            txtCodigo.clear();
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public  void eliminarEvento(){
        try {
            controladorPrincipal.eliminarEvento(txtCodigoEl.getText());
            controladorPrincipal.mostrarAlerta("EVENTO ELIMINADO CON EXITO", Alert.AlertType.INFORMATION);
            txtCodigoEl.clear();
        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void  filtrarEventos(){
     //FALTA IMPLEMENTAR LA LOGICA DE COMUNICACION CON LA CLASE UNIEVENTOS A TRAVES DEL CONTROLADOR PRINCIPAL, EL METODO YA ESTA
    }

    /**
     * Método que se ejecuta cuando se presiona el botón "Seleccionar imagen".
     * @param actionEvent
     */
    public void seleccionarImagen(ActionEvent actionEvent) {
        abrirFileChooser();
    }

    /**
     * Método que se ejecuta cuando se presiona el botón "Generar QR".
     * @param actionEvent
     * @throws Exception
     */
    public void generarQR(ActionEvent actionEvent) throws Exception{

        // Generar el código QR con la librería QRGen
        QrGenerator generator = new QrGenerator()
                .withSize(300, 300)
                .withMargin(3)
                .as(ImageFileType.PNG)
                .withErrorCorrection(ErrorCorrectionLevel.Q);

        // Escribir el código QR en un archivo temporal, reemplazar "Hello, World!" por el texto que se desea codificar
        Path img = generator
                .writeToTmpFile("Hello, World!");

        // Mostrar el código QR en la interfaz gráfica
        qrGenerado.setImage(new javafx.scene.image.Image(img.toUri().toString()));

    }

    /**
     * Método que permite abrir un FileChooser para seleccionar una imagen.
     */
    private void abrirFileChooser() {

        // Crear un FileChooser para seleccionar la imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        // Filtrar los archivos que se pueden seleccionar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        // Mostrar el FileChooser y obtener la imagen seleccionada
        File imagen = fileChooser.showOpenDialog(null);

        // Mostrar la imagen seleccionada en la interfaz gráfica
        if (imagen != null) {
            imagenSeleccionada.setImage(new javafx.scene.image.Image(imagen.toURI().toString()));
        }

    }
}
