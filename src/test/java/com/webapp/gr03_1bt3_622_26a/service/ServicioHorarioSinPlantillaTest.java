package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ServicioHorarioSinPlantillaTest {

    // Fake que simula medico sin plantilla configurada
    private static class RepoPlantillaVaciaFake extends RepositorioPlantillaHoraria {
        @Override
        public java.util.List<com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria>
        buscarPorMedico(int medicoId) {
            return Collections.emptyList();  // sin plantilla
        }

        @Override
        public boolean existeDuplicado(int medicoId, String dia, String hora) {
            return false;
        }
    }

    private static class RepoBloqueVacioFake extends RepositorioBloqueHorario {
        @Override
        public com.webapp.gr03_1bt3_622_26a.model.BloqueHorario guardar(
                com.webapp.gr03_1bt3_622_26a.model.BloqueHorario b) {
            return b;
        }
    }

    @Test
    void generarBloques_sinPlantillaConfigurada_lanzaIllegalStateException() {
        Medico medico = new Medico(
                "Dr. SinPlantilla", "sp@mail.com", "pass",
                "Pediatría", "MED-SP");

        ServicioHorario servicio = new ServicioHorario(
                new RepoPlantillaVaciaFake(),
                new RepoBloqueVacioFake());

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.generarBloques(medico, "2026-05-11", "2026-05-15"),
                "Debe lanzar IllegalStateException si el medico no tiene plantilla");

        assertTrue(ex.getMessage().toLowerCase().contains("plantilla"),
                "El mensaje debe mencionar la plantilla");
    }
}