package com.webapp.gr03_1bt3_622_26a.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String realPath = ctx.getRealPath("/");

        if (realPath == null) {
            throw new RuntimeException(
                    "No se pudo obtener la ruta real de la webapp.");
        }

        System.out.println("[AppListener] Inicializando Hibernate...");
        try {
            HibernateUtil.init(realPath);
            System.out.println("[AppListener] Hibernate OK.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al inicializar Hibernate", e);
        }

        System.out.println("[AppListener] Iniciando seeding...");
        try {
            new DataSeeder().sembrar();
            System.out.println("[AppListener] Seeding OK.");
        } catch (Exception e) {
            System.err.println("[AppListener] Error en seeding: "
                    + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }
}