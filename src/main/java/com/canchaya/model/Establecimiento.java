package com.canchaya.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Establecimiento: complejo deportivo que agrupa varias canchas.
 * Referencia directa a su administrador (@DBRef), asociacion bidireccional.
 */
@Document(collection = "establecimientos")
public class Establecimiento {

    @Id
    private String id;
    private String nombre;
    private String direccion;

    @DBRef(lazy = true)
    private AdminEstablecimiento administrador;

    public Establecimiento() {
    }

    public Establecimiento(String id, String nombre, String direccion,
                           AdminEstablecimiento administrador) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.administrador = administrador;
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public AdminEstablecimiento getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdminEstablecimiento administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        String adminId = (administrador != null) ? administrador.getId() : null;
        return "Establecimiento{id='" + id + "', nombre='" + nombre
                + "', direccion='" + direccion + "', administradorId='" + adminId + "'}";
    }
}
