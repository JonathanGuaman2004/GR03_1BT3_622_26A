package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_citas")
public class HistorialCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(name = "estado_anterior")
    private String estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    private String estadoNuevo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Usuario actor; // null = scheduler automático

    @Column(name = "actor_tipo")
    private String actorTipo; // ADMINISTRADOR, MEDICO, PACIENTE, SISTEMA

    @Column
    private String nota;

    @Column(name = "creado_en")
    private String creadoEn;

    public HistorialCita() {}

    public HistorialCita(Cita cita, String estadoAnterior,
                         String estadoNuevo, Usuario actor,
                         String actorTipo, String nota) {
        this.cita           = cita;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo    = estadoNuevo;
        this.actor          = actor;
        this.actorTipo      = actorTipo;
        this.nota           = nota;
    }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public Cita getCita()                    { return cita; }
    public void setCita(Cita c)              { this.cita = c; }
    public String getEstadoAnterior()        { return estadoAnterior; }
    public void setEstadoAnterior(String e)  { this.estadoAnterior = e; }
    public String getEstadoNuevo()           { return estadoNuevo; }
    public void setEstadoNuevo(String e)     { this.estadoNuevo = e; }
    public Usuario getActor()                { return actor; }
    public void setActor(Usuario u)          { this.actor = u; }
    public String getActorTipo()             { return actorTipo; }
    public void setActorTipo(String t)       { this.actorTipo = t; }
    public String getNota()                  { return nota; }
    public void setNota(String n)            { this.nota = n; }
    public String getCreadoEn()              { return creadoEn; }
    public void setCreadoEn(String c)        { this.creadoEn = c; }
}