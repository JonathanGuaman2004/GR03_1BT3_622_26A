package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;
import java.util.Map;

public class ServicioPaciente {

    private final RepositorioPaciente repo = new RepositorioPaciente();

    public Paciente registrar(Map<String, String> datos) {
        validarDatos(datos);

        if (repo.existeEmail(datos.get("email"))) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        Paciente p = new Paciente(
                datos.get("nombre"),
                datos.get("email"),
                datos.get("password"),
                datos.get("telefono"),
                datos.get("cedula")
        );
        return repo.guardar(p);
    }

    public boolean validarDatos(Map<String, String> datos) {
        requerido(datos, "nombre",   "El nombre es obligatorio.");
        requerido(datos, "email",    "El correo es obligatorio.");
        requerido(datos, "password", "La contraseña es obligatoria.");
        requerido(datos, "cedula",   "La cédula es obligatoria.");

        if (!datos.get("email").contains("@") || !datos.get("email").contains(".")) {
            throw new IllegalArgumentException(
                    "El correo no tiene un formato válido.");
        }

        if (datos.get("password").length() < 6) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 6 caracteres.");
        }
        return true;
    }

    public Paciente buscarPorEmail(String email) {
        return repo.buscarPorEmail(email);
    }

    public Paciente buscarPorId(int id) {
        return repo.buscarPorId(id);
    }

    public Paciente buscarPorCedula(String cedula) {
        return repo.buscarPorCedula(cedula);
    }

    private void requerido(Map<String, String> datos, String campo, String msg) {
        String val = datos.get(campo);
        if (val == null || val.trim().isEmpty()) {
            throw new IllegalArgumentException(msg);
        }
    }
}