package com.webapp.gr03_1bt3_622_26a.controller.admin;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Usuario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioHorario;
import com.webapp.gr03_1bt3_622_26a.service.ServicioPlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorAdminHorario",
        urlPatterns = "/admin/horarios")
public class ControladorAdminHorario extends ControladorBase {

    private ServicioHorario          servicioHorario;
    private ServicioPlantillaHoraria servicioPlantilla;
    private RepositorioMedico        repoMedico;

    @Override
    public void init() {
        servicioHorario   = new ServicioHorario();
        servicioPlantilla = new ServicioPlantillaHoraria();
        repoMedico        = new RepositorioMedico();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;

        String action = req.getParameter("action");
        if ("publicar".equals(action)) {
            publicarBloques(req, res);
            return;
        }
        mostrarFormulario(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esAdmin(req, res)) return;
        generarBloques(req, res);
    }

    private void mostrarFormulario(HttpServletRequest req,
                                   HttpServletResponse res)
            throws ServletException, IOException {
        req.setAttribute("medicos",     repoMedico.listarActivos());
        req.setAttribute("currentPage", "horarios");
        forward(req, res, "/WEB-INF/views/admin/horarios/gestionHorarios.jsp");
    }

    private void generarBloques(HttpServletRequest req,
                                HttpServletResponse res)
            throws ServletException, IOException {
        String medicoIdParam  = trim(req, "medicoId");
        String fechaInicio    = trim(req, "fechaInicio");
        String fechaFin       = trim(req, "fechaFin");

        if (medicoIdParam.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            req.setAttribute("error", "Todos los campos son obligatorios.");
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

            if (!servicioPlantilla.tienePlantilla(medicoId)) {
                req.setAttribute("error",
                        "El médico " + medico.getNombre()
                                + " no tiene plantilla semanal configurada. "
                                + "Configure la plantilla antes de generar bloques.");
                mostrarFormulario(req, res);
                return;
            }

            List<BloqueHorario> bloques = servicioHorario.generarBloques(
                    medico, fechaInicio, fechaFin);

            req.setAttribute("bloquesGenerados", bloques);
            req.setAttribute("medicoSeleccionado", medico);
            req.setAttribute("fechaInicio", fechaInicio);
            req.setAttribute("fechaFin",    fechaFin);
            req.setAttribute("exito",
                    "Se generaron " + bloques.size() + " bloques. "
                            + "Revisa la previsualización y confirma la publicación.");

        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID de médico inválido.");
        } catch (Exception e) {
            req.setAttribute("error", "Error al generar bloques: " + e.getMessage());
        }

        mostrarFormulario(req, res);
    }

    private void publicarBloques(HttpServletRequest req,
                                 HttpServletResponse res)
            throws ServletException, IOException {
        String medicoIdParam = trim(req, "medicoId");
        String fechaInicio   = trim(req, "fechaInicio");
        String fechaFin      = trim(req, "fechaFin");

        try {
            int    medicoId = Integer.parseInt(medicoIdParam);
            int    adminId  = SesionUtil.getUsuarioId(req);

            com.webapp.gr03_1bt3_622_26a.model.Administrador admin =
                    new com.webapp.gr03_1bt3_622_26a.model.Administrador();
            admin.setId(adminId);

            List<BloqueHorario> publicados = servicioHorario.publicarBloques(
                    medicoId, fechaInicio, fechaFin, admin);

            req.setAttribute("exito",
                    "Se publicaron " + publicados.size()
                            + " bloques correctamente. Ya son visibles para los pacientes.");

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Parámetros inválidos.");
        } catch (Exception e) {
            req.setAttribute("error", "Error al publicar: " + e.getMessage());
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