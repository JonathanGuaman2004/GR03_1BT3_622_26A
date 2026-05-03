package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;

public class ServicioAuth {

    private final RepositorioPaciente repoPaciente = new RepositorioPaciente();
    private final RepositorioMedico   repoMedico   = new RepositorioMedico();

    public Usuario autenticar(String email, String clave) {
        if (email == null || email.isBlank() || clave == null || clave.isBlank()) {
            throw new IllegalArgumentException(
                    "Correo y contraseña son obligatorios.");
        }

        Paciente paciente = repoPaciente.buscarPorEmail(email);
        if (paciente != null && paciente.validarPassword(clave)) {
            verificarCuentaActiva(paciente);
            return paciente;
        }

        Medico medico = repoMedico.buscarPorEmail(email);
        if (medico != null && medico.validarPassword(clave)) {
            verificarCuentaActiva(medico);
            return medico;
        }

        Administrador admin = buscarAdministrador(email);
        if (admin != null && admin.validarPassword(clave)) {
            return admin;
        }
        throw new IllegalArgumentException(
                "Credenciales incorrectas. Verifica tu correo y contraseña.");
    }

    private void verificarCuentaActiva(Usuario usuario) {
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new IllegalArgumentException(
                    "Tu cuenta está suspendida. Contacta al administrador.");
        }
    }

    private Administrador buscarAdministrador(String email) {
        try (org.hibernate.Session s =
                     com.webapp.gr03_1bt3_622_26a.util.HibernateUtil
                             .getSessionFactory().openSession()) {
            return s.createQuery(
                            "FROM Administrador WHERE email = :email",
                            Administrador.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
}