package com.canchaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.canchaya.model.Establecimiento;

/** Acceso a la coleccion "establecimientos". */
public interface EstablecimientoRepository extends MongoRepository<Establecimiento, String> {

    /** Busca el establecimiento administrado por un usuario (id del @DBRef administrador). */
    @Query("{ 'administrador.$id' : ?0 }")
    Establecimiento findByAdminId(String adminId);
}
