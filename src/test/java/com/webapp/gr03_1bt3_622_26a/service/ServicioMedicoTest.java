package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioMedicoTest {

    // Ejecutar antes de correr el test
    // Set-Location "c:\Users\MY ASUS\Desktop\Proyectos\MetAgiles\Practica01"; if (Test-Path "target\test-runtime") { Remove-Item "target\test-runtime" -Recurse -Force }; .\mvnw.cmd -Dtest=ServicioMedicoTest#given_datos_validos_when_ingresar_medico_then_medico_guardado test

    private ServicioMedico servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioMedico();
    }

    @Test
    void given_datos_validos_when_ingresar_medico_then_medico_guardado() {
        String nombre = "Dr. Juan Perez";
        String email = "dr.juan.perez@hospital.com";
        String password = "Clave1234";
        String especialidad = "Cardiologia";
        String nro_licencia = "LIC-12345";

        Medico guardado = servicio.ingresarMedico(nombre, email, password, especialidad, nro_licencia);

        assertNotNull(guardado, "Debe retornar un medico guardado");
        assertTrue(guardado.getId() > 0, "El ID generado debe ser mayor a 0");
        assertEquals(nombre, guardado.getNombre());
        assertEquals(especialidad, guardado.getEspecialidad());
        assertEquals(email, guardado.getEmail());
        assertEquals(password, guardado.getPassword());
        assertEquals(nro_licencia, guardado.getNroLicencia());
    }

    @Test
    void given_nombre_ya_existente_when_ingresar_medico_then_return_exception() {
        String nombre = "Dra. Elena Ramírez";
        String email1 = "elena.ramirez@hospital.com";
        String password1 = "Segura9876";
        String especialidad1 = "Pediatría";
        String licencia1 = "LIC-98765";

        String email2 = "otro.email@hospital.com";
        String password2 = "Clave5678";
        String especialidad2 = "Neurología";
        String licencia2 = "LIC-99999";

        servicio.ingresarMedico(nombre, email1, password1, especialidad1, licencia1);

        assertThrows(RuntimeException.class,
                () -> servicio.ingresarMedico(nombre, email2, password2,
                        especialidad2, licencia2),
                "Si el nombre ya existe, debe lanzar una excepción");
    }
}