package com.webapp.gr03_1bt3_622_26a.controller.paciente;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.service.ServicioCita;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAgenda;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ControladorPacienteCitas",
        urlPatterns = "/paciente/citas")
public class ControladorPacienteCitas extends ControladorBase {

    private volatile ServicioCita   servicioCita;
    private volatile ServicioAgenda servicioAgenda;

    private ServicioCita getCita() {
        if (servicioCita == null) {
            synchronized (this) {
                if (servicioCita == null) servicioCita = new ServicioCita();
            }
        }
        return servicioCita;
    }

    private ServicioAgenda getAgenda() {
        if (servicioAgenda == null) {
            synchronized (this) {
                if (servicioAgenda == null) servicioAgenda = new ServicioAgenda();
            }
        }
        return servicioAgenda;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req) ||
                !"PACIENTE".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "agendar"  -> mostrarFormularioAgendar(req, res);
            case "cancelar" -> cancelarCita(req, res);
            case "historial"-> mostrarHistorial(req, res);
            default         -> listarCitas(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req) ||
                !"PACIENTE".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        confirmarCita(req, res);
    }

    private void listarCitas(HttpServletRequest req,
                             HttpServletResponse res)
            throws ServletException, IOException {
        int pacienteId = SesionUtil.getUsuarioId(req);
        List<Cita> citas = getCita().getCitasActivasPorPaciente(pacienteId);
        req.setAttribute("citas",       citas);
        req.setAttribute("currentPage", "citas");
        forward(req, res, "/WEB-INF/views/paciente/citas/misCitas.jsp");
    }

    private void mostrarHistorial(HttpServletRequest req,
                                  HttpServletResponse res)
            throws ServletException, IOException {
        int pacienteId = SesionUtil.getUsuarioId(req);
        List<Cita> historial = getCita().getHistorialPorPaciente(pacienteId);
        req.setAttribute("historial",   historial);
        req.setAttribute("currentPage", "historial");
        forward(req, res, "/WEB-INF/views/paciente/citas/historial.jsp");
    }

    private void mostrarFormularioAgendar(HttpServletRequest req,
                                          HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("bloqueId",    req.getParameter("bloqueId"));
        req.setAttribute("currentPage", "disponibilidad");
        forward(req, res,
                "/WEB-INF/views/paciente/disponibilidad/agendarCita.jsp");
    }

    private void confirmarCita(HttpServletRequest req,
                               HttpServletResponse res)
            throws IOException, ServletException {
        int pacienteId = SesionUtil.getUsuarioId(req);

        Map<String, String> datos = new HashMap<>();
        datos.put("pacienteId", String.valueOf(pacienteId));
        datos.put("bloqueId",   trim(req, "bloqueId"));
        datos.put("motivo",     trim(req, "motivo"));

        // El agendadoPor es el propio paciente
        com.webapp.gr03_1bt3_622_26a.model.Paciente paciente =
                new com.webapp.gr03_1bt3_622_26a.repository
                        .RepositorioPaciente().buscarPorId(pacienteId);

        try {
            Cita cita = getCita().agendar(datos, paciente);
            req.setAttribute("citaConfirmada", cita);
            req.setAttribute("currentPage",    "citas");
            forward(req, res,
                    "/WEB-INF/views/paciente/citas/citaExitosa.jsp");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error",    e.getMessage());
            req.setAttribute("bloqueId", datos.get("bloqueId"));
            req.setAttribute("currentPage", "disponibilidad");
            forward(req, res,
                    "/WEB-INF/views/paciente/disponibilidad/agendarCita.jsp");
        }
    }

    private void cancelarCita(HttpServletRequest req,
                              HttpServletResponse res)
            throws IOException {
        String idParam = req.getParameter("id");
        int pacienteId = SesionUtil.getUsuarioId(req);
        try {
            int id = Integer.parseInt(idParam);
            com.webapp.gr03_1bt3_622_26a.model.Paciente paciente =
                    new com.webapp.gr03_1bt3_622_26a.repository
                            .RepositorioPaciente().buscarPorId(pacienteId);
            getCita().cancelar(id, paciente);
        } catch (NumberFormatException ignored) {
            // Manejo específico para NumberFormatException
        } catch (IllegalArgumentException ignored) {
            // Manejo específico para IllegalArgumentException
        }
        res.sendRedirect(req.getContextPath() + "/paciente/citas");
    }
}