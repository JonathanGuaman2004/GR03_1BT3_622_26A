package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;

/**
 * Simula el envío de notificaciones de confirmación.
 * En producción se reemplazaría por un cliente SMTP o SMS real.
 */
public class ServicioNotificacion {

    /**
     * Envía una confirmación al paciente (simulado en consola).
     */
    public void enviarConfirmacion(Cita cita) {
        System.out.println("[NOTIFICACION] Confirmación enviada al paciente: "
                + cita.getPaciente().getEmail()
                + " — Cita #" + cita.getId()
                + " el " + cita.getFecha()
                + " a las " + cita.getHora()
                + " con Dr/Dra. " + cita.getMedico().getNombre());
    }

    /**
     * Notifica al médico sobre la nueva cita agendada (simulado en consola).
     */
    public void enviarAMedico(Cita cita) {
        System.out.println("[NOTIFICACION] Nueva cita notificada al médico: "
                + cita.getMedico().getEmail()
                + " — Paciente: " + cita.getPaciente().getNombre()
                + " el " + cita.getFecha()
                + " a las " + cita.getHora());
    }
}
