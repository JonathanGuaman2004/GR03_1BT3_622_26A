package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MEDICO")
public class Medico extends Usuario {

    @Column
    private String especialidad;

    @Column
    private String nroLicencia;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponible> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    public Medico() {}

    public Medico(String nombre, String email, String password,
                  String especialidad, String nroLicencia) {
        super(nombre, email, password);
        this.especialidad = especialidad;
        this.nroLicencia  = nroLicencia;
    }

    @Override
    public String getRol() { return "MEDICO"; }

    /** Verifica si el médico tiene al menos un horario disponible. */
    public boolean tieneDisponibilidad() {
        return horarios.stream().anyMatch(HorarioDisponible::isDisponible);
    }

    // Getters y Setters
    public String getEspecialidad()                  { return especialidad; }
    public void setEspecialidad(String e)            { this.especialidad = e; }

    public String getNroLicencia()                   { return nroLicencia; }
    public void setNroLicencia(String n)             { this.nroLicencia = n; }

    public List<HorarioDisponible> getHorarios()     { return horarios; }
    public void setHorarios(List<HorarioDisponible> h) { this.horarios = h; }

    public List<Cita> getCitas()                     { return citas; }
    public void setCitas(List<Cita> citas)           { this.citas = citas; }
}
