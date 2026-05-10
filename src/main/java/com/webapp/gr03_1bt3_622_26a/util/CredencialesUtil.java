package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import java.util.UUID;

public class CredencialesUtil {

    private CredencialesUtil() {}

    public static void generarCredenciales(Medico medico) {
        // El email ya viene registrado en el médico desde el formulario de registro.
        // Solo se genera la contraseña temporal.
        String tokenUuid          = UUID.randomUUID().toString().replace("-", "");
        String fragmentoAleatorio = tokenUuid.substring(0, 6);
        String password           = "Med@" + fragmentoAleatorio + "!";

        medico.setPassword(password);
    }
}