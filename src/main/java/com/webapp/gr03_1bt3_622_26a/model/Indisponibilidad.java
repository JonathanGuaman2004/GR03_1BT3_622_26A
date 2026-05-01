package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "indisponibilidades",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"medico_id", "fecha"}))
public class Indisponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private String fecha; // YYYY-MM-DD

    @Column
    private String motivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrado_por_id", nullable = false)
    private Usuario registradoPor;

    @Column(name = "creado_en")
    private String creadoEn;

    public Indisponibilidad() {}

    public Indisponibilidad(Medico medico, String fecha,
                            String motivo, Usuario registradoPor) {
        this.medico         = medico;
        this.fecha          = fecha;
        this.motivo         = motivo;
        this.registradoPor  = registradoPor;
    }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public Medico getMedico()                { return medico; }
    public void setMedico(Medico m)          { this.medico = m; }
    public String getFecha()                 { return fecha; }
    public void setFecha(String f)           { this.fecha = f; }
    public String getMotivo()                { return motivo; }
    public void setMotivo(String m)          { this.motivo = m; }
    public Usuario getRegistradoPor()        { return registradoPor; }
    public void setRegistradoPor(Usuario u)  { this.registradoPor = u; }
    public String getCreadoEn()              { return creadoEn; }
    public void setCreadoEn(String c)        { this.creadoEn = c; }
}