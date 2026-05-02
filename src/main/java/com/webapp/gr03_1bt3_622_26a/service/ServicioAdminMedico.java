package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;

import java.util.Map;

public class ServicioAdminMedico {

    private final RepositorioMedico repoMedico;

    public ServicioAdminMedico() {
        this.repoMedico = new RepositorioMedico();
    }

    public ServicioAdminMedico(RepositorioMedico repoMedico) {
        this.repoMedico = repoMedico;
    }

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

    // ── Utilidades privadas ────────────────────────────────────────────────

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
















