package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.HorarioDisponible;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioMedico;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioHorario;

import java.util.List;

/**
 * Inserta datos de prueba (médicos y horarios) si la BD está vacía.
 * Se invoca una sola vez desde AppListener después de inicializar Hibernate.
 */
public class DataSeeder {

    private final RepositorioMedico  repoMedico  = new RepositorioMedico();
    private final RepositorioHorario repoHorario = new RepositorioHorario();

    public void sembrar() {
        try {
            List<Medico> medicos = repoMedico.listar();
            System.out.println("[DataSeeder] Médicos encontrados en BD: " + medicos.size());

            if (!medicos.isEmpty()) {
                System.out.println("[DataSeeder] BD ya tiene datos. Semilla omitida.");
                return;
            }

            System.out.println("[DataSeeder] Insertando datos de prueba...");

            // ── Médico 1 ─────────────────────────────────────────────────────────
            Medico m1 = new Medico(
                    "Dr. Carlos Mendoza",
                    "carlos.mendoza@hospital.com",
                    "med123",
                    "Medicina General",
                    "MED-001"
            );
            repoMedico.guardar(m1);
            System.out.println("[DataSeeder]   ✓ Médico 1 insertado: " + m1.getNombre());

            repoHorario.guardar(new HorarioDisponible("Lunes",    "08:00", "09:00", m1));
            repoHorario.guardar(new HorarioDisponible("Lunes",    "09:00", "10:00", m1));
            repoHorario.guardar(new HorarioDisponible("Miércoles","08:00", "09:00", m1));
            repoHorario.guardar(new HorarioDisponible("Viernes",  "14:00", "15:00", m1));

            // ── Médico 2 ─────────────────────────────────────────────────────────
            Medico m2 = new Medico(
                    "Dra. Sofía Paredes",
                    "sofia.paredes@hospital.com",
                    "med123",
                    "Pediatría",
                    "MED-002"
            );
            repoMedico.guardar(m2);
            System.out.println("[DataSeeder]   ✓ Médico 2 insertado: " + m2.getNombre());

            repoHorario.guardar(new HorarioDisponible("Martes",   "10:00", "11:00", m2));
            repoHorario.guardar(new HorarioDisponible("Martes",   "11:00", "12:00", m2));
            repoHorario.guardar(new HorarioDisponible("Jueves",   "15:00", "16:00", m2));

            // ── Médico 3 ─────────────────────────────────────────────────────────
            Medico m3 = new Medico(
                    "Dr. Andrés Vega",
                    "andres.vega@hospital.com",
                    "med123",
                    "Cardiología",
                    "MED-003"
            );
            repoMedico.guardar(m3);
            System.out.println("[DataSeeder]   ✓ Médico 3 insertado: " + m3.getNombre());

            repoHorario.guardar(new HorarioDisponible("Lunes",    "15:00", "16:00", m3));
            repoHorario.guardar(new HorarioDisponible("Miércoles","16:00", "17:00", m3));
            repoHorario.guardar(new HorarioDisponible("Viernes",  "08:00", "09:00", m3));

            System.out.println("[DataSeeder] ✓ Semilla completada: 3 médicos, 10 horarios insertados.");
        } catch (Exception e) {
            System.err.println("[DataSeeder] ✗ Error durante seeding:");
            e.printStackTrace();
        }
    }
}
