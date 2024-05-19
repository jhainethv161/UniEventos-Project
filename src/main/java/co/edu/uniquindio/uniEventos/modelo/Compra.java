package co.edu.uniquindio.uniEventos.modelo;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Compra {
    private Usuario usuario;
    private Evento evento;
    private int cantidadEntradas;
    private Localidad localidad;
    private Cupon cupon;
    private Factura factura;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================\n");
        sb.append("            Detalles de la Compra\n");
        sb.append("====================================\n");
        sb.append(String.format("Usuario          : %s\n", usuario.getNombreCompleto()));
        sb.append(String.format("Email            : %s\n", usuario.getEmail()));
        sb.append(String.format("Evento           : %s\n", evento.getNombre()));
        sb.append(String.format("Ciudad           : %s\n", evento.getCiudad()));
        sb.append(String.format("Fecha del Evento : %s\n", evento.getFecha()));
        sb.append(String.format("Entradas         : %d\n", cantidadEntradas));
        sb.append(String.format("Localidad        : %s\n", localidad.getNombre()));
        sb.append(String.format("Precio Localidad : %.2f\n", localidad.getPrecio()));
        if (cupon != null) {
            sb.append(String.format("Cupón            : %s\n", cupon.getCodigo()));
            sb.append(String.format("Descuento        : %.2f%%\n", cupon.getPorcentajeDescuento()));
        } else {
            sb.append("Cupón            : No aplicado\n");
        }
        if (factura != null) {
            sb.append("Factura:\n");
            sb.append(String.format("  Código Factura : %s\n", factura.getCodigo()));
            sb.append(String.format("  Subtotal       : %.2f\n", factura.getSubTotal()));
            sb.append(String.format("  Total          : %.2f\n", factura.getTotal()));
            sb.append(String.format("  Fecha Venta    : %s\n", factura.getFechaVenta()));
        } else {
            sb.append("Factura          : No generada\n");
        }
        sb.append("====================================\n");
        return sb.toString();
    }
}
