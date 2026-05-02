package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioAdminMedicoInteraccionTest {

    @Mock
    private RepositorioMedico repoMedico;

    // ── TEST 4: Verifica interacción exacta con RepositorioMedico ─────────
    @Test
    void generarCredenciales_verificaInteraccionExactaConRepositorio() {
        Medico medicoSinCredenciales = new Medico(
                "Dr. Ana Salazar", null, null, "Pediatría", "MED-006");

        when(repoMedico.buscarPorId(2)).thenReturn(medicoSinCredenciales);
        when(repoMedico.actualizar(any(Medico.class)))
                .thenReturn(medicoSinCredenciales);

        ServicioAdminMedico servicio = new ServicioAdminMedico(repoMedico);
        servicio.generarCredenciales(2);

        verify(repoMedico, times(1)).buscarPorId(2);
        verify(repoMedico, times(1)).actualizar(any(Medico.class));
        verifyNoMoreInteractions(repoMedico);
    }
}