package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAdminSuspenderConCitasTest {

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

    // ── Fake RepositorioCita con citas activas ────────────────────────────
    private static class RepoCitaConCitasActivasFake extends RepositorioCita {
        @Override
        public long contarCitasActivasPorMedico(int medicoId) {
            return 3L;
        }
    }

    // ── TEST 5: Con citas activas lanza excepción y no cambia estado ───────
    @Test
    void suspenderMedico_conCitasActivas_lanzaExcepcionSinModificarEstado() {
        Medico medico = new Medico(
                "Dr. Test", "test@mail.com", "pass", "Cardiología", "MED-T1");
        medico.setEstado("ACTIVO");

        RepoMedicoFake repoMedico = new RepoMedicoFake(medico);
        RepoCitaConCitasActivasFake repoCita = new RepoCitaConCitasActivasFake();

        ServicioAdminMedico servicio =
                new ServicioAdminMedico(repoMedico, repoCita);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> servicio.suspenderMedico(1));

        assertTrue(ex.getMessage().contains("3"),
                "El mensaje debe indicar cuántas citas activas hay");

        assertNull(repoMedico.estadoActualizado,
                "No se debe haber llamado a actualizarEstado");
    }
}