package com.canchaya.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.canchaya.model.Cancha;

/** Acceso a la coleccion "canchas". */
public interface CanchaRepository extends MongoRepository<Cancha, String> {

    /** Lista las canchas de un establecimiento (id del @DBRef establecimiento). */
    @Query("{ 'establecimiento.$id' : ?0 }")
    List<Cancha> findByEstablecimientoId(String establecimientoId);
}
