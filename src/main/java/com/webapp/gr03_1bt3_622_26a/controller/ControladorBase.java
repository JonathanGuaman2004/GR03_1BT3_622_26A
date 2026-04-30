package com.webapp.gr03_1bt3_622_26a.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ControladorBase extends HttpServlet {

	protected String trim(HttpServletRequest req, String param) {
		String val = req.getParameter(param);
		return val != null ? val.trim() : "";
	}

	protected void forward(HttpServletRequest req, HttpServletResponse res,
	                       String jsp) throws ServletException, IOException {
		req.getRequestDispatcher(jsp).forward(req, res);
	}

	protected void setEncoding(HttpServletRequest req,
	                           HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
	}
}