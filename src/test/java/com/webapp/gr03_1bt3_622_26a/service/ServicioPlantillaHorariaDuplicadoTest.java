package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioPlantillaHorariaDuplicadoTest {

    // Fake que simula que ya existe una franja para el día LUNES
    private static class RepoFakeDuplicado extends RepositorioPlantillaHoraria {
        public PlantillaHoraria franjaGuardada = null;

        @Override
        public boolean existeFranjaPorDia(int medicoId, String diaSemana) {
            // Simula que ya existe una franja para LUNES (sin importar la hora)
            return "LUNES".equals(diaSemana);
        }

        @Override
        public PlantillaHoraria guardar(PlantillaHoraria p) {
            this.franjaGuardada = p;
            return p;
        }
    }

    @Test
    void agregarFranja_conDiaDuplicado_lanzaExcepcionYNoGuarda() {
        RepoFakeDuplicado repoFake = new RepoFakeDuplicado();
        ServicioPlantillaHoraria servicio =
                new ServicioPlantillaHoraria(repoFake);

        Medico medico = new Medico(
                "Dr. Test", "test@mail.com", "pass", "Cardiología", "MED-T1");

        // Intenta agregar LUNES con una hora diferente — igual debe fallar
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.agregarFranja(medico, "LUNES", "10:00", "12:00"));

        assertTrue(ex.getMessage().toLowerCase().contains("existe")
                        || ex.getMessage().toLowerCase().contains("franja")
                        || ex.getMessage().toLowerCase().contains("día"),
                "El mensaje debe indicar que ya existe una franja para ese día");

        assertNull(repoFake.franjaGuardada,
                "No debe haberse guardado ninguna franja");
    }
}