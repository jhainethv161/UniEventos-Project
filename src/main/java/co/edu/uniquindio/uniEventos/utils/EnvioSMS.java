package co.edu.uniquindio.uniEventos.utils;

import co.edu.uniquindio.uniEventos.servicios.Notificacion;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class EnvioSMS implements Notificacion {
    private String mensaje;
    private String numero;

    public EnvioSMS(String mensaje, String numero) {
        this.mensaje = mensaje;
        this.numero = numero;
    }

    public void crearConexion(){
        Twilio.init(
                "AC182fad23e825f930191cd20e01e4095d",
                "235682d4974f7743c648d69155f06ba1");
    }

    @Override
    public void enviarNotificacion() {
        crearConexion();
        Message.creator(
                new PhoneNumber(numero),
                new PhoneNumber("+13192641572"),
                mensaje
        ).create();
    }
}
