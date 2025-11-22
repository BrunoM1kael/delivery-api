package com.delivery_api.Projeto.Delivery.API.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Não mostra campos nulos no JSON
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path; // O roteiro exige isso
    private Map<String, String> details; // Detalhes para erros de validação
}