package com.webapp.gr03_1bt3_622_26a.service;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Test parametrizado — TDD ciclo Red-Green-Refactor
 * Valida generarCredenciales() con diferentes especialidades.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServicioAdminTest {

    private ServicioAdmin servicio;

    @BeforeAll
    void inicializar() {
        servicio = new ServicioAdmin(); // ← se instancia UNA sola vez para los 3 casos
    }

    @ParameterizedTest(name = "Especialidad: {1}")
    @CsvSource({
            "Carlos Lopez, Cardiologia",
            "Maria Torres, Pediatria",
            "Luis Herrera, Dermatologia"
    })
    void testGenerarCredenciales_usuarioYPasswordValidos(String nombre, String especialidad) {

        String[] credenciales = servicio.generarCredenciales(nombre, especialidad);

        assertNotNull(credenciales, "Las credenciales no deben ser null");
        assertEquals(2, credenciales.length, "Debe retornar [usuario, password]");

        String usuario  = credenciales[0];
        String password = credenciales[1];

        String sufijoEsperado = especialidad.toLowerCase().substring(0, 4);

        assertAll("Validaciones de credenciales para: " + especialidad,  // ← agrupa asserts
                () -> assertTrue(usuario.contains(sufijoEsperado),
                        "Usuario debe incluir sufijo: " + sufijoEsperado),
                () -> assertTrue(password.length() >= 8,
                        "Contraseña debe tener al menos 8 caracteres"),
                () -> assertTrue(password.matches(".*[0-9].*"),
                        "Contraseña debe contener número"),
                () -> assertTrue(password.matches(".*[a-zA-Z].*"),
                        "Contraseña debe contener letra")
        );
    }
}