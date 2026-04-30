package com.webapp.gr03_1bt3_622_26a.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SesionUtil {

    public static boolean isSesionActiva(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("usuarioId") != null;
    }

    public static int getUsuarioId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return -1;
        Object id = session.getAttribute("usuarioId");
        return id instanceof Integer ? (Integer) id : -1;
    }

    public static String getUsuarioNombre(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null ? (String) session.getAttribute("usuarioNom") : null;
    }

    public static String getUsuarioRol(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null ? (String) session.getAttribute("usuarioRol") : null;
    }

    public static void cerrarSesion(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
    }
}