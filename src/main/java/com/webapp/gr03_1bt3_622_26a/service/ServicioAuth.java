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
            if (!"ACTIVO".equals(paciente.getEstado())) {
                throw new IllegalArgumentException(
                        "Tu cuenta está suspendida. Contacta al administrador.");
            }
            return paciente;
        }

        Medico medico = repoMedico.buscarPorEmail(email);
        if (medico != null && medico.validarPassword(clave)) {
            if (!"ACTIVO".equals(medico.getEstado())) {
                throw new IllegalArgumentException(
                        "Tu cuenta está suspendida. Contacta al administrador.");
            }
            return medico;
        }

        // Buscar administrador
        try (org.hibernate.Session s =
                     com.webapp.gr03_1bt3_622_26a.util.HibernateUtil
                             .getSessionFactory().openSession()) {
            Administrador admin = s.createQuery(
                            "FROM Administrador WHERE email = :email",
                            Administrador.class)
                    .setParameter("email", email)
                    .uniqueResult();
            if (admin != null && admin.validarPassword(clave)) {
                return admin;
            }
        } catch (Exception ignored) {}

        throw new IllegalArgumentException(
                "Credenciales incorrectas. Verifica tu correo y contraseña.");
    }
}