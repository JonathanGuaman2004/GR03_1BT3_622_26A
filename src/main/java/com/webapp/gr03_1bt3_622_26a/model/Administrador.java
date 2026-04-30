package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    public Administrador() {}

    public Administrador(String nombre, String email, String password) {
        super(nombre, email, password);
    }

    @Override
    public String getRol() { return "ADMINISTRADOR"; }
}