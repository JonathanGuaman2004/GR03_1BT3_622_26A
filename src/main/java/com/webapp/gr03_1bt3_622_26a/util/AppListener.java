package com.webapp.gr03_1bt3_622_26a.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Inicializa Hibernate al arrancar la webapp,
 * siembra datos de prueba si la BD está vacía,
 * y cierra el SessionFactory al detener el servidor.
 */
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

        System.out.println("[AppListener] Inicializando Hibernate. path=" + realPath);
        HibernateUtil.init(realPath);
        System.out.println("[AppListener] Hibernate listo.");

        // Sembrar datos iniciales si la BD está vacía
        new DataSeeder().sembrar();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppListener] Cerrando Hibernate...");
        HibernateUtil.shutdown();
    }
}
