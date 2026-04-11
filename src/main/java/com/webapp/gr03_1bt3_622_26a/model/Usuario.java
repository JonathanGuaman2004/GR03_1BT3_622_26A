package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

/**
 * Entidad base para Paciente y Medico.
 * Usa herencia de tabla única (SINGLE_TABLE) con discriminador.
 */
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Constructor vacío requerido por Hibernate
    public Usuario() {}

    public Usuario(String nombre, String email, String password) {
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
    }

    /** Devuelve el rol del usuario según la subclase. */
    public abstract String getRol();

    /** Valida que la clave proporcionada coincida con la almacenada. */
    public boolean validarPassword(String clave) {
        return this.password != null && this.password.equals(clave);
    }

    // ── Getters y Setters ──────────────────────────────────────────────────────
    public int getId()                     { return id; }
    public void setId(int id)              { this.id = id; }

    public String getNombre()              { return nombre; }
    public void setNombre(String nombre)   { this.nombre = nombre; }

    public String getEmail()               { return email; }
    public void setEmail(String email)     { this.email = email; }

    public String getPassword()            { return password; }
    public void setPassword(String p)      { this.password = p; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", email='" + email + "'}";
    }
}
