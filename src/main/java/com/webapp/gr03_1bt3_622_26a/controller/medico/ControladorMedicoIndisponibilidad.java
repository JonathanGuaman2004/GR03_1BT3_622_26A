package com.webapp.gr03_1bt3_622_26a.controller.medico;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioIndisponibilidad;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.service.ServicioIndisponibilidad;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorMedicoIndisponibilidad",
        urlPatterns = "/medico/indisponibilidad")
public class ControladorMedicoIndisponibilidad extends ControladorBase {

    private ServicioIndisponibilidad  servicio;
    private RepositorioMedico         repoMedico;
    private RepositorioIndisponibilidad repoIndisp;

    @Override
    public void init() {
        servicio   = new ServicioIndisponibilidad();
        repoMedico = new RepositorioMedico();
        repoIndisp = new RepositorioIndisponibilidad();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esMedico(req, res)) return;
        if (debeRedirigirCambioPassword(req, res)) return;
        mostrarFormulario(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        if (!esMedico(req, res)) return;
        if (debeRedirigirCambioPassword(req, res)) return;

        String fecha  = trim(req, "fecha");
        String motivo = trim(req, "motivo");

        int    medicoId = SesionUtil.getUsuarioId(req);
        Medico medico   = repoMedico.buscarPorId(medicoId);

        if (medico == null) {
            req.setAttribute("error", "Médico no encontrado en sesión.");
            mostrarFormulario(req, res);
            return;
        }

        try {
            servicio.registrarIndisponibilidad(medico, fecha, motivo);
            req.setAttribute("exito",
                    "Indisponibilidad registrada para el " + fecha
                            + ". Los bloques de ese día han sido bloqueados.");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("fechaPrevia",  fecha);
            req.setAttribute("motivoPrevio", motivo);
        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("fechaPrevia",  fecha);
            req.setAttribute("motivoPrevio", motivo);
        }

        mostrarFormulario(req, res);
    }

    private void mostrarFormulario(HttpServletRequest req,
                                   HttpServletResponse res)
            throws ServletException, IOException {
        int medicoId = SesionUtil.getUsuarioId(req);
        List<Indisponibilidad> indisponibilidades =
                repoIndisp.buscarPorMedico(medicoId);

        req.setAttribute("indisponibilidades", indisponibilidades);
        req.setAttribute("currentPage", "horarios");
        forward(req, res, "/WEB-INF/views/medico/indisponibilidad.jsp");
    }

    private boolean esMedico(HttpServletRequest req,
                             HttpServletResponse res) throws IOException {
        if (!SesionUtil.isSesionActiva(req)
                || !"MEDICO".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    private boolean debeRedirigirCambioPassword(HttpServletRequest req,
                                                HttpServletResponse res)
            throws IOException {
        Object debeCambiar = req.getSession(false).getAttribute("debeCambiarPwd");
        if (Boolean.TRUE.equals(debeCambiar) || Integer.valueOf(1).equals(debeCambiar)) {
            res.sendRedirect(req.getContextPath() + "/cambiar-password");
            return true;
        }
        return false;
    }
}