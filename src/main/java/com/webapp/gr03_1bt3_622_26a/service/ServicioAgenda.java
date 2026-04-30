package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import java.util.List;

public class ServicioAgenda {

    private final RepositorioMedico        repoMedico  = new RepositorioMedico();
    private final RepositorioBloqueHorario repoBloque  = new RepositorioBloqueHorario();

    public List<Medico> getMedicos() {
        return repoMedico.listarActivos();
    }

    public List<Medico> getMedicosPorEspecialidad(String especialidad) {
        return repoMedico.listarPorEspecialidad(especialidad);
    }

    public Medico getMedico(int id) {
        return repoMedico.buscarPorId(id);
    }

    public List<BloqueHorario> getBloquesDisponibles(int medicoId) {
        return repoBloque.buscarDisponiblesPorMedico(medicoId);
    }

    public BloqueHorario getBloque(int id) {
        return repoBloque.buscarPorId(id);
    }

    public void ocuparBloque(int bloqueId) {
        repoBloque.actualizarEstado(bloqueId, BloqueHorario.OCUPADO);
    }

    public void liberarBloque(int bloqueId) {
        repoBloque.actualizarEstado(bloqueId, BloqueHorario.DISPONIBLE);
    }
}