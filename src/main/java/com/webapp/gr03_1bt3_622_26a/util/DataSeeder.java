package com.webapp.gr03_1bt3_622_26a.util;

import com.webapp.gr03_1bt3_622_26a.model.*;
import com.webapp.gr03_1bt3_622_26a.repository.*;
import java.util.List;

public class DataSeeder {

    private final RepositorioMedico        repoMedico  = new RepositorioMedico();
    private final RepositorioBloqueHorario repoBloque  = new RepositorioBloqueHorario();

    public void sembrar() {
        try {
            List<Medico> medicos = repoMedico.listar();
            if (!medicos.isEmpty()) {
                System.out.println("[DataSeeder] BD ya tiene datos. Omitido.");
                return;
            }

            System.out.println("[DataSeeder] Insertando datos de prueba...");

            Medico m1 = new Medico("Dr. Carlos Mendoza",
                    "carlos.mendoza@hospital.com", "med123",
                    "Medicina General", "MED-001");
            repoMedico.guardar(m1);

            // Bloques publicados para m1
            guardarBloque(m1, "2025-06-02", "08:00", "08:30");
            guardarBloque(m1, "2025-06-02", "08:30", "09:00");
            guardarBloque(m1, "2025-06-04", "08:00", "08:30");

            Medico m2 = new Medico("Dra. Sofía Paredes",
                    "sofia.paredes@hospital.com", "med123",
                    "Pediatría", "MED-002");
            repoMedico.guardar(m2);

            guardarBloque(m2, "2025-06-03", "10:00", "10:30");
            guardarBloque(m2, "2025-06-03", "10:30", "11:00");

            Medico m3 = new Medico("Dr. Andrés Vega",
                    "andres.vega@hospital.com", "med123",
                    "Cardiología", "MED-003");
            repoMedico.guardar(m3);

            guardarBloque(m3, "2025-06-02", "15:00", "15:30");
            guardarBloque(m3, "2025-06-05", "08:00", "08:30");

            System.out.println("[DataSeeder] Semilla completada.");
        } catch (Exception e) {
            System.err.println("[DataSeeder] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void guardarBloque(Medico medico, String fecha,
                               String inicio, String fin) {
        BloqueHorario b = new BloqueHorario(medico, fecha, inicio, fin);
        b.setPublicado(1);
        repoBloque.guardar(b);
    }
}