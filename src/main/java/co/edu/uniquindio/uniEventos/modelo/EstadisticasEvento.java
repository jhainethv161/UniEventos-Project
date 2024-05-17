package co.edu.uniquindio.uniEventos.modelo;
import lombok.*;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class EstadisticasEvento {
    private double porcentajeVendidoPorLocalidad;
    private double totalGanadoPorVentas;
}
