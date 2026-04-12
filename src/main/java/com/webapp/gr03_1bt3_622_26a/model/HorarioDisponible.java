package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "horarios_disponibles")
public class HorarioDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String diaSemana;   // Ej: "Lunes", "Martes"

    @Column(nullable = false)
    private String horaInicio;  // Ej: "09:00"

    @Column(nullable = false)
    private String horaFin;     // Ej: "10:00"

    @Column(nullable = false)
    private boolean disponible = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;


    public HorarioDisponible() {}

    public HorarioDisponible(String diaSemana, String horaInicio, String horaFin, Medico medico) {
        this.diaSemana  = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
        this.medico     = medico;
        this.disponible = true;
    }

    /** Marca el horario como ocupado al reservar una cita. */
    public void reservar() {
        this.disponible = false;
    }

    /** Restaura el horario si la cita es cancelada. */
    public void liberar() {
        this.disponible = true;
    }

    // Getters y Setters
    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }

    public String getDiaSemana()             { return diaSemana; }
    public void setDiaSemana(String d)       { this.diaSemana = d; }

    public String getHoraInicio()            { return horaInicio; }
    public void setHoraInicio(String h)      { this.horaInicio = h; }

    public String getHoraFin()               { return horaFin; }
    public void setHoraFin(String h)         { this.horaFin = h; }

    public boolean isDisponible()            { return disponible; }
    public void setDisponible(boolean d)     { this.disponible = d; }

    public Medico getMedico()                { return medico; }
    public void setMedico(Medico m)          { this.medico = m; }

    @Override
    public String toString() {
        return diaSemana + " " + horaInicio + "-" + horaFin
                + (disponible ? " [Disponible]" : " [Ocupado]");
    }
}