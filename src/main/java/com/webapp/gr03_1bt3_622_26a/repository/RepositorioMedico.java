package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioMedico {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public Medico guardar(Medico med) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(med);
            s.getTransaction().commit();
            return med;
        }
    }

    public Medico buscarPorId(int id) {
        try (Session s = sf().openSession()) {
            return s.get(Medico.class, id);
        }
    }

    public Medico buscarPorEmail(String email) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Medico WHERE email = :email", Medico.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Medico> listar() {
        try (Session s = sf().openSession()) {
            List<Medico> result = s.createQuery("FROM Medico ORDER BY nombre", Medico.class).list();
            System.out.println("[RepositorioMedico] listar() retornó " + result.size() + " médicos.");
            return result;
        } catch (Exception e) {
            System.err.println("[RepositorioMedico] Error en listar():");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
