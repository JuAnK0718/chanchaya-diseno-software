package com.canchaya.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Cliente: usuario que reserva canchas.
 * Mantiene la lista de sus reservas como referencia directa a objetos (@DBRef).
 */
public class Cliente extends Usuario {

    @DBRef(lazy = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente() {
        super();
    }

    public Cliente(String id, String nombre, String telefono, String email,
                   String passwordHash, RolUsuario rol, List<Reserva> reservas) {
        super(id, nombre, telefono, email, passwordHash, rol);
        this.reservas = reservas;
    }

    /** Polimorfismo: el cliente resume su perfil con nombre y telefono. */
    @Override
    public String getResumenPerfil() {
        return getNombre() + " - " + getTelefono();
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public String toString() {
        int totalReservas = (reservas == null) ? 0 : reservas.size();
        return "Cliente{id='" + getId() + "', nombre='" + getNombre()
                + "', email='" + getEmail() + "', reservas=" + totalReservas + "}";
    }
}
