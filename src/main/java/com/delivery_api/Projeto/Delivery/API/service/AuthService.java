package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.LoginRequest;
import com.delivery_api.Projeto.Delivery.API.dto.LoginResponse;
import com.delivery_api.Projeto.Delivery.API.dto.RegisterRequest;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository;
import com.delivery_api.Projeto.Delivery.API.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole() != null ? request.getRole() : Role.CLIENTE)
                .restauranteId(request.getRestauranteId())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario);

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .nomeUsuario(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String token = jwtUtil.generateToken(usuario);

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .nomeUsuario(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .build();
    }
}