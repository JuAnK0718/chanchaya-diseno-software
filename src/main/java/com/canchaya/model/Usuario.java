package com.canchaya.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase abstracta base de los usuarios del sistema.
 * Cliente y AdminEstablecimiento heredan de aqui (herencia).
 * El metodo getResumenPerfil() es abstracto: cada subclase lo implementa
 * de forma distinta (polimorfismo de subtipo).
 *
 * Cliente y AdminEstablecimiento se guardan en la misma coleccion "usuarios".
 */
@Document(collection = "usuarios")
public abstract class Usuario {

    @Id
    private String id;
    private String nombre;
    private String telefono;
    private String email;
    private String passwordHash;
    private RolUsuario rol;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String telefono, String email,
                   String passwordHash, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    /** Cada subclase devuelve su propio resumen de perfil. */
    public abstract String getResumenPerfil();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nombre='" + nombre + "', email='" + email
                + "', rol=" + rol + "}";
    }
}
