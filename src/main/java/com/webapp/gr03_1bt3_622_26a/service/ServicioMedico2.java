package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;

import java.util.ArrayList;
import java.util.List;

public class ServicioMedico2 {

    private final RepositorioMedico repoMedico;

    public ServicioMedico2() {
        this.repoMedico = new RepositorioMedico();
    }

    public ServicioMedico2(RepositorioMedico repoMedico) {
        this.repoMedico = repoMedico;
    }

    public List<Medico> buscarMedicosPorEspecialidad(String especialidad) {
        List<Medico> todosMedicos = repoMedico.listar();
        List<Medico> medicosFiltrados = new ArrayList<>();

        for (Medico medico : todosMedicos) {
            if (especialidad.equals(medico.getEspecialidad())) {
                medicosFiltrados.add(medico);
            }
        }

        return medicosFiltrados;
    }
}
