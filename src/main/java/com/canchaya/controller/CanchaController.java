package com.canchaya.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canchaya.model.Cancha;
import com.canchaya.service.CanchaService;

/** Endpoints de canchas. */
@RestController
@RequestMapping("/canchas")
public class CanchaController {

    private final CanchaService canchaService;

    public CanchaController(CanchaService canchaService) {
        this.canchaService = canchaService;
    }

    @PostMapping
    public ResponseEntity<Cancha> agregarCancha(@RequestParam String nombre,
                                                @RequestParam String deporte,
                                                @RequestParam String establecimientoId) {
        return ResponseEntity.ok(
                canchaService.registrarCancha(nombre, deporte, establecimientoId));
    }

    @GetMapping
    public ResponseEntity<List<Cancha>> listarCanchasPorEstablecimiento(
            @RequestParam String establecimientoId) {
        return ResponseEntity.ok(
                canchaService.listarCanchasPorEstablecimiento(establecimientoId));
    }
}
