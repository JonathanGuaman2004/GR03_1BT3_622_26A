package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioHorarioGenerarTest {

    private static class RepoPlantillaFake extends RepositorioPlantillaHoraria {
        private final List<PlantillaHoraria> plantilla;

        RepoPlantillaFake(List<PlantillaHoraria> plantilla) {
            this.plantilla = plantilla;
        }

        @Override
        public List<PlantillaHoraria> buscarPorMedico(int medicoId) {
            return plantilla;
        }

        @Override
        public boolean existeDuplicado(int medicoId, String dia, String hora) {
            return false;
        }
    }

    private static class RepoBloqueContadorFake extends RepositorioBloqueHorario {
        public List<BloqueHorario> bloquesGuardados = new ArrayList<>();

        @Override
        public BloqueHorario guardar(BloqueHorario b) {
            bloquesGuardados.add(b);
            return b;
        }
    }

    @Test
    void generarBloques_conPlantillaValida_creaBloquesCorrectosExcluyendoAlmuerzo() {
        Medico medico = new Medico(
                "Dr. Test", "test@mail.com", "pass", "Medicina General", "MED-T1");

        // Plantilla: LUNES de 08:00 a 14:00 (6 horas = 12 slots de 30min,
        // pero 13:00-14:00 es almuerzo = 2 slots excluidos → 10 bloques esperados)
        PlantillaHoraria franja = new PlantillaHoraria(
                medico, "LUNES", "08:00", "14:00");

        RepoPlantillaFake repoPlantilla =
                new RepoPlantillaFake(Arrays.asList(franja));
        RepoBloqueContadorFake repoBloque = new RepoBloqueContadorFake();

        ServicioHorario servicio = new ServicioHorario(repoPlantilla, repoBloque);

        // 2026-05-11 es un LUNES
        List<BloqueHorario> resultado =
                servicio.generarBloques(medico, "2026-05-11", "2026-05-11");

        assertNotNull(resultado, "El resultado no debe ser null");
        assertFalse(resultado.isEmpty(), "Debe generarse al menos un bloque");

        // 08:00-13:00 = 10 slots de 30 min (13:00-14:00 excluido = almuerzo)
        assertEquals(10, resultado.size(),
                "Deben generarse exactamente 10 bloques excluyendo almuerzo");

        // Verificar que ningun bloque empieza en horario de almuerzo
        boolean tieneAlmuerzo = resultado.stream()
                .anyMatch(b -> b.getHoraInicio().equals("13:00")
                        || b.getHoraInicio().equals("13:30"));
        assertFalse(tieneAlmuerzo,
                "Ningun bloque debe generarse en horario de almuerzo");

        // Verificar que todos estan en el dia correcto
        assertTrue(resultado.stream().allMatch(b -> "2026-05-11".equals(b.getFecha())),
                "Todos los bloques deben ser del dia solicitado");
    }
}