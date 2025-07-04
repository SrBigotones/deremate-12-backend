package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Map;

@Service
public class EmailService {

    private final PropertyPlaceholderHelper paramsHelper = new PropertyPlaceholderHelper("!{", "}");

    @Value("${email.confirmation.body.template}")
    private String emailConfirmationBodyTemplate;
    @Value("${email.password.recovery.body.template}")
    private String emailPasswordRecoveryTemplate;
    @Value("${email.delivery.notification.body.template}")
    private String emailClienteEntregaTemplate;

    @Value("${email.confirmation.subject}")
    private String emailConfirmationSubject;
    @Value("${email.entrega.cliente.subject}")
    private String emailEntregaClientSubject;

    @Value("${email.from}")
    private String emailFrom;

    @Value("${mail.service.enabled}")
    private boolean mailServiceEnabled;

    @Autowired
    private JavaMailSender emailSender;

    private void enviarMail(String to, String subject, String htmlContent) {
        if (!mailServiceEnabled) {
            // Si el servicio de mail no está habilitado, no se envía el correo.
            System.out.println("--------------------------------------------------");
            System.out.println("El servicio de correo electrónico está deshabilitado. No se enviará el correo a: " + to);
            System.out.println("Asunto: " + subject);
            System.out.println("Contenido HTML: " + htmlContent);
            System.out.println("--------------------------------------------------");
            return;
        }
        
        MimeMessage mensaje = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(emailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            emailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensajeRegistroUsuario(Usuario usuario, String codigoConfirmacion) {
        var params = Map.of("nombre", usuario.getNombre(),
                "apellido", usuario.getApellido(),
                "codigo", codigoConfirmacion);

        enviarMail(usuario.getEmail(), emailConfirmationSubject, completarEmailTemplate(emailConfirmationBodyTemplate, params));
    }

    public void enviarMensajeOlvidoPassword(Usuario usuario, String codigoConfirmacion) {
        var params = Map.of("nombre", usuario.getNombre(),
                "apellido", usuario.getApellido(),
                "codigo", codigoConfirmacion);

        enviarMail(usuario.getEmail(), emailConfirmationSubject, completarEmailTemplate(emailPasswordRecoveryTemplate, params));
    }

    public void enviarMensajeCodigoConfirmacion(Entrega entrega, String codigoConfirmacion) {
        var params = Map.of("emailCliente", entrega.getEmailCliente(),
                "direccionEntrega", entrega.getDireccionEntrega(),
                "codigoEntrega", codigoConfirmacion);

        enviarMail(entrega.getEmailCliente(), emailEntregaClientSubject, completarEmailTemplate(emailClienteEntregaTemplate, params));
    }

    private String completarEmailTemplate(String template, Map<String, String> params) {
        return paramsHelper.replacePlaceholders(template, params::get);
    }
}
