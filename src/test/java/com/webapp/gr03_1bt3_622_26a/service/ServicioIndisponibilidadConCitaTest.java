package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioIndisponibilidadConCitaTest {

    // Fake que simula que hay citas agendadas ese día
    private static class RepoCitaConCitasEnFechaFake extends RepositorioCita {
        @Override
        public List<Cita> buscarCitasEnFecha(int medicoId, String fecha) {
            Medico medico   = new Medico("Dr. F", "f@mail.com", "p",
                    "Cardiología", "MED-F");
            Paciente paciente = new Paciente("Paciente", "p@mail.com", "p",
                    "111", "222");
            BloqueHorario bloque = new BloqueHorario(
                    medico, fecha, "09:00", "09:30");
            Cita cita = new Cita(paciente, medico, bloque, "Consulta", paciente);
            return Arrays.asList(cita);
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
        public int bloqueoCount = 0;
        private final List<BloqueHorario> bloques;

        RepoBloqueConBloquesFake(Medico medico, String fecha) {
            BloqueHorario b1 = new BloqueHorario(medico, fecha, "08:00", "08:30");
            BloqueHorario b2 = new BloqueHorario(medico, fecha, "09:00", "09:30");
            this.bloques = Arrays.asList(b1, b2);
        }

        @Override
        public List<BloqueHorario> buscarPorMedicoYFecha(int medicoId, String fecha) {
            return bloques;
        }

        @Override
        public BloqueHorario actualizar(BloqueHorario b) {
            bloqueoCount++;
            return b;
        }
    }

    @Test
    void registrarIndisponibilidad_conCitasAgendadas_lanzaExcepcionSinBloquear() {
        String fecha   = "2026-06-02";
        Medico medico  = new Medico("Dr. Ocupado", "ocupado@mail.com",
                "pass", "Cardiología", "MED-OC");

        RepoIndispFake              repoIndisp = new RepoIndispFake();
        RepoCitaConCitasEnFechaFake repoCita   = new RepoCitaConCitasEnFechaFake();
        RepoBloqueConBloquesFake    repoBloque =
                new RepoBloqueConBloquesFake(medico, fecha);

        ServicioIndisponibilidad servicio = new ServicioIndisponibilidad(
                repoIndisp, repoCita, repoBloque);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.registrarIndisponibilidad(medico, fecha, "Enfermedad"),
                "Debe lanzar IllegalStateException cuando hay citas agendadas");

        assertTrue(ex.getMessage().toLowerCase().contains("cita")
                        || ex.getMessage().toLowerCase().contains("administrador"),
                "El mensaje debe mencionar las citas o al administrador");

        assertNull(repoIndisp.guardada,
                "No debe haberse persistido la indisponibilidad");

        assertEquals(0, repoBloque.bloqueoCount,
                "No debe haberse bloqueado ningun bloque");
    }
}