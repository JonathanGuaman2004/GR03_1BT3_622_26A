package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAdminMedicoCredencialesTest {

    // ── Fake con registro de llamadas ─────────────────────────────────────
    private static class RepoMedicoCredencialesFake extends RepositorioMedico {
        private final Medico medico;
        public Medico medicoActualizado = null;

        RepoMedicoCredencialesFake(Medico medico) {
            this.medico = medico;
        }

        @Override
        public Medico buscarPorId(int id) {
            return medico;
        }

        @Override
        public Medico actualizar(Medico m) {
            this.medicoActualizado = m;
            return m;
        }

        @Override
        public Medico buscarPorNroLicencia(String nroLicencia) {
            return null;
        }

        @Override
        public Medico buscarPorEmail(String email) {
            return null;
        }
    }

    // ── TEST 3: generarCredenciales asigna email, password y flag ─────────
    @Test
    void generarCredenciales_conMedicoExistente_asignaEmailPasswordYFlag() {
        Medico medico = new Medico(
                "Dr. Juan", null, null, "Cardiología", "MED-005");

        RepoMedicoCredencialesFake repoFake =
                new RepoMedicoCredencialesFake(medico);

        ServicioAdminMedico servicio = new ServicioAdminMedico(repoFake);
        Medico resultado = servicio.generarCredenciales(1);

        assertNotNull(resultado.getEmail(),
                "El email no debe ser null");
        assertNotNull(resultado.getPassword(),
                "La contraseña no debe ser null");
        assertFalse(resultado.getEmail().isBlank(),
                "El email no debe estar vacío");
        assertFalse(resultado.getPassword().isBlank(),
                "La contraseña no debe estar vacía");
        assertEquals(1, resultado.getDebeCambiarPwd(),
                "El flag debe_cambiar_pwd debe ser 1");
    }
}