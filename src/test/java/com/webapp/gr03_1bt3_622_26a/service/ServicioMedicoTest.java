package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de ServicioMedico — verifica registro y validación de duplicados.
 * Inicializa Hibernate contra una BD SQLite temporal en target/test-runtime.
 *
 * Ejecutar limpiando BD previa:
 * Set-Location "<ruta-proyecto>"
 * if (Test-Path "target\test-runtime") { Remove-Item "target\test-runtime" -Recurse -Force }
 * .\mvnw.cmd -Dtest=ServicioMedicoTest test
 */
class ServicioMedicoTest {

    private ServicioMedico servicio;

    @BeforeEach
    void setUp() throws Exception {
        // Reiniciar Hibernate para cada test con BD limpia
        HibernateUtil.shutdown();

        File webAppRealPath = new File("target/test-runtime");
        File webInfDir      = new File(webAppRealPath, "WEB-INF");
        Files.createDirectories(webInfDir.toPath());
        HibernateUtil.init(webAppRealPath.getAbsolutePath());

        servicio = new ServicioMedico();
    }

    @Test
    void given_datos_validos_when_ingresar_medico_then_medico_guardado() {
        Medico guardado = servicio.ingresarMedico(
                "Dr. Juan Perez",
                "dr.juan.perez@hospital.com",
                "Clave1234",
                "Cardiologia",
                "LIC-12345"
        );

        assertNotNull(guardado, "Debe retornar un médico guardado");
        assertTrue(guardado.getId() > 0, "El ID debe ser mayor a 0");
        assertEquals("Dr. Juan Perez",            guardado.getNombre());
        assertEquals("Cardiologia",               guardado.getEspecialidad());
        assertEquals("dr.juan.perez@hospital.com",guardado.getEmail());
        assertEquals("Clave1234",                 guardado.getPassword());
        assertEquals("LIC-12345",                 guardado.getNroLicencia());
    }

    @Test
    void given_nombre_ya_existente_when_ingresar_medico_then_return_exception() {
        servicio.ingresarMedico(
                "Dra. Elena Ramirez",
                "elena.ramirez@hospital.com",
                "Segura9876",
                "Pediatria",
                "LIC-98765"
        );

        assertThrows(RuntimeException.class, () ->
                        servicio.ingresarMedico(
                                "Dra. Elena Ramirez",
                                "otro.email@hospital.com",
                                "Clave5678",
                                "Neurologia",
                                "LIC-99999"
                        ),
                "Si el nombre ya existe debe lanzar excepción"
        );
    }
}