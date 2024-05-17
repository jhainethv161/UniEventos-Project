package co.edu.uniquindio.uniEventos.modelo;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Cupon {
    private float porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String codigo;
}
