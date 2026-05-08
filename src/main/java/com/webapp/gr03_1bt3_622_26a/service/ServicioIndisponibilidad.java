package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioIndisponibilidad;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ServicioIndisponibilidad {

    private final RepositorioIndisponibilidad repoIndisp;
    private final RepositorioCita             repoCita;
    private final RepositorioBloqueHorario    repoBloque;

    public ServicioIndisponibilidad() {
        this.repoIndisp = new RepositorioIndisponibilidad();
        this.repoCita   = new RepositorioCita();
        this.repoBloque = new RepositorioBloqueHorario();
    }

    public ServicioIndisponibilidad(RepositorioIndisponibilidad repoIndisp,
                                    RepositorioCita repoCita,
                                    RepositorioBloqueHorario repoBloque) {
        this.repoIndisp = repoIndisp;
        this.repoCita   = repoCita;
        this.repoBloque = repoBloque;
    }

    private void validarCampo(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El campo '" + nombreCampo + "' es obligatorio.");
        }
    }

    private void validarCamposObligatorios(String fecha, String motivo) {
        validarCampo(fecha,  "fecha");
        validarCampo(motivo, "motivo");
    }


    public Indisponibilidad registrarIndisponibilidad(Medico medico,
                                                      String fecha,
                                                      String motivo) {
        validarCamposObligatorios(fecha, motivo);
        Cita citaMasProxima = repoCita.buscarCitaMasProximaEnFecha(
                medico.getId(), fecha);
        verificarAnticipacionSuficiente(citaMasProxima);

        int bloqueados = bloquearHorariosDelDia(medico.getId(), fecha);
        System.out.println("[Indisponibilidad] Bloques bloqueados: " + bloqueados);
        Indisponibilidad indisp = new Indisponibilidad(medico, fecha, motivo, medico);
        return repoIndisp.guardar(indisp);

    }

    private int bloquearHorariosDelDia(int medicoId, String fecha) {
        List<BloqueHorario> bloques =
                repoBloque.buscarPorMedicoYFecha(medicoId, fecha);
        int totalBloqueados = 0;
        for (BloqueHorario bloque : bloques) {
            bloque.setEstado(BloqueHorario.BLOQUEADO);
            repoBloque.actualizar(bloque);
            totalBloqueados++;
        }
        return totalBloqueados;
    }

    private long calcularMinutosHastaCita(Cita cita) {
        LocalTime horaCita = LocalTime.parse(cita.getBloque().getHoraInicio());
        LocalTime ahora    = LocalDateTime.now().toLocalTime();
        return java.time.Duration.between(ahora, horaCita).toMinutes();
    }

    private void verificarAnticipacionSuficiente(Cita citaMasProxima) {
        if (citaMasProxima == null) return;
        long minutosRestantes = calcularMinutosHastaCita(citaMasProxima);
        if (minutosRestantes < 120) {
            throw new IllegalStateException(
                    "No se puede registrar indisponibilidad. La cita más "
                            + "próxima afectada ocurre en menos de 2 horas. "
                            + "Contacte al administrador.");
        }
    }
}