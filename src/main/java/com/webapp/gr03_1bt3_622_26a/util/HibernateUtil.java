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
        // Estrategia 1: subir desde webAppRealPath hasta encontrar src/main/webapp/WEB-INF
        File candidato = new File(webAppRealPath).getAbsoluteFile();
        for (int i = 0; i < 8; i++) {
            File posibleWebInf = new File(candidato, "src/main/webapp/WEB-INF");
            if (posibleWebInf.exists() && posibleWebInf.isDirectory()) {
                System.out.println("[HibernateUtil] Proyecto encontrado en: " + candidato);
                return new File(posibleWebInf, "citas.db");
            }
            candidato = candidato.getParentFile();
            if (candidato == null) break;
        }

        // Estrategia 2: user.dir (funciona al correr mvn directamente sin Tomcat)
        File workDir = new File(System.getProperty("user.dir"));
        File srcWebInf = new File(workDir, "src/main/webapp/WEB-INF");
        if (srcWebInf.exists() && srcWebInf.isDirectory()) {
            System.out.println("[HibernateUtil] Usando WEB-INF del proyecto fuente via user.dir.");
            return new File(srcWebInf, "citas.db");
        }

        // Estrategia 3: fallback al WEB-INF del despliegue
        File deployWebInf = new File(webAppRealPath, "WEB-INF");
        try {
            Files.createDirectories(deployWebInf.toPath());
        } catch (Exception e) {
            System.err.println("[HibernateUtil] No se pudo crear WEB-INF: " + e.getMessage());
        }
        System.out.println("[HibernateUtil] Usando WEB-INF del despliegue (fallback).");
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