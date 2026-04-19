package com.webapp.gr03_1bt3_622_26a.service;

import java.util.UUID;

public class ServicioAdmin {

    /**
     * Genera credenciales para un médico nuevo.
     * @param nombre      Nombre del médico
     * @param especialidad Especialidad médica
     * @return arreglo de String: [0] = usuario generado, [1] = contraseña generada
     */
    public String[] generarCredenciales(String nombre, String especialidad) {
        // Sufijo: primeras 4 letras de la especialidad en minúsculas
        String sufijo = especialidad.toLowerCase().substring(0, 4);

        // Usuario: primera parte del nombre (sin espacios) + sufijo especialidad
        String nombreBase = nombre.split(" ")[0].toLowerCase();
        String usuario = nombreBase + "_" + sufijo;

        // Contraseña: letras + números (cumple reglas de seguridad)
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String password = "Med" + uuid.substring(0, 5) + "2024";

        return new String[]{usuario, password};
    }
}
