package co.edu.uniquindio.uniEventos.modelo;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String contrasena;
    private String codigoActivacion;
    private boolean activo;
}
