package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;

import java.util.List;

public class ServicioPlantillaHoraria {

    private final RepositorioPlantillaHoraria repo;

    public ServicioPlantillaHoraria() {
        this.repo = new RepositorioPlantillaHoraria();
    }

    public ServicioPlantillaHoraria(RepositorioPlantillaHoraria repo) {
        this.repo = repo;
    }

    public PlantillaHoraria agregarFranja(Medico medico, String diaSemana,
                                          String horaInicio, String horaFin) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico es obligatorio.");
        }
        validarCampoRequerido(diaSemana,   "día de semana");
        validarCampoRequerido(horaInicio,  "hora de inicio");
        validarCampoRequerido(horaFin,     "hora de fin");

        String diaLimpio   = diaSemana.trim();
        String horaLimpia  = horaInicio.trim();
        String finLimpio   = horaFin.trim();

        boolean franjaYaRegistrada = repo.existeDuplicado(
                medico.getId(), diaLimpio, horaLimpia);

        if (franjaYaRegistrada) {
            throw new IllegalArgumentException(
                    "Ya existe una franja registrada para ese médico en "
                            + diaLimpio + " a las " + horaLimpia + ".");
        }

        PlantillaHoraria franja = new PlantillaHoraria(
                medico, diaSemana.trim(), horaInicio.trim(), horaFin.trim());
        return repo.guardar(franja);
    }

    private void validarCampoRequerido(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El campo " + nombreCampo + " es obligatorio.");
        }
    }

    public List<PlantillaHoraria> obtenerPlantilla(int medicoId) {
        return repo.buscarPorMedico(medicoId);
    }

    public boolean tienePlantilla(int medicoId) {
        return !repo.buscarPorMedico(medicoId).isEmpty();
    }
}