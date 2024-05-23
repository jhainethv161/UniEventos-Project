package UniEventos;


import co.edu.uniquindio.uniEventos.modelo.*;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

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


    }








