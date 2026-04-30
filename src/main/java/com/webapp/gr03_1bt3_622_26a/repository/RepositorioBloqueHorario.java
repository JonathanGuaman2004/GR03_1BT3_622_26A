package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Collections;
import java.util.List;

public class RepositorioBloqueHorario {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public BloqueHorario guardar(BloqueHorario b) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(b);
            s.getTransaction().commit();
            return b;
        }
    }

    public BloqueHorario buscarPorId(int id) {
        try (Session s = sf().openSession()) {
            return s.get(BloqueHorario.class, id);
        }
    }

    public BloqueHorario actualizar(BloqueHorario b) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            BloqueHorario merged = s.merge(b);
            s.getTransaction().commit();
            return merged;
        }
    }

    public List<BloqueHorario> buscarDisponiblesPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM BloqueHorario b WHERE b.medico.id = :mid " +
                                    "AND b.estado = 'DISPONIBLE' AND b.publicado = 1 " +
                                    "ORDER BY b.fecha, b.horaInicio", BloqueHorario.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<BloqueHorario> buscarPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM BloqueHorario b WHERE b.medico.id = :mid " +
                                    "ORDER BY b.fecha, b.horaInicio", BloqueHorario.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void actualizarEstado(int id, String estado) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            BloqueHorario b = s.get(BloqueHorario.class, id);
            if (b != null) {
                b.setEstado(estado);
                s.merge(b);
            }
            s.getTransaction().commit();
        }
    }
}