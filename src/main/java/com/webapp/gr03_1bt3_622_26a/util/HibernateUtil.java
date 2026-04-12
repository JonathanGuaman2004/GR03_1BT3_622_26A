package com.webapp.gr03_1bt3_622_26a.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.File;

/**
 * Mantiene el SessionFactory de Hibernate como singleton.
 * Se inicializa una sola vez desde AppListener con la ruta real de la webapp.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    /** Llama esto UNA SOLA VEZ desde AppListener.contextInitialized(). */
    public static synchronized void init(String webAppRealPath) {
        if (sessionFactory != null) return;

        File dbFile = new File(webAppRealPath, "WEB-INF/citas.db");
        String url  = "jdbc:sqlite:" + dbFile.getAbsolutePath();

        System.out.println("[HibernateUtil] BD en: " + url);

        sessionFactory = new Configuration()
                .configure()
                .setProperty(Environment.URL, url)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException(
                    "SessionFactory no inicializado. ¿Se registró AppListener?");
        }
        return sessionFactory;
    }

    public static synchronized void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}
