package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.Compra;
import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.Sesion;
import co.edu.uniquindio.uniEventos.utils.EnvioEmail;
import com.github.aytchell.qrgen.QrGenerator;
import com.github.aytchell.qrgen.config.ErrorCorrectionLevel;
import com.github.aytchell.qrgen.config.ImageFileType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.nio.file.Path;
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
    @FXML
    private ImageView qrGenerado;

    public void realizarCompra() {
        try {
            if(txtEntradas.getText() == null || txtEntradas.getText().isBlank()){
                throw new Exception("Se requiere la cantidad de entrdas");
            }

            int cantidadEntradas = Integer.parseInt(txtEntradas.getText());
            Compra compra = controladorPrincipal.realizarCompra(sesion.getUsuario().getEmail(), txtIdEvento.getText(), opcionesLocalidad.getValue(), cantidadEntradas, txtCupon.getText());
            generarQR(compra.getFactura().getCodigo(), compra);
            controladorPrincipal.mostrarAlerta("Compra realizada con exito. Al correo " + sesion.getUsuario().getEmail() + " se han enviado los detalles de la misma", Alert.AlertType.INFORMATION);

        }catch (Exception e){
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void volver() {
        controladorPrincipal.navegarVentana("/panelUsuario.fxml", "Iniciar sesion");
        controladorPrincipal.cerrarVentana(txtCupon);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void generarQR(String codigoFactura, Compra compra) throws Exception{
        QrGenerator generator = new QrGenerator()
                .withSize(300, 300)
                .withMargin(3)
                .as(ImageFileType.PNG)
                .withErrorCorrection(ErrorCorrectionLevel.Q);

        Path img = generator
                .writeToTmpFile(codigoFactura);

        qrGenerado.setImage(new javafx.scene.image.Image(img.toUri().toString()));
        compra.setQrGenerado(img);
    }


}
