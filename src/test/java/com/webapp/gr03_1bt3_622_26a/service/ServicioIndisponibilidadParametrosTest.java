package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioIndisponibilidad;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioIndisponibilidadParametrosTest {

    private static class RepoIndispFake extends RepositorioIndisponibilidad {
        @Override
        public com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad guardar(
                com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad i) {
            return i;
        }
    }

    private static class RepoCitaFake extends RepositorioCita {
        @Override
        public com.webapp.gr03_1bt3_622_26a.model.Cita
        buscarCitaMasProximaEnFecha(int medicoId, String fecha) {
            return null; // sin citas para no interferir con la prueba
        }
    }

    private static class RepoBloqueVacioFake extends RepositorioBloqueHorario {
        @Override
        public java.util.List<com.webapp.gr03_1bt3_622_26a.model.BloqueHorario>
        buscarPorMedicoYFecha(int medicoId, String fecha) {
            return java.util.Collections.emptyList();
        }
    }

    // RED: falla porque ServicioIndisponibilidad no existe aun
    @ParameterizedTest(name = "Campos invalidos: fecha={0}, motivo={1}")
    @CsvSource({
            "'', Enfermedad",
            "2026-05-11, ''",
            "'', ''"
    })
    void registrarIndisponibilidad_conCampoVacio_lanzaIllegalArgumentException(
            String fecha, String motivo) {

        ServicioIndisponibilidad servicio = new ServicioIndisponibilidad(
                new RepoIndispFake(),
                new RepoCitaFake(),
                new RepoBloqueVacioFake());

        Medico medico = new Medico(
                "Dr. Params", "params@mail.com", "pass",
                "Neurología", "MED-PR");

        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrarIndisponibilidad(medico, fecha, motivo),
                "Debe lanzar IllegalArgumentException cuando un campo esta vacio");
    }
}