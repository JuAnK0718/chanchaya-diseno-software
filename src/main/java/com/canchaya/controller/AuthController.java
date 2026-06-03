package com.canchaya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canchaya.model.Usuario;
import com.canchaya.service.UsuarioService;

/** Endpoints de registro y autenticacion. */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarCliente(@RequestParam String nombre,
                                                    @RequestParam String email,
                                                    @RequestParam String password,
                                                    @RequestParam(required = false) String telefono) {
        return ResponseEntity.ok(usuarioService.registrarNuevoCliente(nombre, email, password, telefono));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email,
                                         @RequestParam String password) {
        return ResponseEntity.ok(usuarioService.autenticarUsuario(email, password));
    }
}
