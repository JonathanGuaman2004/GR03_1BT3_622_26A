package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.model.HorarioDisponible;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAgenda;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Controlador para UC3: Consultar Disponibilidad Médica.
 * GET /disponibilidad              → lista todos los médicos
 * GET /disponibilidad?medicoId=X   → muestra horarios disponibles del médico X
 *
 * NOTA: ServicioAgenda se instancia de forma lazy (primer request) en lugar
 * de en init(), para evitar que HibernateUtil no esté listo aún cuando
 * Tomcat inicializa el servlet antes de que AppListener termine.
 */
@WebServlet(name = "ControladorDisponibilidad", urlPatterns = "/disponibilidad")
public class ControladorDisponibilidad extends ControladorBase {

    private volatile ServicioAgenda servicio;

    private ServicioAgenda getServicio() {
        if (servicio == null) {
            synchronized (this) {
                if (servicio == null) {
                    servicio = new ServicioAgenda();
                }
            }
        }
        return servicio;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

        // Verificar sesión activa
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            System.out.println("[ControladorDisponibilidad] Sesión no válida, redirigiendo a login.");
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        System.out.println("[ControladorDisponibilidad] Sesión válida. Usuario: " + session.getAttribute("usuarioNom"));

        String medicoIdParam = req.getParameter("medicoId");

        if (medicoIdParam != null && !medicoIdParam.isBlank()) {
            System.out.println("[ControladorDisponibilidad] Consultando horarios para médico ID: " + medicoIdParam);
            consultarDisponibilidad(req, res, medicoIdParam);
        } else {
            System.out.println("[ControladorDisponibilidad] Listando todos los médicos.");
            buscarMedicos(req, res);
        }
    }

    /** Muestra la lista de todos los médicos. */
    private void buscarMedicos(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            List<Medico> medicos = getServicio().getMedicos();
            System.out.println("[ControladorDisponibilidad] buscarMedicos() obtuvo " + medicos.size() + " médicos.");
            req.setAttribute("medicos", medicos);
            forward(req, res, "/WEB-INF/views/disponibilidad.jsp");
        } catch (Exception e) {
            System.err.println("[ControladorDisponibilidad] Error en buscarMedicos():");
            e.printStackTrace();
            req.setAttribute("error", "Error al obtener la lista de médicos. Intenta de nuevo más tarde.");
            req.setAttribute("medicos", Collections.emptyList());
            forward(req, res, "/WEB-INF/views/disponibilidad.jsp");
        }
    }

    /** Muestra los horarios disponibles del médico seleccionado. */
    private void consultarDisponibilidad(HttpServletRequest req, HttpServletResponse res,
                                         String medicoIdParam)
            throws ServletException, IOException {
        try {
            int medicoId = Integer.parseInt(medicoIdParam);
            Medico medico = getServicio().getMedico(medicoId);

            if (medico == null) {
                System.out.println("[ControladorDisponibilidad] Médico ID " + medicoId + " no encontrado.");
                req.setAttribute("error", "Médico no encontrado.");
                buscarMedicos(req, res);
                return;
            }

            System.out.println("[ControladorDisponibilidad] Médico encontrado: " + medico.getNombre());

            List<HorarioDisponible> horarios = getServicio().getHorariosDisponibles(medicoId);
            System.out.println("[ControladorDisponibilidad] Horarios disponibles para médico " + medicoId + ": " + horarios.size());

            req.setAttribute("medico",   medico);
            req.setAttribute("horarios", horarios);

            if (horarios.isEmpty()) {
                req.setAttribute("sinDisponibilidad", true);
                System.out.println("[ControladorDisponibilidad] Sin horarios disponibles.");
            }

            forward(req, res, "/WEB-INF/views/horarios.jsp");

        } catch (NumberFormatException e) {
            System.err.println("[ControladorDisponibilidad] ID de médico inválido: " + medicoIdParam);
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de médico inválido.");
        } catch (Exception e) {
            System.err.println("[ControladorDisponibilidad] Error en consultarDisponibilidad():");
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al consultar disponibilidad.");
        }
    }
}
