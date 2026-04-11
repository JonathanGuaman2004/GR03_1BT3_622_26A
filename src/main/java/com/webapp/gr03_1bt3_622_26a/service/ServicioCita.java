package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.HorarioDisponible;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Lógica de negocio para el agendamiento de citas médicas.
 * Corresponde a UC4: Agendar Cita Médica.
 */
public class ServicioCita {

    private final RepositorioCita      repoCita    = new RepositorioCita();
    private final RepositorioHorario   repoHorario = new RepositorioHorario();
    private final RepositorioPaciente  repoPaciente = new RepositorioPaciente();
    private final ServicioNotificacion notif       = new ServicioNotificacion();
    private final ServicioAgenda       agenda      = new ServicioAgenda();

    /**
     * Agenda una cita para un paciente dado un horario disponible.
     *
     * @param datos mapa con: pacienteId, medicoId, horarioId, motivo
     * @return la Cita creada y confirmada
     * @throws IllegalArgumentException si el horario ya no está disponible
     */
    public Cita agendar(Map<String, String> datos) {
        int pacienteId = Integer.parseInt(datos.get("pacienteId"));
        int horarioId  = Integer.parseInt(datos.get("horarioId"));
        String motivo  = datos.getOrDefault("motivo", "Consulta general");

        // Verificar disponibilidad del horario (con bloqueo pesimista implícito)
        HorarioDisponible horario = repoHorario.buscarPorId(horarioId);
        if (horario == null || !horario.isDisponible()) {
            throw new IllegalArgumentException(
                    "El horario seleccionado ya no está disponible. Por favor elige otro.");
        }

        Paciente paciente = repoPaciente.buscarPorId(pacienteId);
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente no encontrado.");
        }

        Medico medico = horario.getMedico();

        // Crear la cita
        Cita cita = new Cita(
                LocalDate.now(),
                LocalTime.parse(horario.getHoraInicio()),
                motivo,
                paciente,
                medico,
                horario
        );

        // Bloquear el horario para evitar duplicados
        agenda.bloquearHorario(horario);

        // Persistir la cita
        cita = repoCita.guardar(cita);

        // Enviar notificaciones (<<extend>> del diagrama)
        notif.enviarConfirmacion(cita);
        notif.enviarAMedico(cita);

        return cita;
    }

    /**
     * Confirma una cita existente (cambia estado a CONFIRMADA).
     */
    public void confirmar(int citaId) {
        repoCita.actualizarEstado(citaId, "CONFIRMADA");
    }

    /**
     * Cancela una cita y libera el horario asociado.
     */
    public void cancelar(int citaId) {
        Cita cita = repoCita.buscarPorId(citaId);
        if (cita == null) return;

        repoCita.actualizarEstado(citaId, "CANCELADA");

        if (cita.getHorario() != null) {
            agenda.liberarHorario(cita.getHorario().getId());
        }
    }

    public List<Cita> getCitasPorPaciente(int pacienteId) {
        return repoCita.buscarPorPaciente(pacienteId);
    }

    public List<Cita> getCitasPorMedico(int medicoId) {
        return repoCita.buscarPorMedico(medicoId);
    }

    public Cita buscarPorId(int id) {
        return repoCita.buscarPorId(id);
    }
}
