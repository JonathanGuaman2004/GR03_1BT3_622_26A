package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioIndisponibilidadBloqueoVerificarTest {

    private static class RepoCitaVaciaFake extends RepositorioCita {
        @Override
        public Cita buscarCitaMasProximaEnFecha(int medicoId, String fecha) {
            return null;
        }
    }

    private static class RepoIndispFake extends RepositorioIndisponibilidad {
        @Override
        public Indisponibilidad guardar(Indisponibilidad i) {
            return i;
        }
    }

    private static class RepoBloqueEstadoFake extends RepositorioBloqueHorario {
        public List<String> estadosActualizados = new ArrayList<>();
        private final List<BloqueHorario> bloques;

        RepoBloqueEstadoFake(Medico medico) {
            // 3 bloques: 2 DISPONIBLE, 1 OCUPADO
            BloqueHorario b1 = new BloqueHorario(
                    medico, "2026-05-22", "08:00", "08:30");
            b1.setEstado(BloqueHorario.DISPONIBLE);

            BloqueHorario b2 = new BloqueHorario(
                    medico, "2026-05-22", "08:30", "09:00");
            b2.setEstado(BloqueHorario.DISPONIBLE);

            BloqueHorario b3 = new BloqueHorario(
                    medico, "2026-05-22", "09:00", "09:30");
            b3.setEstado(BloqueHorario.OCUPADO);

            this.bloques = Arrays.asList(b1, b2, b3);
        }

        @Override
        public List<BloqueHorario> buscarPorMedicoYFecha(
                int medicoId, String fecha) {
            return bloques;
        }

        @Override
        public BloqueHorario actualizar(BloqueHorario b) {
            estadosActualizados.add(b.getEstado());
            return b;
        }
    }

    // RED: falla porque registrarIndisponibilidad no existe aun o
    // no bloquea todos los bloques del medico en la fecha
    @Test
    void registrarIndisponibilidad_verificaQueTodosLosBloquesSeBloquearon() {
        Medico medico = new Medico("Dr. Verify", "verify@mail.com",
                "pass", "Neurología", "MED-VF");

        RepoBloqueEstadoFake repoBloque = new RepoBloqueEstadoFake(medico);

        ServicioIndisponibilidad servicio = new ServicioIndisponibilidad(
                new RepoIndispFake(),
                new RepoCitaVaciaFake(),
                repoBloque);

        servicio.registrarIndisponibilidad(
                medico, "2026-05-22", "Congreso medico");

        // DESPUÉS: usar assertAll para agrupar las verificaciones relacionadas
        // y nombrar variables para mayor claridad:
        int totalBloques        = repoBloque.estadosActualizados.size();
        boolean todosBloqueados = repoBloque.estadosActualizados.stream()
                .allMatch(BloqueHorario.BLOQUEADO::equals);

        org.junit.jupiter.api.Assertions.assertAll(
                "Verificacion del bloqueo de todos los horarios",
                () -> assertEquals(3, totalBloques,
                        "Los 3 bloques deben haber sido actualizados"),
                () -> assertTrue(todosBloqueados,
                        "Todos los bloques deben haberse marcado como BLOQUEADO")
        );
    }
}