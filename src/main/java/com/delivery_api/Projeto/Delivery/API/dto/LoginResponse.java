package com.delivery_api.Projeto.Delivery.API.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo; // "Bearer"
    private String nomeUsuario;
    private String email;
    private String role;
}