package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioPaciente {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public Paciente guardar(Paciente pac) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(pac);
            s.getTransaction().commit();
            return pac;
        }
    }

    public Paciente buscarPorId(int id) {
        try (Session s = sf().openSession()) {
            return s.get(Paciente.class, id);
        }
    }

    public Paciente buscarPorEmail(String email) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Paciente WHERE email = :email", Paciente.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("[RepositorioPaciente] buscarPorEmail: " + e.getMessage());
            return null;
        }
    }

    public List<Paciente> listar() {
        try (Session s = sf().openSession()) {
            return s.createQuery("FROM Paciente ORDER BY nombre", Paciente.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }
}
