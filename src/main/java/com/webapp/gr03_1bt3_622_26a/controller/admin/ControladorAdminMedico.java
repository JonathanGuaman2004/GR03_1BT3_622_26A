package com.webapp.gr03_1bt3_622_26a.controller.admin;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioAdminMedico;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ControladorAdminMedico", urlPatterns = "/admin/medicos")
public class ControladorAdminMedico extends ControladorBase {

    private ServicioAdminMedico servicio;
    private RepositorioMedico   repoMedico;

    @Override
    public void init() {
        servicio   = new ServicioAdminMedico();
        repoMedico = new RepositorioMedico();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;

        String action = req.getParameter("action");
        if ("suspender".equals(action)) {
            suspenderMedico(req, res);
            return;
        }
        if ("generarCredenciales".equals(action)) {
            generarCredenciales(req, res);
            return;
        }
        mostrarPanel(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;

        Map<String, String> datos = new HashMap<>();
        datos.put("nombre",       trim(req, "nombre"));
        datos.put("especialidad", trim(req, "especialidad"));
        datos.put("nroLicencia",  trim(req, "nroLicencia"));

        try {
            Medico medico = servicio.registrarMedico(datos);
            req.setAttribute("medicoRegistrado", medico);
            // No se pone "datos" en el atributo: el formulario queda limpio tras exito
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("datos", datos);
        }
        mostrarPanel(req, res);
    }

    // ── Acciones ──────────────────────────────────────────────────────────

    private void mostrarPanel(HttpServletRequest req,
                              HttpServletResponse res)
            throws ServletException, IOException {
        List<Medico> medicos = repoMedico.listar();
        req.setAttribute("medicos",     medicos);
        req.setAttribute("currentPage", "medicos");
        forward(req, res, "/WEB-INF/views/admin/medicos/gestionMedicos.jsp");
    }

    private void suspenderMedico(HttpServletRequest req,
                                 HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(trim(req, "medicoId"));
            repoMedico.actualizarEstado(id, "SUSPENDIDO");
            req.setAttribute("exito", "Médico suspendido correctamente.");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID de médico inválido.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
        }
        mostrarPanel(req, res);
    }

    private void generarCredenciales(HttpServletRequest req,
                                     HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(trim(req, "medicoId"));
            Medico medico = servicio.generarCredenciales(id);
            req.setAttribute("medicoConCredenciales", medico);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID de médico inválido.");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
        }
        mostrarPanel(req, res);
    }

    // ── Utilidad de seguridad ─────────────────────────────────────────────

    private boolean esAdmin(HttpServletRequest req,
                            HttpServletResponse res) throws IOException {
        if (!"ADMINISTRADOR".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}