package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import java.util.UUID;

public class CredencialesUtil {

    private CredencialesUtil() {}

    public static void generarCredenciales(Medico medico) {
        String nombreSinEspacios   = medico.getNombre().replaceAll("\\s+", ".");
        String nombreEnMinusculas  = nombreSinEspacios.toLowerCase();
        String nombreSoloLetras    = nombreEnMinusculas.replaceAll("[^a-z.]", "");

        String email = "medico." + nombreSoloLetras + "@medicitas.com";

        String tokenUuid           = UUID.randomUUID().toString().replace("-", "");
        String fragmentoAleatorio  = tokenUuid.substring(0, 6);
        String password            = "Med@" + fragmentoAleatorio + "!";

        medico.setEmail(email);
        medico.setPassword(password);
    }
}