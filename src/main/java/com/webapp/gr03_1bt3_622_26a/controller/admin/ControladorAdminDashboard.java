package com.webapp.gr03_1bt3_622_26a.controller.admin;

import com.webapp.gr03_1bt3_622_26a.controller.ControladorBase;
import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioCita;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPaciente;
import com.webapp.gr03_1bt3_622_26a.util.SesionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ControladorAdminDashboard", urlPatterns = "/admin/dashboard")
public class ControladorAdminDashboard extends ControladorBase {

    private RepositorioMedico   repoMedico;
    private RepositorioPaciente repoPaciente;
    private RepositorioCita     repoCita;

    @Override
    public void init() {
        repoMedico   = new RepositorioMedico();
        repoPaciente = new RepositorioPaciente();
        repoCita     = new RepositorioCita();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);

        if (!SesionUtil.isSesionActiva(req) ||
                !"ADMINISTRADOR".equals(SesionUtil.getUsuarioRol(req))) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        mostrarPanel(req, res);
    }

    private void mostrarPanel(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Medico>   todosMedicos   = repoMedico.listar();
        List<Medico>   medicosActivos = repoMedico.listarActivos();
        List<Paciente> todosPacientes = repoPaciente.listar();

        long totalCitas       = 0;
        long citasProgramadas = 0;
        long citasCanceladas  = 0;
        long citasCompletadas = 0;

        for (Paciente p : todosPacientes) {
            List<Cita> citas = repoCita.buscarPorPaciente(p.getId());
            totalCitas += citas.size();
            for (Cita c : citas) {
                switch (c.getEstado()) {
                    case Cita.PROGRAMADA:
                    case Cita.REAGENDADA:
                    case Cita.EN_ESPERA_REASIGNACION:
                        citasProgramadas++;
                        break;
                    case Cita.CANCELADA:
                        citasCanceladas++;
                        break;
                    case Cita.COMPLETADA:
                        citasCompletadas++;
                        break;
                }
            }
        }

        int total      = todosMedicos.size();
        int activos    = medicosActivos.size();
        int suspendidos = total - activos;

        List<Medico> ultimosMedicos =
                todosMedicos.subList(0, Math.min(5, total));

        req.setAttribute("totalMedicos",      total);
        req.setAttribute("medicosActivos",    activos);
        req.setAttribute("medicosSuspendidos", suspendidos);
        req.setAttribute("totalPacientes",    todosPacientes.size());
        req.setAttribute("totalCitas",        totalCitas);
        req.setAttribute("citasProgramadas",  citasProgramadas);
        req.setAttribute("citasCanceladas",   citasCanceladas);
        req.setAttribute("citasCompletadas",  citasCompletadas);
        req.setAttribute("ultimosMedicos",    ultimosMedicos);
        req.setAttribute("currentPage",       "dashboard");

        forward(req, res, "/WEB-INF/views/admin/dashboard.jsp");
    }
}