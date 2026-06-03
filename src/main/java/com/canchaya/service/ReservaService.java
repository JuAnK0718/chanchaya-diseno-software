package com.canchaya.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.canchaya.model.Cancha;
import com.canchaya.model.Cliente;
import com.canchaya.model.EstadoReserva;
import com.canchaya.model.MetodoPago;
import com.canchaya.model.Reserva;
import com.canchaya.repository.ReservaRepository;

/**
 * Logica de negocio de reservas.
 * Depende de CanchaService para verificar disponibilidad antes de confirmar.
 */
@Service
public class ReservaService {

    /** Duracion fija de la franja (minutos) para calcular la hora de fin. */
    private static final int DURACION_FRANJA_MIN = 60;

    private final CanchaService canchaService;
    private final ReservaRepository reservaRepository;

    public ReservaService(CanchaService canchaService, ReservaRepository reservaRepository) {
        this.canchaService = canchaService;
        this.reservaRepository = reservaRepository;
    }

    /**
     * Agenda una reserva instantanea en estado CONFIRMADA.
     * 1) La cancha debe estar disponible a esa hora.
     * 2) No puede haber otra reserva CONFIRMADA en la misma cancha, fecha y hora.
     */
    public Reserva agendarReserva(String clienteId, String canchaId, LocalDate fecha, LocalTime hora) {
        if (!canchaService.verificarDisponibilidadHoraria(canchaId, hora)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La cancha no esta disponible a esa hora");
        }
        boolean ocupada = reservaRepository.findByCanchaIdAndFecha(canchaId, fecha).stream()
                .anyMatch(r -> r.getEstado() == EstadoReserva.CONFIRMADA
                        && r.getHoraInicio().equals(hora));
        if (ocupada) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La franja ya esta reservada");
        }

        Cliente clienteRef = new Cliente();
        clienteRef.setId(clienteId);
        Cancha canchaRef = new Cancha();
        canchaRef.setId(canchaId);

        Reserva reserva = new Reserva(
                UUID.randomUUID().toString(), fecha, hora, hora.plusMinutes(DURACION_FRANJA_MIN),
                EstadoReserva.CONFIRMADA, MetodoPago.EFECTIVO, clienteRef, canchaRef);
        return reservaRepository.save(reserva);
    }

    /** Cancela una reserva existente (la deja en estado CANCELADA_USUARIO). */
    public void procesarCancelacion(String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reserva no encontrada"));
        reserva.setEstado(EstadoReserva.CANCELADA_USUARIO);
        reservaRepository.save(reserva);
    }

    /** Lista el historial de reservas de un cliente. */
    public List<Reserva> listarPorCliente(String clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }
}
