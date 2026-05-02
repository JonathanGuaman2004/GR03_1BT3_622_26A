package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import java.util.UUID;

public class CredencialesUtil {

    private CredencialesUtil() {}

    public static void generarCredenciales(Medico medico) {
        String nombreBase = medico.getNombre()
                .replaceAll("\\s+", ".")
                .toLowerCase()
                .replaceAll("[^a-z.]", "");

        String email = "medico." + nombreBase + "@medicitas.com";

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String password = "Med@" + uuid.substring(0, 6) + "!";

        medico.setEmail(email);
        medico.setPassword(password);
    }
}