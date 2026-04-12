package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.HorarioDisponible;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAgenda;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Controlador para UC3: Consultar Disponibilidad Médica.
 * GET /disponibilidad              → lista todos los médicos
 * GET /disponibilidad?medicoId=X   → muestra horarios disponibles del médico X
 */
@WebServlet(name = "ControladorDisponibilidad", urlPatterns = "/disponibilidad")
public class ControladorDisponibilidad extends HttpServlet {

    private ServicioAgenda servicio;

    @Override
    public void init() throws ServletException {
        servicio = new ServicioAgenda();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String medicoIdParam = req.getParameter("medicoId");

        if (medicoIdParam != null && !medicoIdParam.isBlank()) {
            consultarDisponibilidad(req, res, medicoIdParam);
        } else {
            buscarMedicos(req, res);
        }
    }

    /** Muestra la lista de todos los médicos. */
    private void buscarMedicos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        List<Medico> medicos = servicio.getMedicos();
        req.setAttribute("medicos", medicos);
        forward(req, res, "/WEB-INF/views/disponibilidad.jsp");
    }

    /** Muestra los horarios disponibles del médico seleccionado. */
    private void consultarDisponibilidad(HttpServletRequest req, HttpServletResponse res,
                                         String medicoIdParam)
            throws ServletException, IOException {
        try {
            int medicoId = Integer.parseInt(medicoIdParam);
            Medico medico = servicio.getMedico(medicoId);

            if (medico == null) {
                req.setAttribute("error", "Médico no encontrado.");
                buscarMedicos(req, res);
                return;
            }

            List<HorarioDisponible> horarios = servicio.getHorariosDisponibles(medicoId);
            req.setAttribute("medico",   medico);
            req.setAttribute("horarios", horarios);

            if (horarios.isEmpty()) {
                req.setAttribute("sinDisponibilidad", true);
            }

            forward(req, res, "/WEB-INF/views/horarios.jsp");

        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de médico inválido.");
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse res, String jsp)
            throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, res);
    }
}
