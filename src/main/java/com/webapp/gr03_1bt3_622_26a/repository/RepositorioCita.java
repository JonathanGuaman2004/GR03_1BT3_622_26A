package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.Cita;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioCita {

    private SessionFactory sf() { return HibernateUtil.getSessionFactory(); }

    public Cita guardar(Cita cita) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(cita);
            s.getTransaction().commit();
            return cita;
        }
    }

    public Cita buscarPorId(int id) {
        try (Session s = sf().openSession()) {
            return s.get(Cita.class, id);
        }
    }

    public List<Cita> buscarPorPaciente(int pacienteId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Cita c WHERE c.paciente.id = :pid ORDER BY c.fecha DESC, c.hora DESC",
                    Cita.class)
                    .setParameter("pid", pacienteId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Cita> buscarPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                    "FROM Cita c WHERE c.medico.id = :mid ORDER BY c.fecha DESC, c.hora DESC",
                    Cita.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void actualizarEstado(int id, String estado) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            Cita c = s.get(Cita.class, id);
            if (c != null) {
                c.setEstado(estado);
                s.merge(c);
            }
            s.getTransaction().commit();
        }
    }

    public List<Cita> listar() {
        try (Session s = sf().openSession()) {
            return s.createQuery("FROM Cita ORDER BY fecha DESC", Cita.class).list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Cancela una cita de forma atómica (búsqueda y actualización en una sola transacción).
     * Retorna la cita cancelada para poder obtener el horario.
     * @param id ID de la cita a cancelar
     * @return La cita cancelada, o null si no existe
     */
    public Cita cancelarCita(int id) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            Cita c = s.get(Cita.class, id);
            if (c != null) {
                c.setEstado("CANCELADA");
                s.merge(c);
            }
            s.getTransaction().commit();
            return c;
        }
    }
}
