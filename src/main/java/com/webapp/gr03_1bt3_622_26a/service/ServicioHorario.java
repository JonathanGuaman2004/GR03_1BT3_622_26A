package com.webapp.gr03_1bt3_622_26a.service;

import com.webapp.gr03_1bt3_622_26a.model.BloqueHorario;
import com.webapp.gr03_1bt3_622_26a.model.Medico;
import com.webapp.gr03_1bt3_622_26a.model.PlantillaHoraria;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioBloqueHorario;
import com.webapp.gr03_1bt3_622_26a.repository.RepositorioPlantillaHoraria;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServicioHorario {

    private final RepositorioPlantillaHoraria repoPlantilla;
    private final RepositorioBloqueHorario    repoBloque;

    public ServicioHorario() {
        this.repoPlantilla = new RepositorioPlantillaHoraria();
        this.repoBloque    = new RepositorioBloqueHorario();
    }

    public ServicioHorario(RepositorioPlantillaHoraria repoPlantilla,
                           RepositorioBloqueHorario repoBloque) {
        this.repoPlantilla = repoPlantilla;
        this.repoBloque    = repoBloque;
    }

    public List<BloqueHorario> generarBloques(Medico medico,
                                              String fechaInicioStr,
                                              String fechaFinStr) {
        List<PlantillaHoraria> plantilla = repoPlantilla.buscarPorMedico(medico.getId());

        if (plantilla == null || plantilla.isEmpty()) {
            throw new IllegalStateException(
                    "El médico no tiene plantilla semanal configurada.");
        }

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin    = LocalDate.parse(fechaFinStr);

        List<BloqueHorario> bloquesGenerados = new ArrayList<>();

        for (LocalDate fecha = fechaInicio;
             !fecha.isAfter(fechaFin);
             fecha = fecha.plusDays(1)) {

            String diaSemana = traducirDia(fecha.getDayOfWeek());

            for (PlantillaHoraria franja : plantilla) {
                if (!franja.getDiaSemana().equals(diaSemana)) {
                    continue;
                }

                LocalTime horaActual = LocalTime.parse(franja.getHoraInicio());
                LocalTime horaFin    = LocalTime.parse(franja.getHoraFin());
                LocalTime almuerzoInicio = LocalTime.of(13, 0);
                LocalTime almuerzoFin    = LocalTime.of(14, 0);

                while (horaActual.isBefore(horaFin)) {
                    LocalTime siguienteSlot = horaActual.plusMinutes(30);

                    boolean enAlmuerzo = !horaActual.isBefore(almuerzoInicio)
                            && horaActual.isBefore(almuerzoFin);

                    if (!enAlmuerzo) {
                        String fechaStr = fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
                        String inicioStr = horaActual.toString();
                        String finStr    = siguienteSlot.toString();

                        BloqueHorario bloque = new BloqueHorario(
                                medico, fechaStr, inicioStr, finStr);
                        repoBloque.guardar(bloque);
                        bloquesGenerados.add(bloque);
                    }

                    horaActual = siguienteSlot;
                }
            }
        }

        return bloquesGenerados;
    }

    private String traducirDia(DayOfWeek dia) {
        switch (dia) {
            case MONDAY:    return "LUNES";
            case TUESDAY:   return "MARTES";
            case WEDNESDAY: return "MIERCOLES";
            case THURSDAY:  return "JUEVES";
            case FRIDAY:    return "VIERNES";
            case SATURDAY:  return "SABADO";
            default:        return "DOMINGO";
        }
    }
}