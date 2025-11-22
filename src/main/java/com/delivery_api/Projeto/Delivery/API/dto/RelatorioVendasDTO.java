package com.delivery_api.Projeto.Delivery.API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RelatorioVendasDTO {
    private LocalDateTime data;
    private String numeroPedido;
    private String nomeCliente;
    private BigDecimal valorTotal;
}