package com.delivery_api.Projeto.Delivery.API.config;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import com.delivery_api.Projeto.Delivery.API.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ClienteRepository clienteRepository,
                                   RestauranteRepository restauranteRepository,
                                   ProdutoRepository produtoRepository,
                                   PedidoRepository pedidoRepository,
                                   UsuarioRepository usuarioRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            pedidoRepository.deleteAll();
            produtoRepository.deleteAll();
            restauranteRepository.deleteAll();
            clienteRepository.deleteAll();
            usuarioRepository.deleteAll();

            Usuario admin = Usuario.builder()
                    .nome("Admin Sistema")
                    .email("admin@delivery.com")
                    .senha(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();

            Usuario clienteUser = Usuario.builder()
                    .nome("João Cliente")
                    .email("joao@email.com")
                    .senha(passwordEncoder.encode("123456"))
                    .role(Role.CLIENTE)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();

            usuarioRepository.saveAll(Arrays.asList(admin, clienteUser));
            System.out.println("--- Usuários de teste criados! (Senha: 123456) ---");

            Cliente c1 = new Cliente(null, "João Silva", "joao@email.com", "11999999999", "Rua A, 123", LocalDateTime.now(), true);
            clienteRepository.save(c1);

            Restaurante r1 = new Restaurante(null, "Pizza Palace", "Italiana", "Rua das Pizzas", "1133334444", new BigDecimal("5.00"), new BigDecimal("4.5"), true);
            restauranteRepository.save(r1);
        };
    }
}