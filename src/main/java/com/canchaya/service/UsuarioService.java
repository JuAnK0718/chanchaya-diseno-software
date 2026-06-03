package com.canchaya.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.canchaya.model.Cliente;
import com.canchaya.model.RolUsuario;
import com.canchaya.model.Usuario;
import com.canchaya.repository.UsuarioRepository;

/** Logica de negocio de usuarios: registro y autenticacion. */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /** Registra un nuevo Cliente con la contrasena hasheada en BCrypt. */
    public Usuario registrarNuevoCliente(String nombre, String email, String password) {
        if (usuarioRepository.findByEmail(email) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya esta registrado");
        }
        String hash = passwordEncoder.encode(password);
        Cliente cliente = new Cliente(UUID.randomUUID().toString(), nombre, null, email,
                hash, RolUsuario.CLIENTE, new ArrayList<>());
        return usuarioRepository.save(cliente);
    }

    /** Autentica a un usuario por email y contrasena; devuelve el usuario si las credenciales son validas. */
    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null || !passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }
        return usuario;
    }
}
