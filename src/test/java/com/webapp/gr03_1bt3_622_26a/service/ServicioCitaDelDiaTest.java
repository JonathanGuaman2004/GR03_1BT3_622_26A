package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioCitaDelDiaTest {

    private static class RepoCitaDelDiaFake extends RepositorioCita {
        private final int medicoIdEsperado;
        private final List<Cita> citasHoy;

        RepoCitaDelDiaFake(int medicoIdEsperado, List<Cita> citasHoy) {
            this.medicoIdEsperado = medicoIdEsperado;
            this.citasHoy         = citasHoy;
        }

        @Override
        public List<Cita> buscarPorMedicoYFecha(int medicoId, String fecha) {
            String hoy = LocalDate.now().toString();
            if (medicoId == medicoIdEsperado && hoy.equals(fecha)) {
                return citasHoy;
            }
            return java.util.Collections.emptyList();
        }
    }

    // RED: falla porque getCitasDelDiaPorMedico no existe aún en ServicioCita
    @Test
    void getCitasDelDiaPorMedico_retornaSoloCitasDeHoyEnEstadoActivo() {
        Medico medico = new Medico("Dr. Hoy", "hoy@mail.com",
                "pass", "Medicina General", "MED-HY");

        Paciente paciente = new Paciente("Juan", "juan@mail.com",
                "pass", "099", "123");

        BloqueHorario bloque = new BloqueHorario(
                medico, LocalDate.now().toString(), "09:00", "09:30");

        Cita citaProgramada = new Cita(
                paciente, medico, bloque, "Consulta", paciente);
        citaProgramada.setEstado("PROGRAMADA");

        // Simulamos que el fake solo devuelve la cita de hoy
        RepoCitaDelDiaFake repoFake = new RepoCitaDelDiaFake(
                medico.getId(), Arrays.asList(citaProgramada));

        ServicioCita servicio = new ServicioCita(repoFake);
        List<Cita> resultado  = servicio.getCitasDelDiaPorMedico(medico.getId());

        assertNotNull(resultado, "El resultado no debe ser null");
        assertFalse(resultado.isEmpty(), "Debe retornar al menos una cita");
        assertEquals(1, resultado.size(),
                "Debe retornar exactamente 1 cita del dia");
        assertEquals("PROGRAMADA", resultado.get(0).getEstado(),
                "La cita debe estar en estado PROGRAMADA");
    }
}