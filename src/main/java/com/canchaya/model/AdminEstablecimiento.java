package com.canchaya.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * AdminEstablecimiento: usuario que administra un establecimiento.
 * Referencia directa a su establecimiento (@DBRef), asociacion bidireccional.
 */
public class AdminEstablecimiento extends Usuario {

    @DBRef(lazy = true)
    private Establecimiento establecimiento;

    public AdminEstablecimiento() {
        super();
    }

    public AdminEstablecimiento(String id, String nombre, String telefono, String email,
                                String passwordHash, RolUsuario rol,
                                Establecimiento establecimiento) {
        super(id, nombre, telefono, email, passwordHash, rol);
        this.establecimiento = establecimiento;
    }

    /** Polimorfismo: el admin resume su perfil con nombre y nombre del establecimiento. */
    @Override
    public String getResumenPerfil() {
        String nombreEstab = (establecimiento != null) ? establecimiento.getNombre() : "sin establecimiento";
        return getNombre() + " - " + nombreEstab;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    @Override
    public String toString() {
        String estabId = (establecimiento != null) ? establecimiento.getId() : null;
        return "AdminEstablecimiento{id='" + getId() + "', nombre='" + getNombre()
                + "', establecimientoId='" + estabId + "'}";
    }
}
