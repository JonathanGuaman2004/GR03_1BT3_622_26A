package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private String estado;  // "PENDIENTE", "CONFIRMADA", "CANCELADA"

    @Column
    private String motivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    private HorarioDisponible horario;

    public Cita() {}

    public Cita(LocalDate fecha, LocalTime hora, String motivo,
                Paciente paciente, Medico medico, HorarioDisponible horario) {
        this.fecha    = fecha;
        this.hora     = hora;
        this.motivo   = motivo;
        this.paciente = paciente;
        this.medico   = medico;
        this.horario  = horario;
        this.estado   = "PENDIENTE";
    }

    /** Cambia el estado a CONFIRMADA. */
    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    /** Cambia el estado a CANCELADA y libera el horario asociado. */
    public void cancelar() {
        this.estado = "CANCELADA";
        if (this.horario != null) {
            this.horario.liberar();
        }
    }

    // Getters y Setters
    public int getId()                           { return id; }
    public void setId(int id)                    { this.id = id; }

    public LocalDate getFecha()                  { return fecha; }
    public void setFecha(LocalDate f)            { this.fecha = f; }

    public LocalTime getHora()                   { return hora; }
    public void setHora(LocalTime h)             { this.hora = h; }

    public String getEstado()                    { return estado; }
    public void setEstado(String e)              { this.estado = e; }

    public String getMotivo()                    { return motivo; }
    public void setMotivo(String m)              { this.motivo = m; }

    public Paciente getPaciente()                { return paciente; }
    public void setPaciente(Paciente p)          { this.paciente = p; }

    public Medico getMedico()                    { return medico; }
    public void setMedico(Medico m)              { this.medico = m; }

    public HorarioDisponible getHorario()        { return horario; }
    public void setHorario(HorarioDisponible h)  { this.horario = h; }

    @Override
    public String toString() {
        return "Cita{id=" + id + ", fecha=" + fecha + ", hora=" + hora
                + ", estado='" + estado + "'}";
    }
}