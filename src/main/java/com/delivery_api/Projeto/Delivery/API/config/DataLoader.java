package com.delivery_api.Projeto.Delivery.API.config;

import com.delivery_api.Projeto.Delivery.API.entity.*;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import com.delivery_api.Projeto.Delivery.API.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository,
                                   RestauranteRepository restauranteRepository,
                                   ProdutoRepository produtoRepository,
                                   PedidoRepository pedidoRepository) {
        return args -> {
            Cliente c1 = new Cliente(null, "João Silva", "joao@email.com", "11999999999", "Rua A, 123", LocalDateTime.now(), true);
            Cliente c2 = new Cliente(null, "Maria Souza", "maria@email.com", "11888888888", "Rua B, 456", LocalDateTime.now(), true);
            clienteRepository.saveAll(Arrays.asList(c1, c2));

            Restaurante r1 = new Restaurante(null, "Pizza Palace", "Italiana", "Rua das Pizzas, 1", "1133334444", new BigDecimal("5.00"), BigDecimal.ZERO, true);
            Restaurante r2 = new Restaurante(null, "Burger Kingo", "Lanches", "Av. dos Burgers, 100", "1155556666", new BigDecimal("7.50"), BigDecimal.ZERO, true);
            restauranteRepository.saveAll(Arrays.asList(r1, r2));

            Produto p1 = new Produto(null, "Pizza Margherita", "Molho, mussarela e manjericão", new BigDecimal("45.00"), "Pizza", true, r1.getId());
            Produto p2 = new Produto(null, "Pizza Calabresa", "Molho, mussarela e calabresa", new BigDecimal("40.00"), "Pizza", true, r1.getId());
            Produto p3 = new Produto(null, "X-Bacon", "Pão, carne, queijo e bacon", new BigDecimal("25.00"), "Lanche", true, r2.getId());
            produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

            Pedido ped1 = new Pedido(null, "PED-001", LocalDateTime.now().minusHours(2), StatusPedido.ENTREGUE.name(), new BigDecimal("45.00"), "Sem cebola", c1.getId(), r1, "1x Pizza Margherita");
            Pedido ped2 = new Pedido(null, "PED-002", LocalDateTime.now().minusHours(1), StatusPedido.PENDENTE.name(), new BigDecimal("25.00"), "", c2.getId(), r2, "1x X-Bacon");

            pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

            System.out.println("--- Carga de dados inicial realizada com sucesso! ---");
        };
    }
}