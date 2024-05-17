package co.edu.uniquindio.uniEventos.modelo;

import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
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


}
