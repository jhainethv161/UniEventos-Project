package co.edu.uniquindio.uniEventos.servicios;

import co.edu.uniquindio.uniEventos.modelo.*;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface UniEventosServicio {

    // Métodos de gestión de usuarios
    boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception;

    boolean activarCuenta(String email, String codigoActivacion) throws Exception;

    Usuario iniciarSesion(String email, String password) throws Exception;

    Usuario obtenerUsuarioEmail(String email) throws Exception;

    Usuario obtenerUsuarioCedula(String cedula) throws Exception;


    // Métodos de gestión de eventos
    boolean crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception;

    boolean modificarEvento(String eventoId, String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception;

    Evento buscarEventoCodigo(String eventoId) throws Exception;

    ArrayList<Evento> listarEventos() throws Exception;

    List<Evento> filtrarEventos(String nombre,TipoEvento tipoEvento, String ciudad) throws Exception; //PARAMETROS POR DEFINIR

    boolean eliminarEvento(String codigo) throws Exception;

    // Métodos de gestión de cupones
    boolean crearCupon(float porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;

    // Métodos de compra
    Compra realizarCompra(String email, String eventoId, String localidad, int cantidadEntradas, String codigoCupon) throws Exception;

    void comprobarCapacidadLocalidad(Localidad localidad, int cantidadEntradas) throws Exception;
    Localidad obtenerLocalidad(Evento evento, String nombreLocalidad) throws Exception;

    boolean cancelarCompra(String codigoFactura) throws Exception;

    Compra obtenerCompra(String codigoFactura) throws Exception;
    Factura generarFactura(Compra compra) throws Exception;

    ArrayList<Compra> listarComprasPorUsuario(String email) throws Exception;

    // Métodos de administración
    ArrayList<Evento> listarEventosPorRecaudacion() throws Exception;

    EstadisticasEvento obtenerEstadisticasEvento(String eventoId) throws Exception;


    //Metodos de validacion
    void validarString(String string, String mensaje) throws Exception;

    void validarDatosRegistro(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception;
    void validarDatosEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception;
    void validarDatosCupon(double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception;
    void validarDatosCompra(String emailUsuario, String codigoEvento, String nombreLocalidad, int cantidadPersonas) throws Exception;

    Cupon validarYBuscarCupon(String codigoCupon, Usuario usuario) throws Exception;

    Cupon buscarCupon(String codigoCupon) throws Exception;

    void enviarEmail(Usuario usuario, String mensaje, String asunto) throws Exception;

    void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception;



    }