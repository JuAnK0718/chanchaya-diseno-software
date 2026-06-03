package com.canchaya.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.canchaya.model.Reserva;

/** Acceso a la coleccion "reservas". */
public interface ReservaRepository extends MongoRepository<Reserva, String> {

    /** Lista las reservas de un cliente (id del @DBRef cliente). */
    @Query("{ 'cliente.$id' : ?0 }")
    List<Reserva> findByClienteId(String clienteId);

    /** Lista las reservas de una cancha en una fecha (id del @DBRef cancha + fecha). */
    @Query("{ 'cancha.$id' : ?0, 'fecha' : ?1 }")
    List<Reserva> findByCanchaIdAndFecha(String canchaId, LocalDate fecha);
}
