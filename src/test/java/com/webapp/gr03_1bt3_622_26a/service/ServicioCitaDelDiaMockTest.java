package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioCitaDelDiaMockTest {

    @Mock
    private RepositorioCita repoCitaMock;

    // RED: falla porque getCitasDelDiaPorMedico no existe aún
    // o porque el constructor con inyección no existe
    @Test
    void getCitasDelDiaPorMedico_verificaInteraccionExactaConRepositorio() {
        String fechaHoy = LocalDate.now().toString();
        int    medicoId = 5;

        when(repoCitaMock.buscarPorMedicoYFecha(medicoId, fechaHoy))
                .thenReturn(Collections.emptyList());

        ServicioCita servicio = new ServicioCita(repoCitaMock);
        List<Cita> resultado = servicio.getCitasDelDiaPorMedico(medicoId);

        // Verificacion de interaccion
        verify(repoCitaMock, times(1))
                .buscarPorMedicoYFecha(
                        eq(medicoId),
                        eq(fechaHoy));
        verifyNoMoreInteractions(repoCitaMock);

        // Verificacion del resultado
        org.junit.jupiter.api.Assertions.assertAll(
                "Verificaciones post-ejecucion de getCitasDelDiaPorMedico",
                () -> org.junit.jupiter.api.Assertions.assertNotNull(
                        resultado, "El resultado nunca debe ser null"),
                () -> org.junit.jupiter.api.Assertions.assertEquals(
                        Collections.emptyList(), resultado,
                        "El resultado debe coincidir con lo retornado por el mock")
        );
    }
}

