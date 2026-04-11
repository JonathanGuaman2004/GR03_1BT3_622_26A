package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;

/**
 * Lógica de autenticación.
 * Corresponde a UC2: Iniciar Sesión.
 */
public class ServicioAuth {

    private final RepositorioPaciente repoPaciente = new RepositorioPaciente();
    private final RepositorioMedico   repoMedico   = new RepositorioMedico();

    /**
     * Autentica a un usuario por email y contraseña.
     * Busca primero entre pacientes y luego entre médicos.
     *
     * @return el Usuario autenticado
     * @throws IllegalArgumentException si las credenciales son incorrectas
     */
    public Usuario autenticar(String email, String clave) {
        if (email == null || email.isBlank() || clave == null || clave.isBlank()) {
            throw new IllegalArgumentException("Correo y contraseña son obligatorios.");
        }

        // Buscar como paciente
        Paciente paciente = repoPaciente.buscarPorEmail(email);
        if (paciente != null && paciente.validarPassword(clave)) {
            return paciente;
        }

        // Buscar como médico
        Medico medico = repoMedico.buscarPorEmail(email);
        if (medico != null && medico.validarPassword(clave)) {
            return medico;
        }

        throw new IllegalArgumentException("Credenciales incorrectas. Verifica tu correo y contraseña.");
    }

    /**
     * Valida que un token de sesión (userId almacenado en HttpSession) sea válido.
     * Retorna true si existe un usuario con ese id.
     */
    public boolean validarToken(String userIdToken) {
        if (userIdToken == null || userIdToken.isBlank()) return false;
        try {
            int id = Integer.parseInt(userIdToken);
            return repoPaciente.buscarPorId(id) != null
                    || repoMedico.buscarPorId(id) != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
