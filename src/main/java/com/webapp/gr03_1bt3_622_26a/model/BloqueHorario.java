package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bloques_horario",
        uniqueConstraints = @UniqueConstraint(columnNames = {"medico_id","fecha","hora_inicio"}))
public class BloqueHorario {

    public static final String DISPONIBLE = "DISPONIBLE";
    public static final String OCUPADO    = "OCUPADO";
    public static final String BLOQUEADO  = "BLOQUEADO";
    public static final String EXPIRADO   = "EXPIRADO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private String fecha;           // YYYY-MM-DD

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;      // HH:MM

    @Column(name = "hora_fin", nullable = false)
    private String horaFin;         // HH:MM

    @Column(nullable = false)
    private String estado = DISPONIBLE;

    @Column(nullable = false)
    private int publicado = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicado_por_id")
    private Usuario publicadoPor;

    @Column(name = "publicado_en")
    private String publicadoEn;

    @Column(name = "creado_en")
    private String creadoEn;

    @Column(name = "actualizado_en")
    private String actualizadoEn;

    public BloqueHorario() {}

    public BloqueHorario(Medico medico, String fecha,
                         String horaInicio, String horaFin) {
        this.medico     = medico;
        this.fecha      = fecha;
        this.horaInicio = horaInicio;
        this.horaFin    = horaFin;
        this.estado     = DISPONIBLE;
        this.publicado  = 0;
    }

    public boolean isDisponible() { return DISPONIBLE.equals(estado); }
    public boolean isPublicado()  { return publicado == 1; }

    public void ocupar()    { this.estado = OCUPADO; }
    public void bloquear()  { this.estado = BLOQUEADO; }
    public void expirar()   { this.estado = EXPIRADO; }
    public void liberar()   { this.estado = DISPONIBLE; }

    public int getId()                       { return id; }
    public void setId(int id)                { this.id = id; }
    public Medico getMedico()                { return medico; }
    public void setMedico(Medico m)          { this.medico = m; }
    public String getFecha()                 { return fecha; }
    public void setFecha(String f)           { this.fecha = f; }
    public String getHoraInicio()            { return horaInicio; }
    public void setHoraInicio(String h)      { this.horaInicio = h; }
    public String getHoraFin()               { return horaFin; }
    public void setHoraFin(String h)         { this.horaFin = h; }
    public String getEstado()                { return estado; }
    public void setEstado(String e)          { this.estado = e; }
    public int getPublicado()                { return publicado; }
    public void setPublicado(int p)          { this.publicado = p; }
    public Usuario getPublicadoPor()         { return publicadoPor; }
    public void setPublicadoPor(Usuario u)   { this.publicadoPor = u; }
    public String getPublicadoEn()           { return publicadoEn; }
    public void setPublicadoEn(String p)     { this.publicadoEn = p; }
    public String getCreadoEn()              { return creadoEn; }
    public void setCreadoEn(String c)        { this.creadoEn = c; }
    public String getActualizadoEn()         { return actualizadoEn; }
    public void setActualizadoEn(String a)   { this.actualizadoEn = a; }

    @Override
    public String toString() {
        return "BloqueHorario{fecha=" + fecha + ", " + horaInicio
                + "-" + horaFin + ", estado=" + estado + "}";
    }
}