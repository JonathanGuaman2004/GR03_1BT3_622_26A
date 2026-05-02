package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ControladorCambiarPassword",
        urlPatterns = "/cambiar-password")
public class ControladorCambiarPassword extends ControladorBase {

    private RepositorioMedico repoMedico;

    @Override
    public void init() {
        repoMedico = new RepositorioMedico();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!SesionUtil.isSesionActiva(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        forward(req, res, "/WEB-INF/views/cambiarPassword.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!SesionUtil.isSesionActiva(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String actual       = trim(req, "passwordActual");
        String nueva        = trim(req, "passwordNueva");
        String confirmacion = trim(req, "passwordConfirmacion");

        if (nueva.length() < 6) {
            req.setAttribute("error",
                    "La nueva contraseña debe tener al menos 6 caracteres.");
            forward(req, res, "/WEB-INF/views/cambiarPassword.jsp");
            return;
        }

        if (!nueva.equals(confirmacion)) {
            req.setAttribute("error",
                    "Las contraseñas no coinciden.");
            forward(req, res, "/WEB-INF/views/cambiarPassword.jsp");
            return;
        }

        int    userId = SesionUtil.getUsuarioId(req);
        String rol    = SesionUtil.getUsuarioRol(req);

        Medico medico = repoMedico.buscarPorId(userId);
        if (medico == null || !medico.validarPassword(actual)) {
            req.setAttribute("error",
                    "La contraseña actual es incorrecta.");
            forward(req, res, "/WEB-INF/views/cambiarPassword.jsp");
            return;
        }

        medico.setPassword(nueva);
        medico.setDebeCambiarPwd(0);
        repoMedico.actualizar(medico);

        String destino = "ADMINISTRADOR".equals(rol)
                ? "/admin/dashboard" : "/medico/dashboard";
        res.sendRedirect(req.getContextPath() + destino);
    }
}