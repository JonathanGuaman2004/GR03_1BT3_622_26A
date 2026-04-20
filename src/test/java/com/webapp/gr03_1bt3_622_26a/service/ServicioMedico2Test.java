package com.webapp.gr03_1bt3_622_26a.service;

import static org.junit.jupiter.api.Assertions.*;
        import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;

import java.util.Arrays;
import java.util.List;

public class ServicioMedico2Test {

    private ServicioMedico2 servicioMedico2;

    @BeforeEach
    void setUp() {
        Medico medico1 = new Medico("Dr. López",  "lopez@mail.com",  "pass", "Cardiología", "LIC-001");
        Medico medico2 = new Medico("Dra. Pérez", "perez@mail.com",  "pass", "Cardiología", "LIC-002");
        Medico medico3 = new Medico("Dr. García", "garcia@mail.com", "pass", "Pediatría",   "LIC-003");

        RepositorioMedico repoFake = new RepositorioMedico() {
            @Override
            public List<Medico> listar() {
                return Arrays.asList(medico1, medico2, medico3);
            }
        };

        servicioMedico2 = new ServicioMedico2(repoFake);
    }

    @Test
    void given_a_specialty_when_buscarMedicosPorEspecialidad_then_return_matching_doctors() {
        String especialidad = "Cardiología";

        List<Medico> medicos = servicioMedico2.buscarMedicosPorEspecialidad(especialidad);

        assertNotNull(medicos, "La lista de médicos no debe ser null");
        assertFalse(medicos.isEmpty(), "La lista debe contener al menos un médico de Cardiología");
        assertTrue(
                medicos.stream().allMatch(m -> especialidad.equals(m.getEspecialidad())),
                "Todos los médicos deben tener especialidad Cardiología"
        );
    }

    @Test
    void given_a_nonexistent_specialty_when_buscarMedicosPorEspecialidad_then_return_empty_list() {
        String especialidad = "Especialidad_Inexistente";

        List<Medico> medicos = servicioMedico2.buscarMedicosPorEspecialidad(especialidad);

        assertNotNull(medicos, "La lista no debe ser null");
        assertTrue(medicos.isEmpty(), "La lista debe estar vacía para especialidad inexistente");
    }
}