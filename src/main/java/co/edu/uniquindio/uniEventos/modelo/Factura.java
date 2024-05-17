package co.edu.uniquindio.uniEventos.modelo;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Factura {
    private LocalDate fechaVenta;
    private float total;
    private float subTotal;
    private String codigo;

}
