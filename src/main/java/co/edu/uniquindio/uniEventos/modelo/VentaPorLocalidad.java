package co.edu.uniquindio.uniEventos.modelo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class VentaPorLocalidad {
    private final SimpleStringProperty localidad;
    private final SimpleFloatProperty ventas;

    public VentaPorLocalidad(String localidad, Float ventas) {
        this.localidad = new SimpleStringProperty(localidad);
        this.ventas = new SimpleFloatProperty(ventas);
    }

    public String getLocalidad() {
        return localidad.get();
    }

    public SimpleStringProperty localidadProperty() {
        return localidad;
    }

    public Float getVentas() {
        return ventas.get();
    }

    public SimpleFloatProperty ventasProperty() {
        return ventas;
    }
}
