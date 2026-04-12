package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.HorarioDisponible;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;

import java.util.List;

/**
 * Lógica de consulta de disponibilidad médica.
 * Corresponde a UC3: Consultar Disponibilidad Médica.
 */
public class ServicioAgenda {

    private final RepositorioMedico  repoMedico  = new RepositorioMedico();
    private final RepositorioHorario repoHorario = new RepositorioHorario();

    /** Devuelve la lista de todos los médicos registrados. */
    public List<Medico> getMedicos() {
        return repoMedico.listar();
    }

    /** Devuelve los horarios disponibles de un médico específico. */
    public List<HorarioDisponible> getHorariosDisponibles(int medicoId) {
        return repoHorario.buscarDisponibles(medicoId);
    }

    /** Devuelve todos los horarios (disponibles y ocupados) de un médico. */
    public List<HorarioDisponible> getTodosLosHorarios(int medicoId) {
        return repoHorario.buscarPorMedico(medicoId);
    }

    /** Bloquea un horario al reservar una cita. */
    public void bloquearHorario(HorarioDisponible horario) {
        repoHorario.actualizarDisponibilidad(horario.getId(), false);
    }

    /** Libera un horario al cancelar una cita. */
    public void liberarHorario(int horarioId) {
        repoHorario.actualizarDisponibilidad(horarioId, true);
    }

    /** Busca un médico por su ID. */
    public Medico getMedico(int id) {
        return repoMedico.buscarPorId(id);
    }

    /** Busca un horario por su ID. */
    public HorarioDisponible getHorario(int id) {
        return repoHorario.buscarPorId(id);
    }
}
