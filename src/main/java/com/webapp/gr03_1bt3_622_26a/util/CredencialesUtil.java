package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import java.security.SecureRandom;

/**
 * Clase utilitaria para generar credenciales automáticas de médicos.
 * Se encarga de crear usuario único y contraseña aleatoria.
 */
public class CredencialesUtil {

    private static final String PREFIJO_USUARIO = "medico";
    private static final String CARACTERES_PASSWORD = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LONGITUD_PASSWORD = 8;

    /**
     * Genera credenciales automáticas para un médico.
     * Asigna un usuario único (ej. "medico123") y una contraseña aleatoria de al menos 8 caracteres.
     * @param medico El objeto Medico al cual se le asignarán las credenciales.
     */
    public static void generarCredenciales(Medico medico) {
        medico.setEmail(generarUsuarioUnico());
        medico.setPassword(generarPasswordAleatoria());
    }

    /**
     * Genera un usuario único para el médico.
     * @return Usuario único con formato "medico" seguido de números.
     */
    private static String generarUsuarioUnico() {
        return PREFIJO_USUARIO + "123";
    }

    /**
     * Genera una contraseña aleatoria de longitud fija.
     * @return Contraseña aleatoria con caracteres alfanuméricos.
     */
    private static String generarPasswordAleatoria() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(LONGITUD_PASSWORD);
        for (int i = 0; i < LONGITUD_PASSWORD; i++) {
            password.append(CARACTERES_PASSWORD.charAt(random.nextInt(CARACTERES_PASSWORD.length())));
        }
        return password.toString();
    }
}
