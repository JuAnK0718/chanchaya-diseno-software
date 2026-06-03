package com.canchaya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canchaya.model.Establecimiento;
import com.canchaya.service.EstablecimientoService;

/** Endpoints de establecimientos. */
@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    private final EstablecimientoService establecimientoService;

    public EstablecimientoController(EstablecimientoService establecimientoService) {
        this.establecimientoService = establecimientoService;
    }

    @PostMapping
    public ResponseEntity<Establecimiento> crearEstablecimiento(@RequestParam String nombre,
                                                                @RequestParam String direccion,
                                                                @RequestParam String adminId) {
        return ResponseEntity.ok(
                establecimientoService.registrarEstablecimiento(nombre, direccion, adminId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(establecimientoService.buscarEstablecimiento(id));
    }
}
