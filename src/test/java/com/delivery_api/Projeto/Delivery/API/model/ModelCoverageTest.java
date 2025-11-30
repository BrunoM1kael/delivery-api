package com.delivery_api.Projeto.Delivery.API.model;

import com.delivery_api.Projeto.Delivery.API.dto.PedidoRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.ProdutoRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ModelCoverageTest {

    @Test
    public void testEntitiesAndDTOs() {
        RestauranteRequestDTO rDto = new RestauranteRequestDTO();
        rDto.setNome("Teste");
        rDto.setTaxaEntrega(BigDecimal.TEN);
        assertEquals("Teste", rDto.getNome());
        assertNotNull(rDto.toString());

        ProdutoRequestDTO pDto = new ProdutoRequestDTO();
        pDto.setNome("Produto");
        pDto.setPreco(BigDecimal.ONE);
        assertEquals("Produto", pDto.getNome());

        PedidoRequestDTO pedDto = new PedidoRequestDTO();
        pedDto.setObservacoes("Obs");
        assertEquals("Obs", pedDto.getObservacoes());

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente");
        cliente.inativar();
        assertEquals(1L, cliente.getId());
        assertFalse(cliente.getAtivo());

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Restaurante");
        restaurante.inativar();
        assertEquals("Restaurante", restaurante.getNome());

        Produto produto = new Produto();
        produto.setRestauranteId(1L);
        produto.setDisponivel(true);
        assertTrue(produto.getDisponivel());

        assertNotNull(Role.ADMIN);
        assertNotNull(Role.CLIENTE);
        assertEquals("Pendente", StatusPedido.PENDENTE.getDescricao());
        assertEquals(StatusPedido.ENTREGUE, StatusPedido.valueOf("ENTREGUE"));
    }
}