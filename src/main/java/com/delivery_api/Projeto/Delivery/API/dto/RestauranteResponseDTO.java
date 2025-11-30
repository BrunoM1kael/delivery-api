package com.delivery_api.Projeto.Delivery.API.dto;

import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class RestauranteResponseDTO {
    private Long id;
    private String nome;
    private String categoria;
    private BigDecimal taxaEntrega;
    private Boolean ativo;

    public RestauranteResponseDTO(Restaurante restaurante) {
        this.id = restaurante.getId();
        this.nome = restaurante.getNome();
        this.categoria = restaurante.getCategoria();
        this.taxaEntrega = restaurante.getTaxaEntrega();
        this.ativo = restaurante.getAtivo();
    }
}