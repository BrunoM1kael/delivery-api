package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.ProdutoResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.RestauranteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import com.delivery_api.Projeto.Delivery.API.repository.PedidoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.ProdutoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceCoverageTest {

    @Mock private RestauranteRepository restauranteRepository;
    @Mock private ProdutoRepository produtoRepository;
    @Mock private PedidoRepository pedidoRepository;
    @Mock private ClienteRepository clienteRepository;

    @InjectMocks private RestauranteService restauranteService;
    @InjectMocks private ProdutoService produtoService;
    @InjectMocks private PedidoService pedidoService;

    @Test
    public void testRestauranteServiceFull() {
        Restaurante r = new Restaurante(1L, "R1", "Cat", "End", "Tel", BigDecimal.ONE, BigDecimal.ZERO, true);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(r));
        when(restauranteRepository.existsById(1L)).thenReturn(true);
        when(restauranteRepository.findByCategoria("Cat")).thenReturn(Collections.singletonList(r));

        RestauranteResponseDTO res = restauranteService.buscarPorId(1L);
        assertNotNull(res);

        restauranteService.deletar(1L);
        verify(restauranteRepository).deleteById(1L);

        List<RestauranteResponseDTO> lista = restauranteService.buscarPorCategoria("Cat");
        assertFalse(lista.isEmpty());

        restauranteService.inativar(1L);
        assertFalse(r.getAtivo());
    }

    @Test
    public void testProdutoServiceFull() {
        Produto p = new Produto(1L, "P1", "Desc", BigDecimal.TEN, "Cat", true, 1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(produtoRepository.existsById(1L)).thenReturn(true);

        ProdutoResponseDTO res = produtoService.buscarPorId(1L);
        assertNotNull(res);

        produtoService.inativar(1L);
        assertFalse(p.getDisponivel());

        produtoService.excluir(1L);
        verify(produtoRepository).deleteById(1L);
    }

    @Test
    public void testPedidoServiceFull() {
        Pedido p = new Pedido();
        p.setId(1L);
        p.setStatus(StatusPedido.PENDENTE.name());

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(clienteRepository.existsById(1L)).thenReturn(true);
        when(pedidoRepository.findByClienteIdOrderByDataPedidoDesc(1L)).thenReturn(Collections.singletonList(p));
        when(pedidoRepository.save(any())).thenReturn(p);

        assertNotNull(pedidoService.buscarPorId(1L));
        assertFalse(pedidoService.listarPorCliente(1L).isEmpty());

        pedidoService.atualizarStatus(1L, StatusPedido.PREPARANDO);
        assertEquals(StatusPedido.PREPARANDO.name(), p.getStatus());
    }
}