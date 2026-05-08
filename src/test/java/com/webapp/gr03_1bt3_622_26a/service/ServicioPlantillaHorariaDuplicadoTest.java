package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioPlantillaHorariaDuplicadoTest {

    // Fake que simula que ya existe el duplicado
    private static class RepoFakeDuplicado extends RepositorioPlantillaHoraria {
        public PlantillaHoraria franjaGuardada = null;

        @Override
        public boolean existeDuplicado(int medicoId, String dia, String hora) {
            // Simula que ya existe LUNES 08:00 para el medico
            return "LUNES".equals(dia) && "08:00".equals(hora);
        }

        @Override
        public PlantillaHoraria guardar(PlantillaHoraria p) {
            this.franjaGuardada = p;
            return p;
        }
    }

    // RED: falla porque ServicioPlantillaHoraria no existe aun.
    @Test
    void agregarFranja_conDuplicado_lanzaExcepcionYNoGuarda() {
        RepoFakeDuplicado repoFake = new RepoFakeDuplicado();
        ServicioPlantillaHoraria servicio =
                new ServicioPlantillaHoraria(repoFake);

        Medico medico = new Medico(
                "Dr. Test", "test@mail.com", "pass", "Cardiología", "MED-T1");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.agregarFranja(medico, "LUNES", "08:00", "12:00"));

        assertTrue(ex.getMessage().toLowerCase().contains("duplicad")
                        || ex.getMessage().toLowerCase().contains("existe"),
                "El mensaje debe indicar que ya existe esa franja");

        assertNull(repoFake.franjaGuardada,
                "No debe haberse guardado ninguna franja");
    }
}