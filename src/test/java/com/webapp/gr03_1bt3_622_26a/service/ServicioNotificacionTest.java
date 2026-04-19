package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test de ServicioNotificacion usando Spy (Opción C).
 */
class ServicioNotificacionTest {

    private ServicioNotificacion servicio;
    private Cita citaPrueba;

    @BeforeEach
    void setUp() {
        /*
         * Spy: crea un objeto real de ServicioNotificacion pero permite interceptar métodos específicos.
         */
        servicio = Mockito.spy(new ServicioNotificacion());

        // Construir datos de prueba
        Paciente paciente = new Paciente("Juan Pérez","jonathanbatalla2004@gmail.com", "password123","0987654321","1712345678");

        Medico medico = new Medico("Dr. Carlos Mendoza","jonathan.guaman04@epn.edu.ec","med123","Medicina General","MED-001");

        citaPrueba = new Cita(LocalDate.of(2025, 6, 15),LocalTime.of(9, 0),"Consulta de control",paciente,medico,null);
    }

    /**
     * Verifica que enviarConfirmacion llama a enviar() exactamente una vez.
     */
    @Test
    void give_data_when_confirmation_then_sendToPatien() throws Exception {
        // Evitar que se realice la conexión real a Gmail
        //doNothing().when(servicio).enviar(any(MimeMessage.class));

        // Ejecutar
        servicio.enviarConfirmacion(citaPrueba);

        // Verificar que enviar() fue llamado exactamente una vez
        verify(servicio, times(1)).enviar(any(MimeMessage.class));
    }

    /**
     * Verifica que enviarAMedico llama a enviar() exactamente una vez.
     */
    @Test
    void give_data_when_confirmation_then_sendToMedic() throws Exception {
        //doNothing().when(servicio).enviar(any(MimeMessage.class));

        servicio.enviarAMedico(citaPrueba);

        verify(servicio, times(1)).enviar(any(MimeMessage.class));
    }
}