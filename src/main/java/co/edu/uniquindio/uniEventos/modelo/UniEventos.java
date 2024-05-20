package co.edu.uniquindio.uniEventos.modelo;

import co.edu.uniquindio.uniEventos.factory.*;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.servicios.CreacionEvento;
import co.edu.uniquindio.uniEventos.utils.EnvioEmail;
import co.edu.uniquindio.uniEventos.utils.EnvioSMS;
import co.edu.uniquindio.uniEventos.utils.EventoUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UniEventos implements co.edu.uniquindio.uniEventos.servicios.UniEventos {
    private final ArrayList<Compra> compras;
    private final ArrayList<Usuario> usuarios;
    private final ArrayList<Evento> eventos;
    private final ArrayList<Cupon> cupones;

    public UniEventos() {
        usuarios = new ArrayList<Usuario>();
        eventos = new ArrayList<Evento>();
        compras = new ArrayList<Compra>();
        cupones = new ArrayList<Cupon>();
    }

    @Override
    public boolean registrarUsuario(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        validarDatosRegistro(cedula, nombreCompleto, telefono, email, contrasena);

        if (obtenerUsuarioCedula(cedula) != null || obtenerUsuarioEmail(email) != null) {
            throw new Exception("Ya existe un usuario con la cédula o el email proporcionado.");
        }

        Usuario usuario = Usuario.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .telefono(telefono)
                .email(email)
                .contrasena(contrasena)
                .activo(false)
                .build();

        String codigoActivacion = "AC-"+UUID.randomUUID().toString().substring(0, 10);
        usuario.setCodigoActivacion(codigoActivacion);

        enviarNotificacionesRegistro(email, codigoActivacion);
        usuarios.add(usuario);

        return true;
    }

    @Override
    public void validarDatosRegistro(String cedula, String nombreCompleto, String telefono, String email, String contrasena) throws Exception {
        validarString(nombreCompleto, "El nombre es requerido");
        validarString(cedula, "La cédula es requerida");
        validarString(email, "El email es requerido");
        validarString(telefono, "El teléfono es requerido");
        validarString(contrasena, "La contraseña es requerida");
    }

    @Override
    public void enviarNotificacionesRegistro(String email, String codigoActivacion) throws Exception {
        String mensaje = "Su registro en UniEventos ha sido exitoso, solo queda un paso más.\nCódigo de activación de cuenta: " + codigoActivacion;

        //new EnvioSMS(mensaje, "+573052571455").enviarNotificacion();
        new EnvioEmail(email, "Registro exitoso", mensaje).enviarNotificacion();
        new EnvioEmail(email, "Cupon de bienvenida", "Felicidades, ha obtenido un cupon de bienvenida, el codigo es: CUPON_BIENVENIDA").enviarNotificacion();

    }

    @Override
    public void validarDatosEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        validarString(nombre, "el nombre es requerido");
        validarString(ciudad, "La ciudad es requerida");
        validarString(descripcion, "La descripcion es requerida");
        validarString(imagen, "La imagen es requerida");
        if (fecha == null || tipoEvento == null || localidades == null || localidades.isEmpty() || fecha.isBefore(LocalDate.now())){
            throw new Exception("Asegurate de llenar todos los campos. Intenta nuevamente");
        }
    }

    @Override
    public void validarDatosCupon(double porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        if (porcentajeDescuento <= 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe ser mayor que 0 y menor o igual que 100");
        }
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("Las fechas de inicio y fin del cupón son inválidas");
        }
    }

    @Override
    public boolean activarCuenta(String email, String codigoActivacion) throws Exception {
        Usuario usuario = obtenerUsuarioEmail(email);
        if (!usuario.isActivo()){
            if (usuario.getCodigoActivacion().equals(codigoActivacion)){
                usuario.setActivo(true);
                return  true;
            }
            return false;
        }
        throw new  Exception("El usuario ya se encuentra activo.");
    }

    @Override
    public boolean iniciarSesion(String email, String password) throws Exception {
        return false;
    }

    @Override
    public Usuario obtenerUsuarioEmail(String email) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;    }

    @Override
    public Usuario obtenerUsuarioCedula(String cedula) throws Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula)) {
                return usuario;
            }
        }
        return null;    }

    @Override
    public boolean crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        validarDatosEvento(nombre, ciudad, descripcion, tipoEvento, imagen, fecha, direccion, localidades);
        CreacionEvento creacionEvento;
        if (tipoEvento == TipoEvento.CONCIERTO){
            creacionEvento = new EventoConcierto();
        } else if (tipoEvento == TipoEvento.DEPORTE) {
            creacionEvento = new EventoDeporte();
        }else if (tipoEvento == TipoEvento.FESTIVAL){
            creacionEvento = new EventoFestival();
        } else if (tipoEvento == TipoEvento.TEATRO) {
            creacionEvento = new EventoTeatro();
        }else{
            creacionEvento = new EventoOtro();
        }

        Evento evento = creacionEvento.crearEvento(nombre, ciudad, descripcion, tipoEvento, imagen, fecha, direccion, localidades);
        eventos.add(evento);
        return true;
    }

    @Override
    public boolean modificarEvento(String codigo, String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        try {
            Evento evento = buscarEventoCodigo(codigo);
            ArrayList<Compra> comprasEvento = obtenerComprasEvento(evento);

            if (evento == null) {
                throw new Exception("El evento con el código proporcionado no existe.");
            }

            validarDatosEvento(nombre, ciudad, descripcion, tipoEvento, imagen, fecha, direccion, localidades);

            evento.setNombre(nombre);
            evento.setCiudad(ciudad);
            evento.setDescripcion(descripcion);
            evento.setTipoEvento(tipoEvento);
            evento.setImagen(imagen);
            evento.setFecha(fecha);
            evento.setDireccion(direccion);
            evento.setLocalidades(localidades);

            String mensaje = "Se ha modificado en evento que has comprado, los nuevos datos son: \n \n" + evento.toString();

            for (Compra compra : comprasEvento){
                Usuario usuario = compra.getUsuario();
                enviarEmail(usuario, mensaje, "Modificaion evento");
            }


            return true;
        } catch (Exception e) {
            throw new Exception("Error al modificar el evento: " + e.getMessage());
        }
    }


    @Override
    public Evento buscarEventoCodigo(String codigo) throws Exception {
        for (Evento evento : eventos) {
            if (evento.getCodigo().equals(codigo)) {
                return evento;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Evento> listarEventos() throws Exception {
        return eventos;
    }

    @Override
    public List<Evento> filtrarEventos(String nombre, TipoEvento tipoEvento, String ciudad) {
        return eventos.stream()
                .filter(evento -> (nombre == null || evento.getNombre().equalsIgnoreCase(nombre)) &&
                        (tipoEvento == null || evento.getTipoEvento() == tipoEvento) &&
                        (ciudad == null || evento.getCiudad().equalsIgnoreCase(ciudad)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean eliminarEvento(String codigo) throws Exception {
        Evento eventoEliminar = null;
        for (Evento evento:eventos){
            if (evento.getCodigo().equals(codigo)){
                    eventoEliminar = evento;
            }
        }

        ArrayList<Compra>  comprasEvento = obtenerComprasEvento(eventoEliminar);
        String mensaje = "El evento " + eventoEliminar.getNombre() + " a sido cancelado, lamentamos informate esto. En los proximos dias recibiras tu reembolso";

        if (eventoEliminar != null && comprasEvento != null && !comprasEvento.isEmpty()){
            for (Compra compra: comprasEvento){
                enviarEmail(compra.getUsuario(), mensaje, "Evento cancelado");
            }
            eventos.remove(eventoEliminar);
            return  true;
        }


        return  false;
    }


    public ArrayList<Compra> obtenerComprasEvento(Evento evento){
        ArrayList<Compra> comprasEvento = new ArrayList<>();
        for(Compra compra: compras){
            if (compra.getEvento().equals(evento)){
                comprasEvento.add(compra);
            }
        }
        return comprasEvento;

    }
    @Override
    public boolean crearCupon(float porcentajeDescuento, LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        validarDatosCupon(porcentajeDescuento, fechaInicio, fechaFin);
        String codigoCupon = UUID.randomUUID().toString().substring(0, 15);

        Cupon cupon = Cupon.builder()
                .codigo(codigoCupon)
                .porcentajeDescuento(porcentajeDescuento)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .build();

        String mensaje = "¡Se ha creado un nuevo cupón que podrás utilizar!\n" +
                "Vigencia: desde " + cupon.getFechaInicio() + " hasta " + cupon.getFechaFin() + "\n" +
                "Código cupón: " + cupon.getCodigo();

        cupones.add(cupon);
        for (Usuario usuario: usuarios){
            enviarEmail(usuario, mensaje, "Nuevo cupon disponible");
        }

        return true;
    }

    @Override
    public Compra realizarCompra(String email, String codigoEvento, String localidad, int cantidadEntradas, String codigoCupon) throws Exception {
        validarDatosCompra(email, codigoEvento, localidad, cantidadEntradas);
        Usuario usuario = obtenerUsuarioEmail(email);
        Evento evento = buscarEventoCodigo(codigoEvento);
        Localidad localidadEvento = obtenerLocalidad(evento, localidad);
        Cupon cupon = null;

        if (codigoCupon != null && !codigoCupon.isBlank()){
            cupon = validarYBuscarCupon(codigoCupon, usuario);
        }

        Compra compra = Compra.builder()
                .cantidadEntradas(cantidadEntradas)
                .cupon(cupon)
                .usuario(usuario)
                .evento(evento)
                .localidad(localidadEvento)
                .build();

        Factura factura = generarFactura(compra);
        compra.setFactura(factura);
        enviarEmail(usuario, compra.toString(), "Compra realizada con exito");
        esPrimeraCompra(usuario);
        compras.add(compra);

        return null;
    }


    private boolean esPrimeraCompra(Usuario usuario) {
        for (Compra compra : compras) {
            if (compra.getUsuario().getCedula().equals(usuario.getCedula())) {
                return false;
            }
        }

        String codigoCupon = "PRIMERA_COMPRA";

        String mensaje = "¡Felicitaciones por tu primera compra en UniEventos!\n" +
                "Como agradecimiento, te ofrecemos un cupón de descuento del 10% en tu próxima compra.\n" +
                "Código del cupón: " + codigoCupon + "\n";
        enviarEmail(usuario, mensaje, "Cupón de Descuento por Primera Compra");
        return true;
    }

    @Override
    public void comprobarCapacidadLocalidad(Localidad localidad, int cantidadEntradas) throws Exception {
        if ((localidad.getCapacidadMaxima() - localidad.getEntradasVendidas()) <= cantidadEntradas) {
            throw new Exception("Lo sentimos, no se encuentra disponible la cantidad requerida en esta localidad");
        }
        int entradasVendidas = localidad.getEntradasVendidas() + cantidadEntradas;
        localidad.setEntradasVendidas(entradasVendidas);
    }

    @Override
    public Localidad obtenerLocalidad(Evento evento, String nombreLocalidad) throws Exception{
        for (Localidad localidad : evento.getLocalidades()) {
            if (localidad.getNombre().equals(nombreLocalidad)) {
                return localidad;
            }
        }
        return null;
    }

    @Override
    public boolean cancelarCompra(String codigoFactura) throws Exception {
        Compra compra = obtenerCompra(codigoFactura);

        if (compra == null) {
            throw new Exception("No se encontró la compra con el código de factura proporcionado.");
        }

        Localidad localidad = compra.getLocalidad();
        int cantidadEntradas = compra.getCantidadEntradas();
        localidad.setEntradasVendidas(localidad.getEntradasVendidas() - cantidadEntradas);

        compras.remove(compra);

        String mensaje = "Su compra con el código de factura " + codigoFactura + " ha sido cancelada. Se han devuelto " + cantidadEntradas + " entradas. Proximamanete recibira su reembolso";
        enviarEmail(compra.getUsuario(), mensaje, "Compra Cancelada");

        return true;
    }

    @Override
    public Compra obtenerCompra(String codigoFactura) throws Exception {
        for (Compra compra : compras) {
            if (compra.getFactura().getCodigo().equals(codigoFactura)) {
                return compra;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Compra> listarComprasPorUsuario(String email) throws Exception {
        Usuario usuario = obtenerUsuarioEmail(email);
        ArrayList<Compra> comprasUsuario = new ArrayList<>();
        for (Compra compra : compras) {
            if (compra.getUsuario().getCedula().equals(usuario.getCedula())) {
                comprasUsuario.add(compra);
            }
        }

        return comprasUsuario;    }

    @Override
    public ArrayList<Evento> listarEventosPorRecaudacion() throws Exception {
        return null;
    }

    @Override
    public EstadisticasEvento obtenerEstadisticasEvento(String codigoEvento) throws Exception {
        Evento evento = buscarEventoCodigo(codigoEvento);
        if (evento == null) {
            throw new Exception("El evento con el código proporcionado no existe.");
        }

        float totalGanado = 0;

        EstadisticasEvento estadisticasEvento = new EstadisticasEvento();
        for (Localidad localidad : evento.getLocalidades()) {
            int capacidadMaxima = localidad.getCapacidadMaxima();
            int entradasVendidas = localidad.getEntradasVendidas();;
            float porcentajeVendidoLocalidad = (100*entradasVendidas)/capacidadMaxima;
            float precioTotalLocalidad = entradasVendidas * localidad.getPrecio();
            estadisticasEvento.getVentasPorLocalidad().put(localidad.getNombre(), porcentajeVendidoLocalidad);
            totalGanado += precioTotalLocalidad;
        }

        estadisticasEvento.setTotalGanadoPorVentas(totalGanado);
        return estadisticasEvento;
    }

    @Override
    public void validarString(String string, String mensaje) throws Exception {
        if (string == null || string.isEmpty() || string.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }


    @Override
    public Cupon validarYBuscarCupon(String codigoCupon, Usuario usuario) throws Exception {
    if (codigoCupon != null && !codigoCupon.isBlank()) {
        Cupon cupon = buscarCupon(codigoCupon);
        if (cupon == null) {
            throw new Exception("El cupón especificado no existe");
        }
        validarCuponUsuario(cupon, usuario);
        return cupon;
    }
    return null;
}

    private void validarCuponUsuario(Cupon cupon, Usuario usuario) throws Exception {
        LocalDate fechaActual = LocalDate.now();
        ArrayList<Compra> listaComprasUsuario = listarComprasPorUsuario(usuario.getEmail());
        for (Compra compra : listaComprasUsuario) {
            if (compra.getCupon() != null && compra.getCupon().getCodigo().equals(cupon.getCodigo())) {
                throw new Exception("Usted ya ha utilizado este cupon. Intente nuevamente.");
            }
        }

        if(!(cupon.getFechaInicio().isAfter(fechaActual) || cupon.getFechaFin().isBefore(fechaActual))){
            throw new Exception("El cupón no es válido, intente nuevamente");
        }
    }

    public Cupon buscarCupon(String codigoCupon) throws Exception{
        for (Cupon cupon: cupones){
            if (cupon.getCodigo().equals(codigoCupon)){
                return  cupon;
            }
        }
        return null;
    }

    @Override
    public Factura generarFactura(Compra compra) throws Exception {
        Localidad localidad = compra.getLocalidad();
        int cantidadPersonas = compra.getCantidadEntradas();
        Cupon cupon = compra.getCupon();
        String codigoFactura =  "F-"+UUID.randomUUID().toString();
        LocalDate fecha = LocalDate.now();

        float subtotal = localidad.getPrecio() * cantidadPersonas;
        float total = subtotal;
        if (cupon != null){
            float porcentajeDescuento = cupon.getPorcentajeDescuento()/100;
            total = subtotal - subtotal*porcentajeDescuento;
        }

        Factura factura = Factura.builder()
                .fechaVenta(fecha)
                .codigo(codigoFactura)
                .subTotal(subtotal)
                .total(total)
                .build();

        return factura;
    }

    @Override
    public void validarDatosCompra(String email, String codigoEvento, String nombreLocalidad, int cantidadEntradas) throws Exception {
        validarString(email, "La cédula es requerida");
        validarString(codigoEvento, "Es necesario el código del evento");
        validarString(nombreLocalidad, "Se requiere el nombre de la localidad");
        Usuario usuario = obtenerUsuarioEmail(email);
        Evento evento = buscarEventoCodigo(codigoEvento);
        Localidad localidad = obtenerLocalidad(evento, nombreLocalidad);
        if (cantidadEntradas<=0){
            throw  new Exception("La cantidad de personas para la compra debe ser mayor a cero.");
        }

        comprobarCapacidadLocalidad(localidad, cantidadEntradas);

        if ( usuario== null ||evento == null || localidad==null){
            throw  new Exception("Ha ocurrido un error, revisa que todos los datos sean correctos");
        }
    }


    public void enviarEmail(Usuario usuario, String mensaje, String asunto){
        String email = usuario.getEmail();
        try {
            new EnvioEmail(email, asunto, mensaje).enviarNotificacion();
        } catch (Exception e) {
            System.out.println("Error al enviar correo electrónico a: " + email);
            e.printStackTrace();
        }
    }

    public Usuario validarUsuario(String email, String contrasena) throws Exception{
        Usuario usuario = obtenerUsuarioEmail(email);
        if(usuario != null){
            if(usuario.getContrasena().equals(contrasena)){
                return usuario;
            }
        }
        return  null;
    }
}
