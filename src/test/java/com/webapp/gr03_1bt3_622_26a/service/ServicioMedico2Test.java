package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        List<Medico> medicos = servicioMedico2.buscarMedicosPorEspecialidad("Cardiología");

        assertNotNull(medicos);
        assertFalse(medicos.isEmpty());
        assertTrue(medicos.stream().allMatch(m -> "Cardiología".equals(m.getEspecialidad())));
    }

    @Test
    void given_a_nonexistent_specialty_when_buscarMedicosPorEspecialidad_then_return_empty_list() {
        List<Medico> medicos = servicioMedico2.buscarMedicosPorEspecialidad("Especialidad_Inexistente");

        assertNotNull(medicos);
        assertTrue(medicos.isEmpty());
    }
}