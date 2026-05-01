package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dias_no_laborables")
public class DiasNoLaborable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha_inicio", nullable = false)
    private String fechaInicio; // YYYY-MM-DD

    @Column(name = "fecha_fin", nullable = false)
    private String fechaFin; // YYYY-MM-DD

    @Column(nullable = false)
    private String tipo; // CIERRE_TOTAL, CIERRE_PARCIAL, INFORMATIVO

    @Column(nullable = false)
    private String motivo;

    @Column(name = "nota_interna")
    private String notaInterna;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrado_por_id", nullable = false)
    private Usuario registradoPor;

    @Column(name = "creado_en")
    private String creadoEn;

    @Column(name = "actualizado_en")
    private String actualizadoEn;

    public DiasNoLaborable() {}

    public int getId()                         { return id; }
    public void setId(int id)                  { this.id = id; }
    public String getFechaInicio()             { return fechaInicio; }
    public void setFechaInicio(String f)       { this.fechaInicio = f; }
    public String getFechaFin()                { return fechaFin; }
    public void setFechaFin(String f)          { this.fechaFin = f; }
    public String getTipo()                    { return tipo; }
    public void setTipo(String t)              { this.tipo = t; }
    public String getMotivo()                  { return motivo; }
    public void setMotivo(String m)            { this.motivo = m; }
    public String getNotaInterna()             { return notaInterna; }
    public void setNotaInterna(String n)       { this.notaInterna = n; }
    public Usuario getRegistradoPor()          { return registradoPor; }
    public void setRegistradoPor(Usuario u)    { this.registradoPor = u; }
    public String getCreadoEn()                { return creadoEn; }
    public void setCreadoEn(String c)          { this.creadoEn = c; }
    public String getActualizadoEn()           { return actualizadoEn; }
    public void setActualizadoEn(String a)     { this.actualizadoEn = a; }
}