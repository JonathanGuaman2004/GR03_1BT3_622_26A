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

        System.out.println("[AppListener] =========================================");
        System.out.println("[AppListener] Inicializando Hibernate...");
        System.out.println("[AppListener] Ruta real de webapp: " + realPath);

        try {
            HibernateUtil.init(realPath);
            System.out.println("[AppListener] ✓ Hibernate inicializado correctamente.");
        } catch (Exception e) {
            System.err.println("[AppListener] ✗ Error inicializando Hibernate:");
            e.printStackTrace();
            throw new RuntimeException("Fallo al inicializar Hibernate", e);
        }

        // Sembrar datos iniciales si la BD está vacía
        System.out.println("[AppListener] Iniciando seeding de datos...");
        try {
            new DataSeeder().sembrar();
            System.out.println("[AppListener] ✓ Seeding completado.");
        } catch (Exception e) {
            System.err.println("[AppListener] ✗ Error en seeding:");
            e.printStackTrace();
        }
        System.out.println("[AppListener] =========================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[AppListener] Cerrando Hibernate...");
        HibernateUtil.shutdown();
    }
}
