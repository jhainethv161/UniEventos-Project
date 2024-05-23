package co.edu.uniquindio.uniEventos.factory;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;
import co.edu.uniquindio.uniEventos.servicios.CreacionEvento;
import co.edu.uniquindio.uniEventos.utils.EventoUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventoFestival implements CreacionEvento {
    EventoUtils eventoUtils = new EventoUtils();
    @Override
    public Evento crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, LocalDate fecha, String direccion, ArrayList<Localidad> localidades) throws Exception {
        return eventoUtils.crearEvento(nombre, ciudad, descripcion, tipoEvento, fecha, localidades, "FEST-", descripcion);
    }
}
