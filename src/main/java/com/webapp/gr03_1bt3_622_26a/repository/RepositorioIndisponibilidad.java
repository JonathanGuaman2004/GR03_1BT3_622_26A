package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Indisponibilidad;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioIndisponibilidad {

    private SessionFactory sf() {
        return HibernateUtil.getSessionFactory();
    }

    public Indisponibilidad guardar(Indisponibilidad i) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(i);
            s.getTransaction().commit();
            return i;
        }
    }

    public List<Indisponibilidad> buscarPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Indisponibilidad i WHERE i.medico.id = :mid " +
                                    "ORDER BY i.fecha",
                            Indisponibilidad.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}