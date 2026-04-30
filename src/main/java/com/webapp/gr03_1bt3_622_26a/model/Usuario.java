package com.webapp.gr03_1bt3_622_26a.model;

import jakarta.persistence.*;

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

    @Column(name = "debe_cambiar_pwd", nullable = false)
    private int debeCambiarPwd = 0;

    @Column
    private String cedula;

    @Column
    private String telefono;

    @Column(nullable = false)
    private String estado = "ACTIVO";

    @Column(name = "creado_en")
    private String creadoEn;

    @Column(name = "actualizado_en")
    private String actualizadoEn;

    public Usuario() {}

    public Usuario(String nombre, String email, String password) {
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
        this.estado   = "ACTIVO";
        this.debeCambiarPwd = 0;
    }

    public abstract String getRol();

    public boolean validarPassword(String clave) {
        return this.password != null && this.password.equals(clave);
    }

    public boolean isDebeCambiarPwd() { return debeCambiarPwd == 1; }
    public void setDebeCambiarPwd(int v) { this.debeCambiarPwd = v; }

    public int getId()                     { return id; }
    public void setId(int id)              { this.id = id; }
    public String getNombre()              { return nombre; }
    public void setNombre(String nombre)   { this.nombre = nombre; }
    public String getEmail()               { return email; }
    public void setEmail(String email)     { this.email = email; }
    public String getPassword()            { return password; }
    public void setPassword(String p)      { this.password = p; }
    public String getCedula()              { return cedula; }
    public void setCedula(String c)        { this.cedula = c; }
    public String getTelefono()            { return telefono; }
    public void setTelefono(String t)      { this.telefono = t; }
    public String getEstado()              { return estado; }
    public void setEstado(String e)        { this.estado = e; }
    public String getCreadoEn()            { return creadoEn; }
    public void setCreadoEn(String c)      { this.creadoEn = c; }
    public String getActualizadoEn()       { return actualizadoEn; }
    public void setActualizadoEn(String a) { this.actualizadoEn = a; }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", email='" + email + "'}";
    }
}