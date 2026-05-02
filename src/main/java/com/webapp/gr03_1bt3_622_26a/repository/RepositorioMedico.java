package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Collections;
import java.util.List;

public class RepositorioMedico {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public Medico guardar(Medico m) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(m);
            s.getTransaction().commit();
            return m;
        }
    }

    public Medico actualizar(Medico m) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            Medico merged = s.merge(m);
            s.getTransaction().commit();
            return merged;
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
            return s.createQuery(
                    "FROM Medico ORDER BY nombre", Medico.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Medico> listarPorEspecialidad(String especialidad) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Medico WHERE especialidad = :esp AND estado = 'ACTIVO' " +
                                    "ORDER BY nombre", Medico.class)
                    .setParameter("esp", especialidad)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Medico> listarActivos() {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Medico WHERE estado = 'ACTIVO' ORDER BY nombre",
                    Medico.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Medico buscarPorNroLicencia(String nroLicencia) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Medico WHERE nroLicencia = :lic", Medico.class)
                    .setParameter("lic", nroLicencia)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void actualizarEstado(int medicoId, String estado) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            Medico m = s.get(Medico.class, medicoId);
            if (m != null) {
                m.setEstado(estado);
                s.merge(m);
            }
            s.getTransaction().commit();
        }
    }
}