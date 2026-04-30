package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Paciente;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Collections;
import java.util.List;

public class RepositorioPaciente {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public Paciente guardar(Paciente p) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(p);
            s.getTransaction().commit();
            return p;
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
            return null;
        }
    }

    public Paciente buscarPorCedula(String cedula) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Paciente WHERE cedula = :cedula", Paciente.class)
                    .setParameter("cedula", cedula)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }

    public List<Paciente> listar() {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Paciente ORDER BY nombre", Paciente.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}