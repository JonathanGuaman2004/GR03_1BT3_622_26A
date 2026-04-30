package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CredencialesUtilTest {

    @Test
    void given_a_medico_when_generarCredenciales_then_usuario_and_password_are_set() {
        Medico medico = new Medico("Dr. Juan Pérez", null, null, "Cardiología", "LIC12345");

        CredencialesUtil.generarCredenciales(medico);

        assertNotNull(medico.getEmail(), "El usuario no debe ser null");
        assertTrue(medico.getEmail().startsWith("medico"));
        assertTrue(medico.getEmail().matches("medico\\d+"));
        assertNotNull(medico.getPassword());
        assertTrue(medico.getPassword().length() >= 8);
    }
}