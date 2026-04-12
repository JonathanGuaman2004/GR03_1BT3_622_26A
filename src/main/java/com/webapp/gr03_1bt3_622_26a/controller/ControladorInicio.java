package com.webapp.gr03_1bt3_622_26a.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controlador para la pantalla de inicio/bienvenida post-login.
 * GET /inicio → muestra el dashboard de bienvenida
 */
@WebServlet(name = "ControladorInicio", urlPatterns = "/inicio")
public class ControladorInicio extends HttpServlet {

    /**
     * Maneja peticiones GET a /inicio. 
     * Verifica que el usuario esté autenticado (tenga sesión activa con usuarioId).
     * Si no está autenticado, lo redirige al login.
     * Si está autenticado, muestra la página de inicio (dashboard de bienvenida).
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Verificar sesión activa
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(req, res);
    }
}
