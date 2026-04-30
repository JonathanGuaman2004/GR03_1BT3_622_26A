package com.webapp.gr03_1bt3_622_26a.controller;

import com.webapp.gr03_1bt3_622_26a.service.ServicioPaciente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ControladorRegistro", urlPatterns = "/registro")
public class ControladorRegistro extends ControladorBase {

    private ServicioPaciente servicio;

    @Override
    public void init() { servicio = new ServicioPaciente(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        forward(req, res, "/WEB-INF/views/registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        setEncoding(req, res);
        Map<String, String> datos = new HashMap<>();
        datos.put("nombre",   trim(req, "nombre"));
        datos.put("email",    trim(req, "email"));
        datos.put("password", trim(req, "password"));
        datos.put("telefono", trim(req, "telefono"));
        datos.put("cedula",   trim(req, "cedula"));

        try {
            servicio.validarDatos(datos);
            servicio.registrar(datos);
            res.sendRedirect(req.getContextPath() + "/login?registered=1");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("datos", datos);
            forward(req, res, "/WEB-INF/views/registro.jsp");
        }
    }
}