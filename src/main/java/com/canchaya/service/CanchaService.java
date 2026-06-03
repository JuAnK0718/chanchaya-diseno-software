package com.canchaya.service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.canchaya.model.Cancha;
import com.canchaya.model.Deporte;
import com.canchaya.model.Establecimiento;
import com.canchaya.model.EstadoCancha;
import com.canchaya.repository.CanchaRepository;

/** Logica de negocio de canchas. */
@Service
public class CanchaService {

    /** Horario y duracion por defecto al crear una cancha (la operacion del diagrama no los recibe). */
    private static final LocalTime APERTURA_POR_DEFECTO = LocalTime.of(8, 0);
    private static final LocalTime CIERRE_POR_DEFECTO = LocalTime.of(22, 0);
    private static final int DURACION_POR_DEFECTO_MIN = 60;

    private final CanchaRepository canchaRepository;

    public CanchaService(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    /**
     * Registra una cancha en estado DISPONIBLE. El establecimiento se enlaza por su id (@DBRef).
     */
    public Cancha registrarCancha(String nombre, String deporte, String establecimientoId) {
        Establecimiento estabRef = new Establecimiento();
        estabRef.setId(establecimientoId);
        Cancha cancha = new Cancha(
                UUID.randomUUID().toString(), nombre, Deporte.valueOf(deporte),
                EstadoCancha.DISPONIBLE, APERTURA_POR_DEFECTO, CIERRE_POR_DEFECTO,
                DURACION_POR_DEFECTO_MIN, estabRef);
        return canchaRepository.save(cancha);
    }

    /**
     * Indica si la cancha admite una reserva a esa hora:
     * debe estar DISPONIBLE y la hora debe caer dentro de su horario de atencion.
     */
    public boolean verificarDisponibilidadHoraria(String canchaId, LocalTime hora) {
        Cancha cancha = canchaRepository.findById(canchaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cancha no encontrada"));
        if (cancha.getEstado() != EstadoCancha.DISPONIBLE) {
            return false;
        }
        return !hora.isBefore(cancha.getHoraApertura()) && hora.isBefore(cancha.getHoraCierre());
    }

    /** Lista las canchas de un establecimiento. */
    public List<Cancha> listarCanchasPorEstablecimiento(String establecimientoId) {
        return canchaRepository.findByEstablecimientoId(establecimientoId);
    }
}
