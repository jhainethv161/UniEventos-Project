package co.edu.uniquindio.uniEventos.utils;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import co.edu.uniquindio.uniEventos.modelo.Localidad;
import co.edu.uniquindio.uniEventos.modelo.enums.TipoEvento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class EventoUtils {
    public Evento crearEvento(String nombre, String ciudad, String descripcion, TipoEvento tipoEvento, String imagen, LocalDate fecha, ArrayList<Localidad> localidades, String prefijo) throws Exception {
        try {
            String codigo = generarCodigoEvento(prefijo);
            Evento evento = Evento.builder().
                    nombre(nombre)
                    .ciudad(ciudad)
                    .descripcion(descripcion)
                    .tipoEvento(tipoEvento)
                    .fecha(fecha)
                    .imagen(imagen)
                    .localidades(localidades)
                    .codigo(codigo)
                    .build();
            return evento;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String generarCodigoEvento(String prefijo){
        UUID uuid = UUID.randomUUID();
        String codigo = prefijo + uuid.toString();
        return codigo;
    }
}
