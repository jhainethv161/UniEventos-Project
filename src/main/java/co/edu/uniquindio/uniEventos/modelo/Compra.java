package co.edu.uniquindio.uniEventos.modelo;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Compra {
    private Usuario usuario;
    private Evento evento;
    private int cantidadEntradas;
    private Localidad localidad;
    private Cupon cupon;
    private Factura factura;

}
