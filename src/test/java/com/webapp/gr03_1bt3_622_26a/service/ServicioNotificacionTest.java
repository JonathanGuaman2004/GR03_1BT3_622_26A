package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test de ServicioNotificacion usando Spy.
 * Verifica que enviarConfirmacion() y enviarAMedico()
 * invocan enviar() exactamente una vez cada uno.
 */
class ServicioNotificacionTest {

    private ServicioNotificacion servicio;
    private Cita citaPrueba;

    @BeforeEach
    void setUp() {
        servicio = Mockito.spy(new ServicioNotificacion());

        Paciente paciente = new Paciente(
                "Juan Pérez",
                "jonathanbatalla2004@gmail.com",
                "password123",
                "0987654321",
                "1712345678"
        );

        Medico medico = new Medico(
                "Dr. Carlos Mendoza",
                "jonathan.guaman04@epn.edu.ec",
                "med123",
                "Medicina General",
                "MED-001"
        );

        // Nuevo constructor: BloqueHorario en lugar de LocalDate/LocalTime
        BloqueHorario bloque = new BloqueHorario(medico, "2025-06-15", "09:00", "09:30");

        citaPrueba = new Cita(paciente, medico, bloque, "Consulta de control", paciente);
    }

    @Test
    void give_data_when_confirmation_then_sendToPatient() throws Exception {
        servicio.enviarConfirmacion(citaPrueba);
        verify(servicio, times(1)).enviar(any(MimeMessage.class));
    }

    @Test
    void give_data_when_confirmation_then_sendToMedic() throws Exception {
        servicio.enviarAMedico(citaPrueba);
        verify(servicio, times(1)).enviar(any(MimeMessage.class));
    }
}