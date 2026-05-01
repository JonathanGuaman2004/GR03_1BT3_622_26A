package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(nullable = false)
    private String tipo; // CUENTA_CREADA, CITA_AGENDADA, CITA_CANCELADA, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(nullable = false)
    private int enviado = 0; // 0=pendiente, 1=enviado, 2=error

    @Column(name = "enviado_en")
    private String enviadoEn;

    @Column(name = "detalle_error")
    private String detalleError;

    @Column(name = "creado_en")
    private String creadoEn;

    public Notificacion() {}

    public Notificacion(Usuario destinatario, String tipo, Cita cita) {
        this.destinatario = destinatario;
        this.tipo         = tipo;
        this.cita         = cita;
        this.enviado      = 0;
    }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public Usuario getDestinatario()         { return destinatario; }
    public void setDestinatario(Usuario u)   { this.destinatario = u; }
    public String getTipo()                  { return tipo; }
    public void setTipo(String t)            { this.tipo = t; }
    public Cita getCita()                    { return cita; }
    public void setCita(Cita c)              { this.cita = c; }
    public int getEnviado()                  { return enviado; }
    public void setEnviado(int e)            { this.enviado = e; }
    public String getEnviadoEn()             { return enviadoEn; }
    public void setEnviadoEn(String e)       { this.enviadoEn = e; }
    public String getDetalleError()          { return detalleError; }
    public void setDetalleError(String d)    { this.detalleError = d; }
    public String getCreadoEn()              { return creadoEn; }
    public void setCreadoEn(String c)        { this.creadoEn = c; }
}