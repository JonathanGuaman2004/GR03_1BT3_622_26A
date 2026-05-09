package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioCitaDelDiaSinCitasTest {

    private static class RepoCitaSinCitasFake extends RepositorioCita {
        @Override
        public List<Cita> buscarPorMedicoYFecha(int medicoId, String fecha) {
            return Collections.emptyList(); // sin citas hoy
        }
    }

    // RED: falla porque getCitasDelDiaPorMedico no existe aún
    @Test
    void getCitasDelDiaPorMedico_sinCitasHoy_retornaListaVacia() {
        ServicioCita servicio = new ServicioCita(new RepoCitaSinCitasFake());

        List<Cita> resultado = servicio.getCitasDelDiaPorMedico(99);

        boolean listaVacia = resultado != null && resultado.isEmpty();

        assertAll(
                "El servicio debe retornar lista vacia sin lanzar excepcion",
                () -> assertNotNull(resultado,
                        "getCitasDelDiaPorMedico nunca debe retornar null"),
                () -> assertTrue(listaVacia,
                        "La lista debe estar vacia cuando el medico no tiene citas hoy")
        );
    }
}