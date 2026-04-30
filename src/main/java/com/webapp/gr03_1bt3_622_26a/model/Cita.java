package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "citas")
public class Cita {

    public static final String PROGRAMADA            = "PROGRAMADA";
    public static final String COMPLETADA            = "COMPLETADA";
    public static final String AUSENTE               = "AUSENTE";
    public static final String CANCELADA             = "CANCELADA";
    public static final String REAGENDADA            = "REAGENDADA";
    public static final String EN_ESPERA_REASIGNACION = "EN_ESPERA_REASIGNACION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bloque_id", nullable = false)
    private BloqueHorario bloque;

    @Column(nullable = false)
    private String estado = PROGRAMADA;

    @Column
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendado_por_id", nullable = false)
    private Usuario agendadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelado_por_id")
    private Usuario canceladoPor;

    @Column(name = "cancelado_en")
    private String canceladoEn;

    @Column(name = "motivo_cancelacion")
    private String motivoCancelacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_origen_id")
    private Cita citaOrigen;

    @Column(name = "creado_en")
    private String creadoEn;

    @Column(name = "actualizado_en")
    private String actualizadoEn;

    public Cita() {}

    public Cita(Paciente paciente, Medico medico,
                BloqueHorario bloque, String motivo, Usuario agendadoPor) {
        this.paciente    = paciente;
        this.medico      = medico;
        this.bloque      = bloque;
        this.motivo      = motivo;
        this.agendadoPor = agendadoPor;
        this.estado      = PROGRAMADA;
    }

    public void cancelar(Usuario actor, String motivo) {
        this.estado            = CANCELADA;
        this.canceladoPor      = actor;
        this.motivoCancelacion = motivo;
    }

    public boolean isProgramada() { return PROGRAMADA.equals(estado); }
    public boolean isReagendada() { return REAGENDADA.equals(estado); }
    public boolean isCancelable() { return isProgramada() || isReagendada(); }

    public int getId()                           { return id; }
    public void setId(int id)                    { this.id = id; }
    public Paciente getPaciente()                { return paciente; }
    public void setPaciente(Paciente p)          { this.paciente = p; }
    public Medico getMedico()                    { return medico; }
    public void setMedico(Medico m)              { this.medico = m; }
    public BloqueHorario getBloque()             { return bloque; }
    public void setBloque(BloqueHorario b)       { this.bloque = b; }
    public String getEstado()                    { return estado; }
    public void setEstado(String e)              { this.estado = e; }
    public String getMotivo()                    { return motivo; }
    public void setMotivo(String m)              { this.motivo = m; }
    public Usuario getAgendadoPor()              { return agendadoPor; }
    public void setAgendadoPor(Usuario u)        { this.agendadoPor = u; }
    public Usuario getCanceladoPor()             { return canceladoPor; }
    public void setCanceladoPor(Usuario u)       { this.canceladoPor = u; }
    public String getCanceladoEn()               { return canceladoEn; }
    public void setCanceladoEn(String c)         { this.canceladoEn = c; }
    public String getMotivoCancelacion()         { return motivoCancelacion; }
    public void setMotivoCancelacion(String m)   { this.motivoCancelacion = m; }
    public Cita getCitaOrigen()                  { return citaOrigen; }
    public void setCitaOrigen(Cita c)            { this.citaOrigen = c; }
    public String getCreadoEn()                  { return creadoEn; }
    public void setCreadoEn(String c)            { this.creadoEn = c; }
    public String getActualizadoEn()             { return actualizadoEn; }
    public void setActualizadoEn(String a)       { this.actualizadoEn = a; }

    @Override
    public String toString() {
        return "Cita{id=" + id + ", estado=" + estado
                + ", bloque=" + (bloque != null ? bloque.getFecha()
                                                  + " " + bloque.getHoraInicio() : "null") + "}";
    }
}