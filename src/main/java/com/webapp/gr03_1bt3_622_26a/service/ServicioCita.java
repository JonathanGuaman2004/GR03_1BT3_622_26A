package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;
import java.util.List;
import java.util.Map;

public class ServicioCita {

    private final RepositorioCita          repoCita    = new RepositorioCita();
    private final RepositorioBloqueHorario repoBloque  = new RepositorioBloqueHorario();
    private final RepositorioPaciente      repoPaciente = new RepositorioPaciente();
    private final ServicioNotificacion     notif       = new ServicioNotificacion();
    private final ServicioAgenda           agenda      = new ServicioAgenda();

    public Cita agendar(Map<String, String> datos, Usuario agendadoPor) {
        int pacienteId = Integer.parseInt(datos.get("pacienteId"));
        int bloqueId   = Integer.parseInt(datos.get("bloqueId"));
        String motivo  = datos.getOrDefault("motivo", "Consulta general");

        BloqueHorario bloque = repoBloque.buscarPorId(bloqueId);
        if (bloque == null || !bloque.isDisponible() || !bloque.isPublicado()) {
            throw new IllegalArgumentException(
                    "El horario seleccionado no está disponible.");
        }

        Paciente paciente = repoPaciente.buscarPorId(pacienteId);
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente no encontrado.");
        }

        Cita cita = new Cita(paciente, bloque.getMedico(),
                bloque, motivo, agendadoPor);
        agenda.ocuparBloque(bloqueId);
        cita = repoCita.guardar(cita);
        notif.enviarConfirmacion(cita);
        return cita;
    }

    public void cancelar(int citaId, Usuario actor) {
        Cita cita = repoCita.buscarPorId(citaId);
        if (cita == null) return;
        if (!cita.isCancelable()) {
            throw new IllegalArgumentException(
                    "Esta cita no puede cancelarse en su estado actual.");
        }
        cita.cancelar(actor, "Cancelada por " + actor.getRol().toLowerCase());
        repoCita.actualizar(cita);
        agenda.liberarBloque(cita.getBloque().getId());
    }

    public List<Cita> getCitasActivasPorPaciente(int pacienteId) {
        return repoCita.buscarActivasPorPaciente(pacienteId);
    }

    public List<Cita> getHistorialPorPaciente(int pacienteId) {
        return repoCita.buscarHistorialPorPaciente(pacienteId);
    }

    public List<Cita> getCitasPorMedico(int medicoId) {
        return repoCita.buscarPorMedico(medicoId);
    }

    public Cita buscarPorId(int id) {
        return repoCita.buscarPorId(id);
    }
}