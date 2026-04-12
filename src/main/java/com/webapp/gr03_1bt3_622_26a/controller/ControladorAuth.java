package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Controlador para UC2: Iniciar / Cerrar Sesión.
 * GET  /login        → muestra el formulario de login
 * POST /login        → procesa la autenticación
 * GET  /login?action=logout → cierra la sesión
 */
@WebServlet(name = "ControladorAuth", urlPatterns = "/login")
public class ControladorAuth extends HttpServlet {

    private ServicioAuth servicio;

    @Override
    public void init() throws ServletException {
        servicio = new ServicioAuth();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if ("logout".equals(req.getParameter("action"))) {
            cerrarSesion(req, res);
            return;
        }
        forward(req, res, "/WEB-INF/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email = trim(req, "email");
        String clave = trim(req, "password");

        try {
            Usuario usuario = servicio.autenticar(email, clave);

            // Guardar en sesión
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioId",  usuario.getId());
            session.setAttribute("usuarioNom", usuario.getNombre());
            session.setAttribute("usuarioRol", usuario.getRol());

            // Redirigir según el rol
            if ("MEDICO".equals(usuario.getRol())) {
                res.sendRedirect(req.getContextPath() + "/disponibilidad");
            } else {
                res.sendRedirect(req.getContextPath() + "/disponibilidad");
            }

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            forward(req, res, "/WEB-INF/views/login.jsp");
        }
    }

    private void cerrarSesion(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        res.sendRedirect(req.getContextPath() + "/login");
    }

    private String trim(HttpServletRequest req, String param) {
        String val = req.getParameter(param);
        return val != null ? val.trim() : "";
    }

    private void forward(HttpServletRequest req, HttpServletResponse res, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, res);
    }
}
