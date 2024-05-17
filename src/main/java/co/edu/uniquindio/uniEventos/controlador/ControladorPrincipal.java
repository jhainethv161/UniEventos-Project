package co.edu.uniquindio.uniEventos.controlador;

import co.edu.uniquindio.uniEventos.modelo.*;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.servicios.UniEventos;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControladorPrincipal implements UniEventos {
    @Override
    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        return false;
    }

    @Override
    public boolean activarCuenta(String email, String codigoActivacion) throws Exception {
        return false;
    }

    @Override
    public boolean iniciarSesion(String email, String password) throws Exception {
        return false;
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
        return false;
    }

    @Override
    public boolean modificarEvento(String eventoId, String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        return false;
    }

    @Override
    public Evento buscarEventoCodigo(String eventoId) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Evento> listarEventos() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Evento> filtrarEventos() throws Exception {
        return null;
    }

    @Override
    public boolean eliminarEvento(String codigo) throws Exception {
        return false;
    }

    @Override
    public boolean crearCupon(float porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        return false;
    }

    @Override
    public boolean enviarCuponPrimeraCompra(String email) throws Exception {
        return false;
    }

    @Override
    public Compra realizarCompra(String email, String eventoId, String localidad, int cantidadEntradas, String codigoCupon) throws Exception {
        return null;
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
        return false;
    }

    @Override
    public Compra obtenerCompra(String codigoFactura) throws Exception {
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
        return null;
    }

    @Override
    public void validarString(String string, String mensaje) throws Exception {

    }

    @Override
    public void validarDatosRegistro(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {

    }

    @Override
    public void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception {

    }

    @Override
    public void validarDatosEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {

    }

    @Override
    public void validarDatosCupon(double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {

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
    public void notificarCreacionCupon(Cupon cupon) throws Exception {

    }

    @Override
    public Factura generarFactura(Compra compra) throws Exception {
        return null;
    }

    @Override
    public void validarDatosCompra(String emailUsuario, String codigoEvento, String nombreLocalidad, int cantidadPersonas) throws Exception {

    }
}
