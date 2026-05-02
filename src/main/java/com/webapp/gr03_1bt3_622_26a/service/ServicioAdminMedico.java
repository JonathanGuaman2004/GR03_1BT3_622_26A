package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.util.CredencialesUtil;

import java.util.Map;

public class ServicioAdminMedico {

    private final RepositorioMedico repoMedico;
    private final RepositorioCita   repoCita;

    public ServicioAdminMedico() {
        this.repoMedico = new RepositorioMedico();
        this.repoCita   = new RepositorioCita();
    }

    public ServicioAdminMedico(RepositorioMedico repoMedico) {
        this.repoMedico = repoMedico;
        this.repoCita   = new RepositorioCita();
    }

    public ServicioAdminMedico(RepositorioMedico repoMedico,
                               RepositorioCita repoCita) {
        this.repoMedico = repoMedico;
        this.repoCita   = repoCita;
    }

    // ── HU-05A ───────────────────────────────────────────────────────────

    public Medico registrarMedico(Map<String, String> datos) {
        String nombre       = extraer(datos, "nombre");
        String especialidad = extraer(datos, "especialidad");
        String nroLicencia  = extraer(datos, "nroLicencia");

        validarRequerido(nombre,       "El nombre es obligatorio.");
        validarRequerido(especialidad, "La especialidad es obligatoria.");
        validarRequerido(nroLicencia,  "El número de licencia es obligatorio.");
        validarLicenciaUnica(nroLicencia);

        Medico medico = new Medico(
                nombre, null, null, especialidad, nroLicencia);
        medico.setEstado("ACTIVO");
        return repoMedico.guardar(medico);
    }

    // ── HU-05B ───────────────────────────────────────────────────────────

    public Medico generarCredenciales(int medicoId) {
        Medico medico = repoMedico.buscarPorId(medicoId);
        if (medico == null)
            throw new IllegalArgumentException("Médico no encontrado.");

        CredencialesUtil.generarCredenciales(medico);
        medico.setDebeCambiarPwd(1);
        return repoMedico.actualizar(medico);
    }

    // ── HU-06 ────────────────────────────────────────────────────────────

    public void suspenderMedico(int medicoId) {
        Medico medico = repoMedico.buscarPorId(medicoId);
        if (medico == null)
            throw new IllegalArgumentException("Médico no encontrado.");

        long citasActivas = repoCita.contarCitasActivasPorMedico(medicoId);
        if (citasActivas > 0)
            throw new IllegalStateException(
                    "El médico tiene " + citasActivas +
                            " cita(s) activa(s). Reagende o cancele antes de suspender.");

        repoMedico.actualizarEstado(medicoId, "SUSPENDIDO");
    }

    // ── Utilidades privadas ───────────────────────────────────────────────

    private String extraer(Map<String, String> datos, String clave) {
        return datos.getOrDefault(clave, "").trim();
    }

    private void validarRequerido(String valor, String mensaje) {
        if (valor.isEmpty()) throw new IllegalArgumentException(mensaje);
    }

    private void validarLicenciaUnica(String nroLicencia) {
        if (repoMedico.buscarPorNroLicencia(nroLicencia) != null)
            throw new IllegalArgumentException(
                    "Ya existe un médico con ese número de licencia.");
    }
}