package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.LoginRequest;
import com.delivery_api.Projeto.Delivery.API.dto.LoginResponse;
import com.delivery_api.Projeto.Delivery.API.dto.RegisterRequest;
import com.delivery_api.Projeto.Delivery.API.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Retorna dados do usuário logado")
    public ResponseEntity<LoginResponse> getCurrentUser(
            @org.springframework.security.core.annotation.AuthenticationPrincipal
            com.delivery_api.Projeto.Delivery.API.entity.Usuario usuarioLogado) {

        return ResponseEntity.ok(LoginResponse.builder()
                .token(null)
                .tipo("Bearer")
                .nomeUsuario(usuarioLogado.getNome())
                .email(usuarioLogado.getEmail())
                .role(usuarioLogado.getRole().name())
                .build());
    }
}