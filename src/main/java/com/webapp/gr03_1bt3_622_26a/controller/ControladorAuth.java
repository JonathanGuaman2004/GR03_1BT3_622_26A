package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ControladorAuth", urlPatterns = "/login")
public class ControladorAuth extends ControladorBase {

    private ServicioAuth servicio;

    @Override
    public void init() { servicio = new ServicioAuth(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if ("logout".equals(req.getParameter("action"))) {
            cerrarSesion(req, res);
            return;
        }
        forward(req, res, "/WEB-INF/views/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        String email = trim(req, "email");
        String clave = trim(req, "password");

        try {
            Usuario usuario = servicio.autenticar(email, clave);
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioId",  usuario.getId());
            session.setAttribute("usuarioNom", usuario.getNombre());
            session.setAttribute("usuarioRol", usuario.getRol());

            // Redirigir según rol
            String ctx = req.getContextPath();
            switch (usuario.getRol()) {
                case "ADMINISTRADOR" ->
                        res.sendRedirect(ctx + "/admin/dashboard");
                case "MEDICO" -> {
                    if (usuario.isDebeCambiarPwd()) {
                        res.sendRedirect(ctx + "/cambiar-password");
                    } else {
                        res.sendRedirect(ctx + "/medico/dashboard");
                    }
                }
                default -> res.sendRedirect(ctx + "/paciente/dashboard");
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            forward(req, res, "/WEB-INF/views/login.jsp");
        }
    }

    private void cerrarSesion(HttpServletRequest req,
                              HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        res.sendRedirect(req.getContextPath() + "/login");
    }
}