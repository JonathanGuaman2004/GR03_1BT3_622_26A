package com.webapp.gr03_1bt3_622_26a.controller.medico;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorMedicoDashboard", urlPatterns = "/medico/dashboard")
public class ControladorMedicoDashboard extends ControladorBase {

    private RepositorioMedico        repoMedico;
    private RepositorioCita          repoCita;
    private RepositorioBloqueHorario repoBloque;

    @Override
    public void init() {
        repoMedico = new RepositorioMedico();
        repoCita   = new RepositorioCita();
        repoBloque = new RepositorioBloqueHorario();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req) ||
                !"MEDICO".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int medicoId = SesionUtil.getUsuarioId(req);
        Medico medico = repoMedico.buscarPorId(medicoId);

        // Citas del médico (todas)
        List<Cita> todasCitas = repoCita.buscarPorMedico(medicoId);

        // Contar por estado
        long programadas = todasCitas.stream()
                .filter(c -> "PROGRAMADA".equals(c.getEstado()) || "REAGENDADA".equals(c.getEstado()))
                .count();
        long completadas = todasCitas.stream()
                .filter(c -> "COMPLETADA".equals(c.getEstado()))
                .count();
        long canceladas  = todasCitas.stream()
                .filter(c -> "CANCELADA".equals(c.getEstado()))
                .count();

        // Próxima cita (primera programada)
        Cita proximaCita = todasCitas.stream()
                .filter(c -> "PROGRAMADA".equals(c.getEstado()) || "REAGENDADA".equals(c.getEstado()))
                .findFirst()
                .orElse(null);

        // Bloques disponibles publicados
        List<BloqueHorario> bloquesDisponibles =
                repoBloque.buscarDisponiblesPorMedico(medicoId);

        req.setAttribute("medico",            medico);
        req.setAttribute("proximaCita",       proximaCita);
        req.setAttribute("citasProgramadas",  programadas);
        req.setAttribute("citasCompletadas",  completadas);
        req.setAttribute("citasCanceladas",   canceladas);
        req.setAttribute("totalCitas",        (long) todasCitas.size());
        req.setAttribute("bloquesDisponibles", bloquesDisponibles.size());
        req.setAttribute("currentPage",       "dashboard");

        forward(req, res, "/WEB-INF/views/medico/dashboard.jsp");
    }
}