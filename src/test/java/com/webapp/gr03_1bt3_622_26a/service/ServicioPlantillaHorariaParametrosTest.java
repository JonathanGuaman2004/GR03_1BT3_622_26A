package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ServicioPlantillaHorariaParametrosTest {

    // Fake mínimo para que no explote — solo existe para que compile
    private static class RepoFakeVacio extends RepositorioPlantillaHoraria {
        @Override
        public boolean existeDuplicado(int medicoId, String dia, String hora) {
            return false;
        }
    }

    @ParameterizedTest(name = "Campos invalidos: dia={0}, horaInicio={1}, horaFin={2}")
    @CsvSource({
            "'', 08:00, 12:00",
            "LUNES, '', 12:00",
            "LUNES, 08:00, ''"
    })
    void agregarFranja_conCampoVacio_lanzaIllegalArgumentException(
            String dia, String horaInicio, String horaFin) {

        ServicioPlantillaHoraria servicio =
                new ServicioPlantillaHoraria(new RepoFakeVacio());

        Medico medico = new Medico(
                "Dr. Test", "test@mail.com", "pass", "Cardiología", "MED-T1");

        assertThrows(IllegalArgumentException.class,
                () -> servicio.agregarFranja(medico, dia, horaInicio, horaFin),
                "Debe lanzar IllegalArgumentException cuando un campo esta vacio");
    }
}