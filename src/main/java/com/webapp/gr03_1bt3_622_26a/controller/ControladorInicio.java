package com.webapp.gr03_1bt3_622_26a.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ControladorInicio", urlPatterns = "/inicio")
public class ControladorInicio extends ControladorBase {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/inicio.jsp").forward(req, res);
    }
}
