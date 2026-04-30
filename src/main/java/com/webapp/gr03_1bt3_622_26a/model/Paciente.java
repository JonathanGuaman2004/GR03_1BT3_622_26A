package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PACIENTE")
public class Paciente extends Usuario {

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    public Paciente() {}

    public Paciente(String nombre, String email, String password,
                    String telefono, String cedula) {
        super(nombre, email, password);
        setTelefono(telefono);
        setCedula(cedula);
    }

    @Override
    public String getRol() { return "PACIENTE"; }

    public List<Cita> getCitas()           { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
}