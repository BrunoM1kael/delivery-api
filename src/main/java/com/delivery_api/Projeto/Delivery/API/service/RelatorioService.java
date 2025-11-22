package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.RelatorioVendasDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import com.delivery_api.Projeto.Delivery.API.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<RelatorioVendasDTO> vendasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<Pedido> pedidos = pedidoRepository.findByDataPedidoBetween(inicio, fim);
        List<RelatorioVendasDTO> relatorio = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            String nomeCliente = clienteRepository.findById(pedido.getClienteId())
                    .map(c -> c.getNome())
                    .orElse("Cliente Desconhecido");

            relatorio.add(new RelatorioVendasDTO(
                    pedido.getDataPedido(),
                    pedido.getNumeroPedido(),
                    nomeCliente,
                    pedido.getValorTotal()
            ));
        }
        return relatorio;
    }

    public BigDecimal calcularFaturamentoTotal() {
        return pedidoRepository.findAll().stream()
                .map(Pedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}