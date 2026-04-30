package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Servicio de notificaciones por correo electrónico real vía Gmail SMTP.
 * Corresponde a UC4: Agendar Cita Médica — notificación al paciente y médico.
 *
 * REFACTOR: Se extrajo la construcción del asunto y cuerpo a métodos
 * privados para que enviarConfirmacion() y enviarAMedico() queden
 * con una sola responsabilidad clara cada uno.
 */
public class ServicioNotificacion {

    // ── Constantes SMTP ──────────────────────────────────────────────────────
    private static final String CORREO_ORIGEN = "sistemasoft26@gmail.com";
    private static final String PASSWORD_APP  = "forv vnxi jrup awzk";
    private static final String SMTP_HOST     = "smtp.gmail.com";
    private static final String SMTP_PORT     = "587";

    /**
     * Envía una confirmación al paciente (simulado en consola).
     */
    public void enviarConfirmacion(Cita cita) {
        try {
            MimeMessage mensaje = construirMensaje(
                    cita.getPaciente().getEmail(),
                    asuntoConfirmacion(),
                    cuerpoConfirmacion(cita)
            );
            enviar(mensaje);
            System.out.println("[NOTIFICACION] Confirmación enviada a: "
                    + cita.getPaciente().getEmail());

        } catch (Exception e) {
            System.err.println("[NOTIFICACION] Error al enviar confirmación al paciente: "
                    + e.getMessage());
        }
    }

    /**
     * Notifica al médico sobre la nueva cita agendada (simulado en consola).
     */
    public void enviarAMedico(Cita cita) {
        try {
            MimeMessage mensaje = construirMensaje(
                    cita.getMedico().getEmail(),
                    asuntoMedico(),
                    cuerpoMedico(cita)
            );
            enviar(mensaje);
            System.out.println("[NOTIFICACION] Aviso enviado al médico: "
                    + cita.getMedico().getEmail());

        } catch (Exception e) {
            System.err.println("[NOTIFICACION] Error al notificar al médico: "
                    + e.getMessage());
        }
    }

    // ── Método extraído para permitir el spy en tests ────────────────────────

    protected void enviar(MimeMessage mensaje) throws MessagingException {
        //Transport.send(mensaje);
    }

    // ── Construcción de mensajes ─────────────────────────────────────────────

    private MimeMessage construirMensaje(String destinatario,
                                         String asunto,
                                         String cuerpo)
            throws MessagingException {
        Session session = crearSesionSMTP();
        MimeMessage mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(CORREO_ORIGEN));
        mensaje.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(destinatario));
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        return mensaje;
    }

    // ── Asuntos ──────────────────────────────────────────────────────────────

    private String asuntoConfirmacion() {
        return "Confirmación de cita — MediCitas";
    }

    private String asuntoMedico() {
        return "Nueva cita agendada — MediCitas";
    }

    // ── Cuerpos ──────────────────────────────────────────────────────────────

    private String cuerpoConfirmacion(Cita cita) {
        return "Estimado/a " + cita.getPaciente().getNombre() + ",\n\n"
                + "Su cita ha sido registrada exitosamente.\n\n"
                + "Detalles:\n"
                + "  Médico    : " + cita.getMedico().getNombre()           + "\n"
                + "  Fecha     : " + cita.getBloque().getFecha()            + "\n"
                + "  Hora      : " + cita.getBloque().getHoraInicio()       + "\n"
                + "  Motivo    : " + cita.getMotivo()                       + "\n\n"
                + "Gracias por usar MediCitas.";
    }

    private String cuerpoMedico(Cita cita) {
        return "Dr/Dra. " + cita.getMedico().getNombre() + ",\n\n"
                + "Se ha agendado una nueva cita con usted.\n\n"
                + "Detalles:\n"
                + "  Paciente  : " + cita.getPaciente().getNombre()         + "\n"
                + "  Fecha     : " + cita.getBloque().getFecha()            + "\n"
                + "  Hora      : " + cita.getBloque().getHoraInicio()       + "\n"
                + "  Motivo    : " + cita.getMotivo()                       + "\n\n"
                + "Por favor confirme la cita en el sistema.";
    }

    // ── Configuración SMTP ───────────────────────────────────────────────────

    private Session crearSesionSMTP() {
        Properties props = new Properties();
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host",            SMTP_HOST);
        props.put("mail.smtp.port",            SMTP_PORT);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CORREO_ORIGEN, PASSWORD_APP);
            }
        });
    }
}