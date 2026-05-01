package com.webapp.gr03_1bt3_622_26a.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.io.File;
import java.nio.file.Files;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static synchronized void init(String webAppRealPath) {
        if (sessionFactory != null) return;

        // Intentar usar WEB-INF dentro del proyecto fuente directamente
        // para que la BD sobreviva rebuilds de Tomcat
        File dbFile = resolverRutaBD(webAppRealPath);
        String url  = "jdbc:sqlite:" + dbFile.getAbsolutePath();
        System.out.println("[HibernateUtil] BD en: " + url);

        sessionFactory = new Configuration()
                .configure()
                .setProperty(Environment.URL, url)
                .buildSessionFactory();
    }

    /**
     * Resuelve la ruta de la BD priorizando src/main/webapp/WEB-INF
     * para que la BD persista entre reinicios de Tomcat.
     * Si no encuentra esa ruta, cae en WEB-INF del despliegue.
     */
    private static File resolverRutaBD(String webAppRealPath) {
        // Ruta del directorio de trabajo del proceso (raíz del proyecto)
        File workDir = new File(System.getProperty("user.dir"));

        // Intentar src/main/webapp/WEB-INF (desarrollo con IntelliJ/Maven)
        File srcWebInf = new File(workDir, "src/main/webapp/WEB-INF");
        if (srcWebInf.exists() && srcWebInf.isDirectory()) {
            System.out.println("[HibernateUtil] Usando WEB-INF del proyecto fuente.");
            return new File(srcWebInf, "citas.db");
        }

        // Fallback: WEB-INF del despliegue de Tomcat
        File deployWebInf = new File(webAppRealPath, "WEB-INF");
        try {
            Files.createDirectories(deployWebInf.toPath());
        } catch (Exception e) {
            System.err.println("[HibernateUtil] No se pudo crear WEB-INF: " + e.getMessage());
        }
        System.out.println("[HibernateUtil] Usando WEB-INF del despliegue.");
        return new File(deployWebInf, "citas.db");
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