package com.webapp.gr03_1bt3_622_26a.repository;

import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class RepositorioPlantillaHoraria {

    private SessionFactory sf() {
        return HibernateUtil.getSessionFactory();
    }

    public PlantillaHoraria guardar(PlantillaHoraria p) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            s.persist(p);
            s.getTransaction().commit();
            return p;
        }
    }

    public boolean existeDuplicado(int medicoId, String diaSemana, String horaInicio) {
        try (Session s = sf().openSession()) {
            Long count = s.createQuery(
                            "SELECT COUNT(p) FROM PlantillaHoraria p " +
                                    "WHERE p.medico.id = :mid " +
                                    "AND p.diaSemana = :dia " +
                                    "AND p.horaInicio = :hora",
                            Long.class)
                    .setParameter("mid",  medicoId)
                    .setParameter("dia",  diaSemana)
                    .setParameter("hora", horaInicio)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<PlantillaHoraria> buscarPorMedico(int medicoId) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM PlantillaHoraria p WHERE p.medico.id = :mid " +
                                    "AND p.activo = 1 ORDER BY p.diaSemana, p.horaInicio",
                            PlantillaHoraria.class)
                    .setParameter("mid", medicoId)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<PlantillaHoraria> buscarPorMedicoYDia(int medicoId, String diaSemana) {
        try (Session s = sf().openSession()) {
            return s.createQuery(
                            "FROM PlantillaHoraria p WHERE p.medico.id = :mid " +
                                    "AND p.diaSemana = :dia AND p.activo = 1 " +
                                    "ORDER BY p.horaInicio",
                            PlantillaHoraria.class)
                    .setParameter("mid", medicoId)
                    .setParameter("dia", diaSemana)
                    .list();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void eliminar(int id) {
        try (Session s = sf().openSession()) {
            s.beginTransaction();
            PlantillaHoraria p = s.get(PlantillaHoraria.class, id);
            if (p != null) {
                p.setActivo(0);
                s.merge(p);
            }
            s.getTransaction().commit();
        }
    }

    public boolean existeFranjaPorDia(int medicoId, String diaSemana) {
        try (Session s = sf().openSession()) {
            Long count = s.createQuery(
                            "SELECT COUNT(p) FROM PlantillaHoraria p " +
                                    "WHERE p.medico.id = :mid " +
                                    "AND p.diaSemana = :dia " +
                                    "AND p.activo = 1",
                            Long.class)
                    .setParameter("mid", medicoId)
                    .setParameter("dia", diaSemana)
                    .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            return false;
        }
    }
}