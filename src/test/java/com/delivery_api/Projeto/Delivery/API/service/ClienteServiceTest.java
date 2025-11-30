package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.ClienteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.ClienteResquetDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.exceptions.BusinessException;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    public void deveCadastrarClienteComSucesso() {
        ClienteResquetDTO dto = new ClienteResquetDTO();
        dto.setNome("Teste");
        dto.setEmail("teste@email.com");
        dto.setTelefone("11999998888");
        dto.setEndereco("Rua Teste");

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setId(1L);
        clienteSalvo.setNome(dto.getNome());
        clienteSalvo.setEmail(dto.getEmail());
        clienteSalvo.setAtivo(true);

        when(clienteRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        ClienteResponseDTO resultado = clienteService.cadastrar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Teste", resultado.getNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void naoDeveCadastrarClienteComEmailDuplicado() {
        ClienteResquetDTO dto = new ClienteResquetDTO();
        dto.setEmail("duplicado@email.com");

        when(clienteRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteService.cadastrar(dto));
        verify(clienteRepository, never()).save(any());
    }
}