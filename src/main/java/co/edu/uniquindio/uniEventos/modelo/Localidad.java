package co.edu.uniquindio.uniEventos.modelo;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Localidad {
    private String nombre;
    private float precio;
    private int capacidadMaxima;
}
