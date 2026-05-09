package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioIndisponibilidadSinCitaTest {

    // Fake que simula que no hay citas agendadas ese día
    private static class RepoCitaSinCitasFake extends RepositorioCita {
        @Override
        public List<Cita> buscarCitasEnFecha(int medicoId, String fecha) {
            return Collections.emptyList();
        }
    }

    private static class RepoIndispFake extends RepositorioIndisponibilidad {
        public Indisponibilidad guardada = null;

        @Override
        public Indisponibilidad guardar(Indisponibilidad i) {
            this.guardada = i;
            return i;
        }
    }

    private static class RepoBloqueConBloquesFake extends RepositorioBloqueHorario {
        public int bloquesActualizados = 0;
        private final List<BloqueHorario> bloques;

        RepoBloqueConBloquesFake(Medico medico) {
            BloqueHorario b1 = new BloqueHorario(
                    medico, "2026-05-20", "08:00", "08:30");
            BloqueHorario b2 = new BloqueHorario(
                    medico, "2026-05-20", "08:30", "09:00");
            this.bloques = Arrays.asList(b1, b2);
        }

        @Override
        public List<BloqueHorario> buscarPorMedicoYFecha(int medicoId, String fecha) {
            return bloques;
        }

        @Override
        public BloqueHorario actualizar(BloqueHorario b) {
            bloquesActualizados++;
            return b;
        }
    }

    @Test
    void registrarIndisponibilidad_sinCitas_registraYBloqueaBloques() {
        Medico medico = new Medico("Dr. Libre", "libre@mail.com",
                "pass", "Pediatría", "MED-LI");

        RepoIndispFake           repoIndisp = new RepoIndispFake();
        RepoCitaSinCitasFake     repoCita   = new RepoCitaSinCitasFake();
        RepoBloqueConBloquesFake repoBloque = new RepoBloqueConBloquesFake(medico);

        ServicioIndisponibilidad servicio = new ServicioIndisponibilidad(
                repoIndisp, repoCita, repoBloque);

        assertDoesNotThrow(
                () -> servicio.registrarIndisponibilidad(
                        medico, "2026-05-20", "Enfermedad"),
                "No debe lanzar excepcion cuando no hay citas activas");

        assertNotNull(repoIndisp.guardada,
                "La indisponibilidad debe haberse persistido");

        assertEquals("2026-05-20", repoIndisp.guardada.getFecha(),
                "La fecha persistida debe coincidir");

        assertEquals(2, repoBloque.bloquesActualizados,
                "Los 2 bloques del medico deben haberse bloqueado");
    }
}