package com.webapp.gr03_1bt3_622_26a.controller.medico;

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

@WebServlet(name = "ControladorMedicoCitas",
        urlPatterns = "/medico/citas")
public class ControladorMedicoCitas extends ControladorBase {

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

        Object debeCambiar = req.getSession(false).getAttribute("debeCambiarPwd");
        if (Boolean.TRUE.equals(debeCambiar) || Integer.valueOf(1).equals(debeCambiar)) {
            res.sendRedirect(req.getContextPath() + "/cambiar-password");
            return;
        }

        String action = req.getParameter("action");
        if ("historial".equals(action)) {
            mostrarHistorial(req, res);
        } else {
            mostrarCitasDelDia(req, res);
        }
    }

    private void mostrarCitasDelDia(HttpServletRequest req,
                                    HttpServletResponse res)
            throws ServletException, IOException {
        int medicoId = SesionUtil.getUsuarioId(req);
        List<Cita> citasDelDia = servicioCita.getCitasDelDiaPorMedico(medicoId);

        req.setAttribute("citasDelDia",  citasDelDia);
        req.setAttribute("currentPage",  "citas");
        forward(req, res, "/WEB-INF/views/medico/misCitasMedico.jsp");
    }

    private void mostrarHistorial(HttpServletRequest req,
                                  HttpServletResponse res)
            throws ServletException, IOException {
        int medicoId = SesionUtil.getUsuarioId(req);
        List<Cita> historial = servicioCita.getCitasPorMedico(medicoId);

        req.setAttribute("historial",   historial);
        req.setAttribute("currentPage", "historial");
        forward(req, res, "/WEB-INF/views/medico/historialMedico.jsp");
    }
}