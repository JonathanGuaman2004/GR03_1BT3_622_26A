package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ServicioIndisponibilidadConCitaTest {

    // Fake que simula cita proxima en menos de 2 horas
    private static class RepoCitaConCitaCercaFake extends RepositorioCita {
        @Override
        public Cita buscarCitaMasProximaEnFecha(int medicoId, String fecha) {
            // Crear una cita cuya hora de inicio sea 30 minutos desde ahora
            Medico medico = new Medico("Dr. F", "f@mail.com", "p",
                    "Cardiología", "MED-F");
            Paciente paciente = new Paciente("Paciente", "p@mail.com", "p",
                    "111", "222");

            LocalTime horaProxima = LocalDateTime.now().toLocalTime()
                    .plusMinutes(30);
            String horaStr = horaProxima.format(
                    DateTimeFormatter.ofPattern("HH:mm"));

            BloqueHorario bloque = new BloqueHorario(
                    medico, fecha, horaStr,
                    horaProxima.plusMinutes(30).format(
                            DateTimeFormatter.ofPattern("HH:mm")));

            return new Cita(paciente, medico, bloque, "Consulta", paciente);
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

    private static class RepoBloqueRegistroFake extends RepositorioBloqueHorario {
        public int bloqueoCount = 0;

        @Override
        public java.util.List<BloqueHorario> buscarPorMedicoYFecha(
                int medicoId, String fecha) {
            return Collections.emptyList();
        }

        @Override
        public BloqueHorario actualizar(BloqueHorario b) {
            bloqueoCount++;
            return b;
        }
    }

    // RED: falla porque registrarIndisponibilidad no existe o no lanza excepcion
    // cuando la cita esta a menos de 2 horas
    @Test
    void registrarIndisponibilidad_conCitaEnMenosDe2Horas_lanzaExcepcionSinBloquear() {
        RepoIndispFake     repoIndisp = new RepoIndispFake();
        RepoCitaConCitaCercaFake repoCita = new RepoCitaConCitaCercaFake();
        RepoBloqueRegistroFake   repoBloque = new RepoBloqueRegistroFake();

        ServicioIndisponibilidad servicio = new ServicioIndisponibilidad(
                repoIndisp, repoCita, repoBloque);

        Medico medico = new Medico("Dr. Ocupado", "ocupado@mail.com",
                "pass", "Cardiología", "MED-OC");

        String fechaManana = LocalDateTime.now().toLocalDate()
                .plusDays(1).toString();

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.registrarIndisponibilidad(
                        medico, fechaManana, "Enfermedad"),
                "Debe lanzar IllegalStateException con cita proxima");

        assertTrue(ex.getMessage().toLowerCase().contains("2 hora")
                        || ex.getMessage().toLowerCase().contains("administrador"),
                "El mensaje debe mencionar las 2 horas o al administrador");

        assertNull(repoIndisp.guardada,
                "No debe haberse persistido la indisponibilidad");

        assertEquals(0, repoBloque.bloqueoCount,
                "No debe haberse bloqueado ningun bloque");
    }
}