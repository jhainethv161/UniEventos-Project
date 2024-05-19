package co.edu.uniquindio.uniEventos.modelo;
import lombok.*;

import java.util.HashMap;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
public class EstadisticasEvento {
    private HashMap<String, Float> ventasPorLocalidad;
    private double totalGanadoPorVentas;
}
