package com.webapp.gr03_1bt3_622_26a.controller.paciente;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.service.ServicioCita;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorPacienteDashboard",
        urlPatterns = "/paciente/dashboard")
public class ControladorPacienteDashboard extends ControladorBase {

    private ServicioCita servicioCita;

    @Override
    public void init() { servicioCita = new ServicioCita(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req) ||
                !"PACIENTE".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int pacienteId = SesionUtil.getUsuarioId(req);
        List<Cita> citasActivas =
                servicioCita.getCitasActivasPorPaciente(pacienteId);

        // Próxima cita = primera de la lista (ya ordenadas por fecha asc)
        Cita proximaCita = citasActivas.isEmpty() ? null : citasActivas.get(0);

        req.setAttribute("proximaCita", proximaCita);
        req.setAttribute("currentPage", "dashboard");
        forward(req, res, "/WEB-INF/views/paciente/dashboard.jsp");
    }
}