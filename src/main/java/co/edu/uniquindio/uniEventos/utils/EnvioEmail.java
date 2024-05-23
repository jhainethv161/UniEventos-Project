package co.edu.uniquindio.uniEventos.utils;


import co.edu.uniquindio.uniEventos.servicios.Notificacion;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.nio.file.Path;


@AllArgsConstructor
public class EnvioEmail implements Notificacion {

    private String destinatario, asunto, mensaje;

    @Override
    public void enviarNotificacion() {
        Email email = EmailBuilder.startingBlank()
                .from("pruebas.uq.sis@gmail.com")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "pruebas.uq.sis@gmail.com", "sucn cusm pcru zuby")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
