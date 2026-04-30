package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
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
     */
    public Cita agendar(Map<String, String> datos) {
        int pacienteId = Integer.parseInt(datos.get("pacienteId"));
        int horarioId  = Integer.parseInt(datos.get("horarioId"));
        String motivo  = datos.getOrDefault("motivo", "Consulta general");

        HorarioDisponible horario  = validarHorario(horarioId);
        Paciente          paciente = validarPaciente(pacienteId);
        Cita              cita     = crearCita(motivo, paciente, horario);

        return bloquearPersistirYNotificar(cita, horario);
    }

    /**
     * Valida que el horario exista y esté disponible
     */
    private HorarioDisponible validarHorario(int horarioId) {
        HorarioDisponible horario = repoHorario.buscarPorId(horarioId);
        if (horario == null || !horario.isDisponible()) {
            throw new IllegalArgumentException(
                    "El horario seleccionado ya no está disponible. Por favor elige otro.");
        }
        return horario;
    }

    /**
     * Verifica que el paciente exista en el sistema
     */
    private Paciente validarPaciente(int pacienteId) {
        Paciente paciente = repoPaciente.buscarPorId(pacienteId);
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente no encontrado.");
        }
        return paciente;
    }

    /**
     * Instancia la entidad Cita con estado inicial PENDIENTE
     */
    private Cita crearCita(String motivo, Paciente paciente, HorarioDisponible horario) {
        return new Cita(
                LocalDate.now(),
                LocalTime.parse(horario.getHoraInicio()),
                motivo,
                paciente,
                horario.getMedico(),  // ← Replace Temp with Query aplicado aquí
                horario
        );
    }

    /**
     * Bloquea el horario, persiste la cita y dispara las notificaciones
<     */
    private Cita bloquearPersistirYNotificar(Cita cita, HorarioDisponible horario) {
        agenda.bloquearHorario(horario);
        cita = repoCita.guardar(cita);
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
        Cita cita = repoCita.cancelarCita(citaId);

        if (cita != null && cita.getHorario() != null) {
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
