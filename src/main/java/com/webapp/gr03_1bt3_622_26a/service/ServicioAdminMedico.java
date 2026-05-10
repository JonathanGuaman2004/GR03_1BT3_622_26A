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
        String email        = extraer(datos, "email");
        String telefono     = extraer(datos, "telefono");
        String cedula       = extraer(datos, "cedula");

        validarRequerido(nombre,       "El nombre es obligatorio.");
        validarRequerido(especialidad, "La especialidad es obligatoria.");
        validarRequerido(nroLicencia,  "El número de licencia es obligatorio.");
        validarRequerido(email,        "El correo electrónico es obligatorio.");
        validarRequerido(cedula,       "La cédula es obligatoria.");
        validarFormatoEmail(email);
        validarLicenciaUnica(nroLicencia);
        validarEmailUnico(email);

        Medico medico = new Medico(nombre, email, null, especialidad, nroLicencia);
        medico.setTelefono(telefono);
        medico.setCedula(cedula);
        medico.setEstado("ACTIVO");
        return repoMedico.guardar(medico);
    }

    // ── HU-05B ───────────────────────────────────────────────────────────

    public Medico generarCredenciales(int medicoId) {
        Medico medico = repoMedico.buscarPorId(medicoId);
        if (medico == null)
            throw new IllegalArgumentException("Médico no encontrado.");

        if (medico.getEmail() == null || medico.getEmail().isBlank())
            throw new IllegalArgumentException(
                    "El médico no tiene correo registrado. "
                            + "Actualice el registro antes de generar credenciales.");

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

    private void validarFormatoEmail(String email) {
        if (!email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido.");
    }

    private void validarLicenciaUnica(String nroLicencia) {
        if (repoMedico.buscarPorNroLicencia(nroLicencia) != null)
            throw new IllegalArgumentException(
                    "Ya existe un médico con ese número de licencia.");
    }

    private void validarEmailUnico(String email) {
        if (repoMedico.buscarPorEmail(email) != null)
            throw new IllegalArgumentException(
                    "Ya existe un médico registrado con ese correo electrónico.");
    }
}