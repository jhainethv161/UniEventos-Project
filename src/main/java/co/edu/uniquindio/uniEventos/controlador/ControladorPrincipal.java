package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.*;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.servicios.UniEventosServicio;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

public class ControladorPrincipal implements UniEventosServicio {

    private final UniEventos uniEventos;

    @Getter
    @Setter
    public ArrayList<Localidad> localidadesEvento;
    private ControladorPrincipal() {
        uniEventos = new UniEventos();
        localidadesEvento = new ArrayList<>();
    }
    public static ControladorPrincipal INSTANCIA;
    public static Sesion SESION;
    public static ControladorPrincipal getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    public Sesion getInstanciaSesion(){
        if (SESION == null) {
            SESION = new Sesion();
        }
        return SESION;
    }
    public void cerrarSesion(){
        SESION= null;
    }

    public FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana){
        try {
            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();
            return loader;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    public ButtonType mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(mensaje);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        return alert.getResult();
    }

    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    @Override
    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        return uniEventos.registrarUsuario(cedula, nombreCompleto, telefono, email, contrasena);
    }

    @Override
    public boolean activarCuenta(String email, String codigoActivacion) throws Exception {
        return uniEventos.activarCuenta(email, codigoActivacion);
    }

    @Override
    public Usuario iniciarSesion(String email, String password) throws Exception {
        return uniEventos.iniciarSesion(email, password);
    }

    @Override
    public Usuario obtenerUsuarioEmail(String email) throws Exception {
        return null;
    }

    @Override
    public Usuario obtenerUsuarioCedula(String cedula) throws Exception {
        return null;
    }

    @Override
    public boolean crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        return uniEventos.crearEvento(nombre, ciudad, descripcion, tipoEvento, imagen, fecha, direccion, localidades);
    }

    @Override
    public boolean modificarEvento(String eventoId, String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion) throws Exception {
        return uniEventos.modificarEvento(eventoId, nombre, ciudad, descripcion, tipoEvento, imagen, fecha, direccion);
    }

    @Override
    public Evento buscarEventoCodigo(String eventoId) throws Exception {
        return uniEventos.buscarEventoCodigo(eventoId);
    }

    @Override
    public ArrayList<Evento> listarEventos() throws Exception {
        return uniEventos.listarEventos();
    }

    @Override
    public List<Evento> filtrarEventos(String nombre, TipoEvento tipoEvento, String ciudad) throws Exception {
        return null;
    }

    @Override
    public boolean eliminarEvento(String codigo) throws Exception {
        return uniEventos.eliminarEvento(codigo);
    }

    @Override
    public boolean crearCupon(float porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        return uniEventos.crearCupon(porcentajeDescuento, fechaInicio, fechaFin);
    }

    @Override
    public Compra realizarCompra(String email, String eventoId, String localidad, int cantidadEntradas, String codigoCupon) throws Exception {
        return uniEventos.realizarCompra(email, eventoId, localidad, cantidadEntradas, codigoCupon);
    }

    @Override
    public void comprobarCapacidadLocalidad(Localidad localidad, int cantidadEntradas) throws Exception {

    }

    @Override
    public Localidad obtenerLocalidad(Evento evento, String nombreLocalidad) throws Exception {
        return null;
    }

    @Override
    public boolean cancelarCompra(String codigoFactura) throws Exception {
        return uniEventos.cancelarCompra(codigoFactura);
    }

    @Override
    public Compra obtenerCompra(String codigoFactura) throws Exception {
        return null;
    }

    @Override
    public Factura generarFactura(Compra compra) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Compra> listarComprasPorUsuario(String email) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Evento> listarEventosPorRecaudacion() throws Exception {
        return null;
    }

    @Override
    public EstadisticasEvento obtenerEstadisticasEvento(String eventoId) throws Exception {
        return uniEventos.obtenerEstadisticasEvento(eventoId);
    }

    @Override
    public void validarString(String string, String mensaje) throws Exception {

    }

    @Override
    public void validarDatosRegistro(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {

    }

    @Override
    public void validarDatosEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {

    }

    @Override
    public void validarDatosCupon(double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {

    }

    @Override
    public void validarDatosCompra(String emailUsuario, String codigoEvento, String nombreLocalidad, int cantidadPersonas) throws Exception {

    }

    @Override
    public Cupon validarYBuscarCupon(String codigoCupon, Usuario usuario) throws Exception {
        return null;
    }

    @Override
    public Cupon buscarCupon(String codigoCupon) throws Exception {
        return null;
    }

    @Override
    public void enviarEmail(Usuario usuario, String mensaje, String asunto) throws Exception {

    }

    @Override
    public void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception {

    }

    @Override
    public Localidad crearLocalidad(String nombreLocalidad, int capacidadMaxima, float precio) throws Exception {
        return uniEventos.crearLocalidad(nombreLocalidad, capacidadMaxima, precio);
    }
}
