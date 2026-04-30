package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioHorario {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public HorarioDisponible guardar(HorarioDisponible h) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(h);
            s.getTransaction().commit();
            return h;
        }
    }

    public HorarioDisponible buscarPorId(int id) {
        try (Session s = sf().openSession()) {
            return s.get(HorarioDisponible.class, id);
        }
    }

    public List<HorarioDisponible> buscarPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM HorarioDisponible h WHERE h.medico.id = :mid ORDER BY h.diaSemana, h.horaInicio",
                    HorarioDisponible.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<HorarioDisponible> buscarDisponibles(int medicoId) {
        try (Session s = sf().openSession()) {
            List<HorarioDisponible> result = s.createQuery(
                    "FROM HorarioDisponible h WHERE h.medico.id = :mid AND h.disponible = true ORDER BY h.diaSemana, h.horaInicio",
                    HorarioDisponible.class)
                    .setParameter("mid", medicoId)
                    .list();
            System.out.println("[RepositorioHorario] buscarDisponibles() para médico " + medicoId + " retornó " + result.size() + " horarios.");
            return result;
        } catch (Exception e) {
            System.err.println("[RepositorioHorario] Error en buscarDisponibles():");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void actualizarDisponibilidad(int id, boolean disponible) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            HorarioDisponible h = s.get(HorarioDisponible.class, id);
            if (h != null) {
                h.setDisponible(disponible);
                s.merge(h);
            }
            s.getTransaction().commit();
        }
    }
}
