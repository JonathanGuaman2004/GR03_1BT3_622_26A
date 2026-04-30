package com.webapp.gr03_1bt3_622_26a.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.webapp.gr03_1bt3_622_26a.model.Medico;

public class CredencialesUtilTest {

    @Test
    void given_a_medico_when_generarCredenciales_then_usuario_and_password_are_set() {
        // Given: Un médico sin credenciales
        Medico medico = new Medico("Dr. Juan Pérez", null, null, "Cardiología", "LIC12345");

        // When: Se genera las credenciales (usuario único y contraseña aleatoria)
        CredencialesUtil.generarCredenciales(medico);

        // Then: El usuario debe ser único (ej. "medico123") y la contraseña aleatoria de al menos 8 caracteres
        assertNotNull(medico.getEmail(), "El usuario no debe ser null");
        assertTrue(medico.getEmail().startsWith("medico"), "El usuario debe comenzar con 'medico'");
        assertTrue(medico.getEmail().matches("medico\\d+"), "El usuario debe tener el formato 'medico' seguido de números");
        assertNotNull(medico.getPassword(), "La contraseña no debe ser null");
        assertTrue(medico.getPassword().length() >= 8, "La contraseña debe tener al menos 8 caracteres");
    }
}
