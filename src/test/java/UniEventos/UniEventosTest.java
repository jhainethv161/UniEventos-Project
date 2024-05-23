package UniEventos;


import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.UniEventos;
import co.edu.uniquindio.uniEventos.modelo.Usuario;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.utils.EnvioEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static co.edu.uniquindio.uniEventos.modelo.UniEventos.miUniEventos;
import static org.junit.jupiter.api.Assertions.*;

public class UniEventosTest {


    private UniEventos uniEventos;

    @BeforeEach
    public void setUp() {
        uniEventos = new UniEventos();
    }

    @Test
    public void testRegistrarUsuario() {
        assertDoesNotThrow(() -> {
            boolean result = uniEventos.registrarUsuario("123", "Juan Perez", "3001234567", "juan@mail.com", "password123");
            assertTrue(result);
        });
    }

    @Test
    public void testValidarDatosRegistro() {
        assertDoesNotThrow(() -> {
            uniEventos.validarDatosRegistro("123", "Juan Perez", "3001234567", "juan@mail.com", "password123");
        });
    }

    @Test
    public void testEnviarNotificacionesRegistro() {
        assertDoesNotThrow(() -> {
            uniEventos.enviarNotificacionesRegistro("juan@mail.com", "AC-1234567890");
        });
    }

    @Test
    public void testObtenerUsuarioEmail() throws Exception {
        String email = "test3@example.com";
        uniEventos.registrarUsuario("123456", "Test User 3", "555555555", email, "password3");
        Usuario usuario = uniEventos.obtenerUsuarioEmail(email);
        assertNotNull(usuario);
        assertEquals(email, usuario.getEmail());
    }

    @Test
    public void testBuscarEventoCodigo() throws Exception {
        ArrayList<Localidad> localidades = new ArrayList<>();
        localidades.add(new Localidad("VIP", 100, 200, 0));
        uniEventos.crearEvento("Test Event 4", "City", "Description", TipoEvento.CONCIERTO, "image4.jpg", LocalDate.now().plusDays(30), "Address4", localidades);
        Evento evento = uniEventos.listarEventos().get(0);
        Evento eventoBuscado = uniEventos.buscarEventoCodigo(evento.getCodigo());
        assertNotNull(eventoBuscado);
        assertEquals(evento.getCodigo(), eventoBuscado.getCodigo());
    }

    @Test
    public void testCrearLocalidad() throws Exception {
        Localidad localidad = uniEventos.crearLocalidad("VIP", 100, 200);
        assertNotNull(localidad);
        assertEquals("VIP", localidad.getNombre());
        assertEquals(100, localidad.getCapacidadMaxima());
        assertEquals(200, localidad.getPrecio(), 0);
    }



    @Test
    public void testIniciarSesion() {
        assertDoesNotThrow(() -> {
            uniEventos.registrarUsuario("123", "Juan Perez", "3001234567", "juan@mail.com", "password123");
            uniEventos.activarCuenta("juan@mail.com", "AC-1234567890");
            Usuario usuario = uniEventos.iniciarSesion("juan@mail.com", "password123");
            assertNotNull(usuario);
        });
    }

    @Test
    public void testCrearEvento() {
        assertDoesNotThrow(() -> {
            ArrayList<Localidad> localidades = new ArrayList<>();
            localidades.add(new Localidad("VIP", 100, 200, 0));
            boolean result = uniEventos.crearEvento("Concierto de Rock", "Bogotá", "El mejor concierto de rock del año", TipoEvento.CONCIERTO, "imagen.jpg", LocalDate.of(2024, 6, 20), "Dirección del evento", localidades);
            assertTrue(result);
        });
    }

    @Test
    public void testModificarEvento() {
        assertDoesNotThrow(() -> {
            ArrayList<Localidad> localidades = new ArrayList<>();
            localidades.add(new Localidad("VIP", 100, 200, 0));
            uniEventos.crearEvento("Concierto de Rock", "Bogotá", "El mejor concierto de rock del año", TipoEvento.CONCIERTO, "imagen.jpg", LocalDate.of(2024, 6, 20), "Dirección del evento", localidades);

            Evento evento = uniEventos.listarEventos().get(0);
            boolean result = uniEventos.modificarEvento(evento.getCodigo(), "Concierto de Pop", "Bogotá", "El mejor concierto de pop del año", TipoEvento.CONCIERTO, "imagen.jpg", LocalDate.of(2024, 6, 25), "Nueva dirección del evento");
            assertTrue(result);
        });
    }
}
