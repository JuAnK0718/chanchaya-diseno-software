package com.canchaya.model;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Cancha: espacio deportivo reservable dentro de un establecimiento.
 * Referencia directa a su establecimiento (@DBRef).
 */
@Document(collection = "canchas")
public class Cancha {

    @Id
    private String id;
    private String nombre;
    private Deporte deporte;
    private EstadoCancha estado;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private int duracionAlquilerMinutos;

    @DBRef(lazy = true)
    private Establecimiento establecimiento;

    public Cancha() {
    }

    public Cancha(String id, String nombre, Deporte deporte, EstadoCancha estado,
                  LocalTime horaApertura, LocalTime horaCierre, int duracionAlquilerMinutos,
                  Establecimiento establecimiento) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.estado = estado;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.duracionAlquilerMinutos = duracionAlquilerMinutos;
        this.establecimiento = establecimiento;
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

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public EstadoCancha getEstado() {
        return estado;
    }

    public void setEstado(EstadoCancha estado) {
        this.estado = estado;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public int getDuracionAlquilerMinutos() {
        return duracionAlquilerMinutos;
    }

    public void setDuracionAlquilerMinutos(int duracionAlquilerMinutos) {
        this.duracionAlquilerMinutos = duracionAlquilerMinutos;
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
        return "Cancha{id='" + id + "', nombre='" + nombre + "', deporte=" + deporte
                + ", estado=" + estado + ", horaApertura=" + horaApertura
                + ", horaCierre=" + horaCierre + ", duracionAlquilerMinutos=" + duracionAlquilerMinutos
                + ", establecimientoId='" + estabId + "'}";
    }
}
