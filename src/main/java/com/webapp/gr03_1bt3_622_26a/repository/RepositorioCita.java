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

    public Cita actualizar(Cita cita) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            Cita merged = s.merge(cita);
            s.getTransaction().commit();
            return merged;
        }
    }

    public List<Cita> buscarPorPaciente(int pacienteId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Cita c WHERE c.paciente.id = :pid " +
                                    "ORDER BY c.bloque.fecha DESC, c.bloque.horaInicio DESC",
                            Cita.class)
                    .setParameter("pid", pacienteId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Cita> buscarActivasPorPaciente(int pacienteId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Cita c WHERE c.paciente.id = :pid " +
                                    "AND c.estado IN ('PROGRAMADA','REAGENDADA','EN_ESPERA_REASIGNACION') " +
                                    "ORDER BY c.bloque.fecha, c.bloque.horaInicio",
                            Cita.class)
                    .setParameter("pid", pacienteId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Cita> buscarHistorialPorPaciente(int pacienteId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM Cita c WHERE c.paciente.id = :pid " +
                                    "AND c.estado IN ('COMPLETADA','AUSENTE','CANCELADA','REAGENDADA') " +
                                    "ORDER BY c.bloque.fecha DESC",
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
                            "FROM Cita c WHERE c.medico.id = :mid " +
                                    "ORDER BY c.bloque.fecha DESC, c.bloque.horaInicio DESC",
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
    
    public long contarCitasActivasPorMedico(int medicoId) {
        try (org.hibernate.Session s = sf().openSession()) {
            Long resultado = s.createQuery(
                            "SELECT COUNT(c) FROM Cita c " +
                                    "WHERE c.medico.id = :mid " +
                                    "AND c.estado IN ('PROGRAMADA','REAGENDADA')",
                            Long.class)
                    .setParameter("mid", medicoId)
                    .uniqueResult();
            return resultado != null ? resultado : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }
}