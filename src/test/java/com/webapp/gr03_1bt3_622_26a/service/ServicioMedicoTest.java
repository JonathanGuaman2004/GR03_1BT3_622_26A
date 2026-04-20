package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioMedicoTest {

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
        String nombre_existente = "Dra. Elena Ramírez";
        String email_inicial = "elena.ramirez@hospital.com";
        String password_inicial = "Segura9876";
        String especialidad_inicial = "Pediatría";
        String licencia_inicial = "LIC-98765";

        String email_duplicado = "otro.email@hospital.com";
        String password_duplicado = "Clave5678";
        String especialidad_duplicada = "Neurologia";
        String licencia_duplicada = "LIC-99999";

        servicio.ingresarMedico(nombre_existente, email_inicial, password_inicial, especialidad_inicial, licencia_inicial);

        assertThrows(RuntimeException.class,
                () -> servicio.ingresarMedico(nombre_existente, email_duplicado, password_duplicado,
                        especialidad_duplicada, licencia_duplicada),
                "Si el nombre ya existe, debe lanzar una excepcion");
    }
}

