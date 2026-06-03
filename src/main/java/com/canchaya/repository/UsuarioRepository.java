package com.canchaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.canchaya.model.Usuario;

/** Acceso a la coleccion "usuarios" (Cliente y AdminEstablecimiento). */
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    /** Busca un usuario por su email (consulta derivada del nombre del metodo). */
    Usuario findByEmail(String email);
}
