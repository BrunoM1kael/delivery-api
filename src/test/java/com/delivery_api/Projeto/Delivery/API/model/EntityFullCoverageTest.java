package com.delivery_api.Projeto.Delivery.API.model;

import com.delivery_api.Projeto.Delivery.API.entity.*;
import com.delivery_api.Projeto.Delivery.API.enums.Role;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EntityFullCoverageTest {

    @Test
    public void testClienteEntity() {
        Cliente c1 = new Cliente(1L, "Nome", "email", "tel", "end", LocalDateTime.now(), true);
        Cliente c2 = new Cliente();
        c2.setId(1L);
        c2.setNome("Nome");
        c2.setEmail("email");
        c2.setTelefone("tel");
        c2.setEndereco("end");
        c2.setDataCadastro(c1.getDataCadastro());
        c2.setAtivo(true);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotNull(c1.toString());

        assertNotNull(c1.getId());
        assertNotNull(c1.getEmail());
    }

    @Test
    public void testRestauranteEntity() {
        Restaurante r1 = new Restaurante(1L, "R1", "Cat", "End", "Tel", BigDecimal.ONE, BigDecimal.ZERO, true);
        Restaurante r2 = new Restaurante();
        r2.setId(1L);
        r2.setNome("R1");
        r2.setCategoria("Cat");
        r2.setEndereco("End");
        r2.setTelefone("Tel");
        r2.setTaxaEntrega(BigDecimal.ONE);
        r2.setAvaliacao(BigDecimal.ZERO);
        r2.setAtivo(true);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotNull(r1.toString());
    }

    @Test
    public void testUsuarioEntity() {
        Usuario u1 = Usuario.builder()
                .id(1L)
                .email("email")
                .senha("123")
                .role(Role.ADMIN)
                .ativo(true)
                .build();

        Usuario u2 = new Usuario(1L, "email", "123", "Nome", Role.ADMIN, true, LocalDateTime.now(), null);
        u2.setDataCriacao(null);
        u1.setNome("Nome");


        assertNotNull(u1.getAuthorities());
        assertTrue(u1.isAccountNonExpired());
        assertTrue(u1.isAccountNonLocked());
        assertTrue(u1.isCredentialsNonExpired());
        assertTrue(u1.isEnabled());

        assertNotNull(u1.toString());
    }
}