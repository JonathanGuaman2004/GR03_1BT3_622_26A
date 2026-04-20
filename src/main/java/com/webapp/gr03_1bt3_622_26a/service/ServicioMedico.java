package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;

import java.io.File;
import java.nio.file.Files;

public class ServicioMedico {

    private final RepositorioMedico repoMedico = new RepositorioMedico();

    public Medico ingresarMedico(String nombre, String email, String password,
                                 String especialidad, String nroLicencia) {
        Medico medico = new Medico(nombre, email, password, especialidad, nroLicencia);
        asegurarHibernateInicializado();
        return repoMedico.guardar(medico);
    }

    private void asegurarHibernateInicializado() {
        try {
            HibernateUtil.getSessionFactory();
        } catch (IllegalStateException e) {
            File webAppRealPath = new File("target/test-runtime");
            File webInfDir = new File(webAppRealPath, "WEB-INF");
            try {
                Files.createDirectories(webInfDir.toPath());
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo preparar el directorio de pruebas para la BD.", ex);
            }
            HibernateUtil.init(webAppRealPath.getAbsolutePath());
        }
    }
}



