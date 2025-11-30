package com.delivery_api.Projeto.Delivery.API.dto;

import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private String numeroPedido;
    private LocalDateTime dataPedido;
    private String status;
    private BigDecimal valorTotal;
    private String observacoes;
    private Long clienteId;
    private Long restauranteId;
    private String itens;
    private String nomeRestaurante;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.numeroPedido = pedido.getNumeroPedido();
        this.dataPedido = pedido.getDataPedido();
        this.status = pedido.getStatus();
        this.valorTotal = pedido.getValorTotal();
        this.observacoes = pedido.getObservacoes();
        this.clienteId = pedido.getClienteId();
        if (pedido.getRestaurante() != null) {
            this.restauranteId = pedido.getRestaurante().getId();
            this.nomeRestaurante = pedido.getRestaurante().getNome();
        }
        this.itens = pedido.getItens();
    }
}