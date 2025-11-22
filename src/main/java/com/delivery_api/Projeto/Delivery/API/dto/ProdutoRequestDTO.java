package com.delivery_api.Projeto.Delivery.API.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProdutoRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @DecimalMax(value = "500.00", message = "O preço não pode exceder R$ 500,00")
    private BigDecimal preco;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotNull(message = "Informe se está disponível")
    private Boolean disponivel;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restauranteId;
}