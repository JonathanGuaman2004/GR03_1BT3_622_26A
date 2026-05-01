package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Usuario actor;

    @Column(name = "actor_rol")
    private String actorRol; // ADMINISTRADOR, MEDICO, PACIENTE, SISTEMA

    @Column(nullable = false)
    private String accion; // PUBLICAR_HORARIO, REAGENDAR_CITA, etc.

    @Column(name = "entidad_tipo")
    private String entidadTipo; // CITA, BLOQUE, MEDICO, PACIENTE

    @Column(name = "entidad_id")
    private Integer entidadId;

    @Column
    private String detalle; // JSON o texto libre

    @Column(name = "ip_origen")
    private String ipOrigen;

    @Column(name = "creado_en")
    private String creadoEn;

    public Auditoria() {}

    public Auditoria(Usuario actor, String actorRol, String accion,
                     String entidadTipo, Integer entidadId, String detalle) {
        this.actor       = actor;
        this.actorRol    = actorRol;
        this.accion      = accion;
        this.entidadTipo = entidadTipo;
        this.entidadId   = entidadId;
        this.detalle     = detalle;
    }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public Usuario getActor()                { return actor; }
    public void setActor(Usuario u)          { this.actor = u; }
    public String getActorRol()              { return actorRol; }
    public void setActorRol(String r)        { this.actorRol = r; }
    public String getAccion()                { return accion; }
    public void setAccion(String a)          { this.accion = a; }
    public String getEntidadTipo()           { return entidadTipo; }
    public void setEntidadTipo(String e)     { this.entidadTipo = e; }
    public Integer getEntidadId()            { return entidadId; }
    public void setEntidadId(Integer e)      { this.entidadId = e; }
    public String getDetalle()               { return detalle; }
    public void setDetalle(String d)         { this.detalle = d; }
    public String getIpOrigen()              { return ipOrigen; }
    public void setIpOrigen(String i)        { this.ipOrigen = i; }
    public String getCreadoEn()              { return creadoEn; }
    public void setCreadoEn(String c)        { this.creadoEn = c; }
}