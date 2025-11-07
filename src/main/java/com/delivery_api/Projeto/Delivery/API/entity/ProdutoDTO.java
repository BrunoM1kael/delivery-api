package com.delivery_api.Projeto.Delivery.API.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private Boolean disponivel;

}