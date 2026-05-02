package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAdminMedicoTest {

    // ── Fake de RepositorioMedico reutilizable ─────────────────────────────
    private static class RepoMedicoFake extends RepositorioMedico {
        private final Medico medicoExistente;
        public Medico medicoGuardado = null;

        RepoMedicoFake(Medico medicoExistente) {
            this.medicoExistente = medicoExistente;
        }

        @Override
        public Medico buscarPorNroLicencia(String nroLicencia) {
            if (medicoExistente != null &&
                    nroLicencia.equals(medicoExistente.getNroLicencia())) {
                return medicoExistente;
            }
            return null;
        }

        @Override
        public Medico guardar(Medico m) {
            this.medicoGuardado = m;
            return m;
        }

        @Override
        public Medico buscarPorEmail(String email) {
            return null;
        }
    }

    private ServicioAdminMedico servicio;
    private RepoMedicoFake repoFake;

    @BeforeEach
    void setUp() {
        repoFake = new RepoMedicoFake(null);
        servicio = new ServicioAdminMedico(repoFake);
    }

    // ── TEST 1: Parámetros — campos obligatorios vacíos ────────────────────
    @ParameterizedTest(name = "Campo vacío: {0}, {1}, {2}")
    @CsvSource({
            "'', Neurología, MED-010",
            "Dr. Pedro, '', MED-010",
            "Dr. Pedro, Neurología, ''"
    })
    void registrarMedico_conCampoObligatorioVacio_lanzaExcepcion(
            String nombre, String especialidad, String nroLicencia) {

        Map<String, String> datos = new HashMap<>();
        datos.put("nombre",      nombre);
        datos.put("especialidad",especialidad);
        datos.put("nroLicencia", nroLicencia);

        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrarMedico(datos),
                "Debe lanzar excepción cuando un campo obligatorio está vacío");
    }

    // ── TEST 2: Licencia duplicada ─────────────────────────────────────────
    @Test
    void registrarMedico_conLicenciaDuplicada_lanzaIllegalArgumentException() {
        Medico existente = new Medico(
                "Dr. Existente", "exist@mail.com", "pass", "Cardiología", "MED-001");
        repoFake = new RepoMedicoFake(existente);
        servicio  = new ServicioAdminMedico(repoFake);

        Map<String, String> datos = new HashMap<>();
        datos.put("nombre",      "Dr. Nuevo");
        datos.put("especialidad","Pediatría");
        datos.put("nroLicencia", "MED-001");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> servicio.registrarMedico(datos));

        assertTrue(ex.getMessage().toLowerCase().contains("licencia"),
                "El mensaje debe mencionar 'licencia'");

        assertNull(repoFake.medicoGuardado,
                "No debe haberse guardado ningún médico");
    }
}
