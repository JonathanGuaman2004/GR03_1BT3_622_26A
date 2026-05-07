package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioHorarioPublicacionMockTest {

    @Mock
    private RepositorioBloqueHorario repoBloqueMock;

    @Mock
    private RepositorioPlantillaHoraria repoPlantillaMock;

    @Test
    void publicarBloques_verificaInteraccionExactaConRepositorio() {
        Medico medico = new Medico(
                "Dr. Mock", "mock@mail.com", "pass",
                "Cardiología", "MED-MK");

        BloqueHorario b1 = new BloqueHorario(
                medico, "2026-05-11", "08:00", "08:30");
        BloqueHorario b2 = new BloqueHorario(
                medico, "2026-05-11", "08:30", "09:00");
        // b1 y b2 no publicados (publicado=0 por defecto)

        List<BloqueHorario> bloques = Arrays.asList(b1, b2);

        when(repoBloqueMock.buscarPorMedico(medico.getId()))
                .thenReturn(bloques);
        when(repoBloqueMock.actualizar(any(BloqueHorario.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        ServicioHorario servicio = new ServicioHorario(
                repoPlantillaMock, repoBloqueMock);

        Usuario admin = new com.webapp.gr03_1bt3_622_26a.model.Administrador(
                "Admin", "admin@mail.com", "admin123");

        List<BloqueHorario> publicados = servicio.publicarBloques(
                medico.getId(), "2026-05-11", "2026-05-11", admin);

        // Verificar interacciones exactas
        verify(repoBloqueMock, times(1)).buscarPorMedico(medico.getId());
        verify(repoBloqueMock, times(2)).actualizar(any(BloqueHorario.class));
        verifyNoMoreInteractions(repoBloqueMock);
        verifyNoInteractions(repoPlantillaMock);

        org.junit.jupiter.api.Assertions.assertEquals(2, publicados.size(),
                "Deben haberse publicado los 2 bloques");
    }
}