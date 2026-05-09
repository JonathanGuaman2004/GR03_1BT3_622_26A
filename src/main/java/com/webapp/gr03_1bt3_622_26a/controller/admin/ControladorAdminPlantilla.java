package com.webapp.gr03_1bt3_622_26a.controller.admin;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.service.ServicioPlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorAdminPlantilla",
        urlPatterns = "/admin/plantilla")

public class ControladorAdminPlantilla extends ControladorBase {

    private ServicioPlantillaHoraria servicio;
    private RepositorioMedico        repoMedico;
    private RepositorioPlantillaHoraria repoPlantilla;

    @Override
    public void init() {
        servicio      = new ServicioPlantillaHoraria();
        repoMedico    = new RepositorioMedico();
        repoPlantilla = new RepositorioPlantillaHoraria();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;

        String action = req.getParameter("action");
        if ("eliminar".equals(action)) {
            eliminarFranja(req, res);
            return;
        }
        mostrarFormulario(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;

        String medicoIdParam = trim(req, "medicoId");
        String dia           = trim(req, "diaSemana");
        String horaInicio    = trim(req, "horaInicio");
        String horaFin       = trim(req, "horaFin");

        if (medicoIdParam.isEmpty()) {
            req.setAttribute("error", "Debe seleccionar un médico.");
            mostrarFormulario(req, res);
            return;
        }

        try {
            int    medicoId = Integer.parseInt(medicoIdParam);
            Medico medico   = repoMedico.buscarPorId(medicoId);

            if (medico == null) {
                req.setAttribute("error", "Médico no encontrado.");
                mostrarFormulario(req, res);
                return;
            }

            servicio.agregarFranja(medico, dia, horaInicio, horaFin);
            req.setAttribute("exito", "Franja agregada correctamente.");
            req.setAttribute("medicoSeleccionadoId", medicoId);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID de médico inválido.");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("medicoSeleccionadoId", medicoIdParam);
        }

        mostrarFormulario(req, res);
    }

    private void mostrarFormulario(HttpServletRequest req,
                                   HttpServletResponse res)
            throws ServletException, IOException {
        List<Medico> medicos = repoMedico.listarActivos();
        req.setAttribute("medicos", medicos);

        String medicoIdParam = req.getParameter("medicoId");
        if (medicoIdParam == null) {
            Object medicoSelAttrObj = req.getAttribute("medicoSeleccionadoId");
            if (medicoSelAttrObj != null) {
                medicoIdParam = medicoSelAttrObj.toString();
            }
        }

        if (medicoIdParam != null && !medicoIdParam.isEmpty()) {
            try {
                int medicoId = Integer.parseInt(medicoIdParam);
                Medico medico = repoMedico.buscarPorId(medicoId);
                if (medico != null) {
                    List<PlantillaHoraria> plantilla =
                            servicio.obtenerPlantilla(medicoId);
                    req.setAttribute("medicoSeleccionado", medico);
                    req.setAttribute("plantilla", plantilla);
                }
            } catch (NumberFormatException ignored) {}
        }

        req.setAttribute("currentPage", "plantilla");
        forward(req, res, "/WEB-INF/views/admin/horarios/plantillaSemanal.jsp");
    }

    private void eliminarFranja(HttpServletRequest req,
                                HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int franjaId  = Integer.parseInt(trim(req, "franjaId"));
            int medicoId  = Integer.parseInt(trim(req, "medicoId"));
            repoPlantilla.eliminar(franjaId);
            req.setAttribute("exito", "Franja eliminada.");
            req.setAttribute("medicoSeleccionadoId", medicoId);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Parámetros inválidos.");
        }
        mostrarFormulario(req, res);
    }

    private boolean esAdmin(HttpServletRequest req,
                            HttpServletResponse res) throws IOException {
        if (!"ADMINISTRADOR".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}