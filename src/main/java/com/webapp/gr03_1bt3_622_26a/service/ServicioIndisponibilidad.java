package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioIndisponibilidad;

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

        verificarDiaLaboral(medico.getId(), fecha);
        verificarSinCitasAgendadas(medico.getId(), fecha);

        int bloqueados = bloquearHorariosDelDia(medico.getId(), fecha);
        System.out.println("[Indisponibilidad] Bloques bloqueados: " + bloqueados);

        Indisponibilidad indisp = new Indisponibilidad(medico, fecha, motivo, medico);
        return repoIndisp.guardar(indisp);
    }

    private void verificarDiaLaboral(int medicoId, String fecha) {
        List<BloqueHorario> bloquesDelDia =
                repoBloque.buscarPorMedicoYFecha(medicoId, fecha);
        if (bloquesDelDia == null || bloquesDelDia.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede registrar indisponibilidad. "
                            + "El médico no tiene horarios asignados para la fecha indicada. "
                            + "Solo puede registrar indisponibilidad en días en que trabaja.");
        }
    }

    private void verificarSinCitasAgendadas(int medicoId, String fecha) {
        List<Cita> citasDelDia = repoCita.buscarCitasEnFecha(medicoId, fecha);
        if (citasDelDia != null && !citasDelDia.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede registrar indisponibilidad. "
                            + "Tiene " + citasDelDia.size()
                            + " cita(s) agendada(s) para esa fecha. "
                            + "Contacte al administrador para reagendarlas.");
        }
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
}