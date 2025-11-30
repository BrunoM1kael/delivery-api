package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicesFinalTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void testClienteServiceBuscas() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNome("Teste");
        c.setEmail("teste@email.com");
        c.setAtivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        when(clienteRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(c));
        when(clienteRepository.findByNomeContainingIgnoreCase("Teste")).thenReturn(Collections.singletonList(c));
        when(clienteRepository.findByAtivoTrue()).thenReturn(Collections.singletonList(c));

        assertTrue(clienteService.buscarPorId(1L).isPresent());
        assertTrue(clienteService.buscarPorEmail("teste@email.com").isPresent());
        assertFalse(clienteService.buscarPorNome("Teste").isEmpty());
        assertFalse(clienteService.listarAtivos().isEmpty());
    }

    @Test
    public void testClienteServiceUpdateEInativar() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setEmail("antigo@email.com");

        Cliente novo = new Cliente();
        novo.setEmail("novo@email.com");
        novo.setNome("Novo Nome");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(novo);
        when(clienteRepository.existsByEmail("novo@email.com")).thenReturn(false);

        Cliente atualizado = clienteService.atualizar(1L, novo);
        assertEquals("Novo Nome", atualizado.getNome());

        clienteService.inativar(1L);
        verify(clienteRepository, times(2)).save(any(Cliente.class)); // 1 no update, 1 no inativar
    }

    @Test
    public void testClienteServiceExceptions() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clienteService.inativar(99L));
        assertThrows(IllegalArgumentException.class, () -> clienteService.atualizar(99L, new Cliente()));
    }
}