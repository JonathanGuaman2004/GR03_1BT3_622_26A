package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAdminSuspenderSinCitasTest {

    // ── Fake RepositorioMedico ────────────────────────────────────────────
    private static class RepoMedicoFake extends RepositorioMedico {
        private final Medico medico;
        public String estadoActualizado = null;

        RepoMedicoFake(Medico medico) { this.medico = medico; }

        @Override
        public Medico buscarPorId(int id) { return medico; }

        @Override
        public void actualizarEstado(int medicoId, String estado) {
            this.estadoActualizado = estado;
        }

        @Override
        public Medico buscarPorNroLicencia(String nroLicencia) { return null; }

        @Override
        public Medico buscarPorEmail(String email) { return null; }
    }

    // ── Fake RepositorioCita sin citas activas ────────────────────────────
    private static class RepoCitaSinCitasActivasFake extends RepositorioCita {
        @Override
        public long contarCitasActivasPorMedico(int medicoId) {
            return 0L;
        }
    }

    // ── TEST 6: Sin citas activas actualiza estado a SUSPENDIDO ───────────
    @Test
    void suspenderMedico_sinCitasActivas_actualizaEstadoASuspendido() {
        Medico medico = new Medico(
                "Dr. Test2", "test2@mail.com", "pass", "Pediatría", "MED-T2");
        medico.setEstado("ACTIVO");

        RepoMedicoFake repoMedico = new RepoMedicoFake(medico);
        RepoCitaSinCitasActivasFake repoCita = new RepoCitaSinCitasActivasFake();

        ServicioAdminMedico servicio =
                new ServicioAdminMedico(repoMedico, repoCita);

        assertDoesNotThrow(() -> servicio.suspenderMedico(1));

        assertEquals("SUSPENDIDO", repoMedico.estadoActualizado,
                "El estado debe haberse actualizado a SUSPENDIDO");
    }
}



