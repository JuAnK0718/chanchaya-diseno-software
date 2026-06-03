package com.canchaya.service;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.canchaya.model.AdminEstablecimiento;
import com.canchaya.model.Establecimiento;
import com.canchaya.repository.EstablecimientoRepository;

/** Logica de negocio de establecimientos. */
@Service
public class EstablecimientoService {

    private final EstablecimientoRepository establecimientoRepository;

    public EstablecimientoService(EstablecimientoRepository establecimientoRepository) {
        this.establecimientoRepository = establecimientoRepository;
    }

    /**
     * Registra un establecimiento. El administrador se enlaza por su id:
     * se crea una referencia (@DBRef) al usuario admin sin cargar todo el objeto.
     */
    public Establecimiento registrarEstablecimiento(String nombre, String direccion, String adminId) {
        AdminEstablecimiento adminRef = new AdminEstablecimiento();
        adminRef.setId(adminId);
        Establecimiento establecimiento = new Establecimiento(
                UUID.randomUUID().toString(), nombre, direccion, adminRef);
        return establecimientoRepository.save(establecimiento);
    }

    /** Busca un establecimiento por su id. */
    public Establecimiento buscarEstablecimiento(String id) {
        return establecimientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Establecimiento no encontrado"));
    }
}
