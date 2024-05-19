package co.edu.uniquindio.uniEventos.modelo;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Evento {
    private String nombre;
    private String ciudad;
    private String descripcion;
    private String codigo;
    private String direccion;
    private String imagen;
    private TipoEvento tipoEvento;
    private LocalDate fecha;
    private ArrayList<Localidad> localidades;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("            Detalles del Evento\n");
        sb.append("====================================\n");
        sb.append(String.format("Nombre       : %s\n", nombre));
        sb.append(String.format("Ciudad       : %s\n", ciudad));
        sb.append(String.format("Descripción  : %s\n", descripcion));
        sb.append(String.format("Código       : %s\n", codigo));
        sb.append(String.format("Dirección    : %s\n", direccion));
        sb.append(String.format("Imagen       : %s\n", imagen));
        sb.append(String.format("Tipo         : %s\n", tipoEvento));
        sb.append(String.format("Fecha        : %s\n", fecha));
        sb.append("Localidades  :\n");
        for (Localidad localidad : localidades) {
            sb.append(String.format("  - %s (Capacidad: %d, Precio: %.2f)\n", localidad.getNombre(), localidad.getCapacidadMaxima(), localidad.getPrecio()));
        }
        sb.append("====================================\n");
        return sb.toString();
    }
}
