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
        Medico guardado = servicio.ingresarMedico("Dr. Juan Perez", "Cardiologia");

        assertNotNull(guardado, "Debe retornar un medico guardado");
        assertTrue(guardado.getId() > 0, "El ID generado debe ser mayor a 0");
        assertEquals("Dr. Juan Perez", guardado.getNombre());
        assertEquals("Cardiologia", guardado.getEspecialidad());
    }

    @Test
    void given_nombre_ya_existente_when_ingresar_medico_then_return_exception() {
        servicio.ingresarMedico("Dr. Juan Perez", "Cardiologia");

        assertThrows(RuntimeException.class,
                () -> servicio.ingresarMedico("Dr. Juan Perez", "Neurologia"),
                "Si el nombre ya existe, debe lanzar una excepcion");
    }
}

