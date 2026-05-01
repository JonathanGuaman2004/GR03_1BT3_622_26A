package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plantilla_horaria",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"medico_id", "dia_semana", "hora_inicio"}))
public class PlantillaHoraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana; // LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio; // HH:MM

    @Column(name = "hora_fin", nullable = false)
    private String horaFin; // HH:MM

    @Column(nullable = false)
    private int activo = 1;

    @Column(name = "creado_en")
    private String creadoEn;

    public PlantillaHoraria() {}

    public PlantillaHoraria(Medico medico, String diaSemana,
                            String horaInicio, String horaFin) {
        this.medico     = medico;
        this.diaSemana  = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
        this.activo     = 1;
    }

    public int getId()                     { return id; }
    public void setId(int id)              { this.id = id; }
    public Medico getMedico()              { return medico; }
    public void setMedico(Medico m)        { this.medico = m; }
    public String getDiaSemana()           { return diaSemana; }
    public void setDiaSemana(String d)     { this.diaSemana = d; }
    public String getHoraInicio()          { return horaInicio; }
    public void setHoraInicio(String h)    { this.horaInicio = h; }
    public String getHoraFin()             { return horaFin; }
    public void setHoraFin(String h)       { this.horaFin = h; }
    public int getActivo()                 { return activo; }
    public void setActivo(int a)           { this.activo = a; }
    public String getCreadoEn()            { return creadoEn; }
    public void setCreadoEn(String c)      { this.creadoEn = c; }
}