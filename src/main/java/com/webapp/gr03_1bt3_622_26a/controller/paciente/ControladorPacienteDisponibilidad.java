package com.webapp.gr03_1bt3_622_26a.controller.paciente;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAgenda;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorPacienteDisponibilidad",
        urlPatterns = "/paciente/disponibilidad")
public class ControladorPacienteDisponibilidad extends ControladorBase {

    private volatile ServicioAgenda servicio;

    private ServicioAgenda getServicio() {
        if (servicio == null) {
            synchronized (this) {
                if (servicio == null) servicio = new ServicioAgenda();
            }
        }
        return servicio;
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

        String medicoIdParam    = req.getParameter("medicoId");
        String especialidadParam = req.getParameter("especialidad");

        if (medicoIdParam != null && !medicoIdParam.isBlank()) {
            mostrarBloques(req, res, medicoIdParam);
        } else {
            mostrarMedicos(req, res, especialidadParam);
        }
    }

    private void mostrarMedicos(HttpServletRequest req,
                                HttpServletResponse res,
                                String especialidad)
            throws ServletException, IOException {
        List<Medico> medicos = especialidad != null && !especialidad.isBlank()
                ? getServicio().getMedicosPorEspecialidad(especialidad)
                : getServicio().getMedicos();

        req.setAttribute("medicos",      medicos);
        req.setAttribute("especialidad", especialidad);
        req.setAttribute("currentPage",  "disponibilidad");
        forward(req, res, "/WEB-INF/views/paciente/disponibilidad/medicos.jsp");
    }

    private void mostrarBloques(HttpServletRequest req,
                                HttpServletResponse res,
                                String medicoIdParam)
            throws ServletException, IOException {
        try {
            int    medicoId  = Integer.parseInt(medicoIdParam);
            Medico medico    = getServicio().getMedico(medicoId);

            if (medico == null) {
                req.setAttribute("error", "Médico no encontrado.");
                mostrarMedicos(req, res, null);
                return;
            }

            // Obtener todas las fechas con bloques publicados para el selector
            List<String> fechasDisponibles =
                    getServicio().getFechasDisponibles(medicoId);

            // Obtener la fecha seleccionada por el paciente (si existe)
            String fechaSeleccionada = req.getParameter("fecha");

            List<BloqueHorario> bloques;
            if (fechaSeleccionada != null && !fechaSeleccionada.isBlank()) {
                // Mostrar TODOS los bloques publicados de ese día (disponibles y ocupados)
                bloques = getServicio().getBloquesPublicadosPorFecha(
                        medicoId, fechaSeleccionada);
            } else {
                // Si no hay fecha seleccionada, mostrar solo disponibles de todas las fechas
                bloques = getServicio().getBloquesDisponibles(medicoId);
                fechaSeleccionada = null;
            }

            req.setAttribute("medico",            medico);
            req.setAttribute("bloques",           bloques);
            req.setAttribute("fechasDisponibles", fechasDisponibles);
            req.setAttribute("fechaSeleccionada", fechaSeleccionada);
            req.setAttribute("sinDisponibilidad", fechasDisponibles.isEmpty());
            req.setAttribute("currentPage",       "disponibilidad");
            forward(req, res,
                    "/WEB-INF/views/paciente/disponibilidad/horarios.jsp");

        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "ID de médico inválido.");
        }
    }
}