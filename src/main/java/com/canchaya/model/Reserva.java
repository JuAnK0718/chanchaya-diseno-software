package com.canchaya.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Reserva: turno de juego en una cancha para una fecha y hora.
 * Referencias directas al cliente que reserva y a la cancha reservada (@DBRef).
 */
@Document(collection = "reservas")
public class Reserva {

    @Id
    private String id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoReserva estado;
    private MetodoPago metodoPago;

    @DBRef(lazy = true)
    private Cliente cliente;

    @DBRef(lazy = true)
    private Cancha cancha;

    public Reserva() {
    }

    public Reserva(String id, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                   EstadoReserva estado, MetodoPago metodoPago, Cliente cliente, Cancha cancha) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.cancha = cancha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    @Override
    public String toString() {
        String clienteId = (cliente != null) ? cliente.getId() : null;
        String canchaId = (cancha != null) ? cancha.getId() : null;
        return "Reserva{id='" + id + "', fecha=" + fecha + ", horaInicio=" + horaInicio
                + ", horaFin=" + horaFin + ", estado=" + estado + ", metodoPago=" + metodoPago
                + ", clienteId='" + clienteId + "', canchaId='" + canchaId + "'}";
    }
}
