package com.webapp.gr03_1bt3_622_26a.controller.medico;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.service.ServicioCita;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorMedicoDashboard",
        urlPatterns = "/medico/dashboard")
public class ControladorMedicoDashboard extends ControladorBase {

    private ServicioCita servicioCita;

    @Override
    public void init() {
        servicioCita = new ServicioCita();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req)
                || !"MEDICO".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Si el médico debe cambiar contraseña, redirigir
        Object debeCambiar = req.getSession(false)
                .getAttribute("debeCambiarPwd");
        if (Boolean.TRUE.equals(debeCambiar)
                || Integer.valueOf(1).equals(debeCambiar)) {
            res.sendRedirect(req.getContextPath() + "/cambiar-password");
            return;
        }

        int medicoId = SesionUtil.getUsuarioId(req);
        List<Cita> citasDelDia = servicioCita.getCitasDelDiaPorMedico(medicoId);

        req.setAttribute("citasDelDia",  citasDelDia);
        req.setAttribute("currentPage",  "dashboard");
        forward(req, res, "/WEB-INF/views/medico/dashboard.jsp");
    }
}