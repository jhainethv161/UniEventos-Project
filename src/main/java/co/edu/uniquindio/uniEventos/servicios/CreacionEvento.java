package co.edu.uniquindio.uniEventos.servicios;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;

import java.time.LocalDate;
import java.util.ArrayList;

public interface CreacionEvento {
    Evento crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception;

}
