package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.service.ServicioCita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para UC4: Agendar Cita Médica.
 * GET  /citas                     → lista citas del paciente en sesión
 * GET  /citas?action=agendar&horarioId=X → formulario de confirmación
 * POST /citas                     → confirma y persiste la cita
 * GET  /citas?action=cancelar&id=X → cancela la cita
 */
@WebServlet(name = "ControladorCita", urlPatterns = "/citas")
public class ControladorCita extends HttpServlet {

    private ServicioCita servicio;

    @Override
    public void init() throws ServletException {
        servicio = new ServicioCita();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (!sesionActiva(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "agendar"  -> mostrarFormularioAgendar(req, res);
            case "cancelar" -> cancelarCita(req, res);
            default         -> listarCitas(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (!sesionActiva(req)) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        agendarCita(req, res);
    }

    // ── Acciones ───────────────────────────────────────────────────────────────

    private void listarCitas(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int pacienteId = getPacienteId(req);
        List<Cita> citas = servicio.getCitasPorPaciente(pacienteId);
        req.setAttribute("citas", citas);
        forward(req, res, "/WEB-INF/views/citas.jsp");
    }

    private void mostrarFormularioAgendar(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String horarioIdParam = req.getParameter("horarioId");
        req.setAttribute("horarioId", horarioIdParam);
        forward(req, res, "/WEB-INF/views/agendarCita.jsp");
    }

    private void agendarCita(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        int pacienteId = getPacienteId(req);

        Map<String, String> datos = new HashMap<>();
        datos.put("pacienteId", String.valueOf(pacienteId));
        datos.put("horarioId",  trim(req, "horarioId"));
        datos.put("motivo",     trim(req, "motivo"));

        try {
            Cita cita = servicio.agendar(datos);
            req.setAttribute("citaConfirmada", cita);
            forward(req, res, "/WEB-INF/views/citaExitosa.jsp");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("horarioId", datos.get("horarioId"));
            forward(req, res, "/WEB-INF/views/agendarCita.jsp");
        }
    }

    private void cancelarCita(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String idParam = req.getParameter("id");
        try {
            int id = Integer.parseInt(idParam);
            servicio.cancelar(id);
        } catch (NumberFormatException e) {
            // ignorar IDs malformados
        }
        res.sendRedirect(req.getContextPath() + "/citas");
    }

    // ── Utilidades ─────────────────────────────────────────────────────────────

    private boolean sesionActiva(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null && s.getAttribute("usuarioId") != null;
    }

    private int getPacienteId(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return (int) s.getAttribute("usuarioId");
    }

    private String trim(HttpServletRequest req, String param) {
        String val = req.getParameter(param);
        return val != null ? val.trim() : "";
    }

    private void forward(HttpServletRequest req, HttpServletResponse res, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, res);
    }

    public void enviarNotificacion(Cita cita) {
        // Delegado al ServicioCita internamente al agendar
    }
}
