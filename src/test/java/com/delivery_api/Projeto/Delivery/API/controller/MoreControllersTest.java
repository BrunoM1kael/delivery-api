package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.ClienteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.dto.ClienteResquetDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // <--- DESLIGA FILTROS DE SEGURANÇA (Resolve o 403)
@ActiveProfiles("test")
public class MoreControllersTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ClienteService clienteService;

    @Test
    public void testClienteController() throws Exception {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNome("Cliente");
        ClienteResponseDTO response = new ClienteResponseDTO(c);

        when(clienteService.listarAtivos()).thenReturn(Collections.singletonList(c));
        when(clienteService.buscarPorId(anyLong())).thenReturn(Optional.of(c));
        when(clienteService.cadastrar(any())).thenReturn(response);
        when(clienteService.buscarPorEmail(any())).thenReturn(Optional.of(c));
        when(clienteService.buscarPorNome(any())).thenReturn(Collections.singletonList(c));

        mockMvc.perform(get("/clientes")).andExpect(status().isOk());
        mockMvc.perform(get("/clientes/1")).andExpect(status().isOk());
        mockMvc.perform(get("/clientes/email/teste@email.com")).andExpect(status().isOk());
        mockMvc.perform(get("/clientes/buscar?nome=Cliente")).andExpect(status().isOk());
        mockMvc.perform(delete("/clientes/1")).andExpect(status().isOk());

        ClienteResquetDTO dto = new ClienteResquetDTO();
        dto.setNome("Novo");
        dto.setEmail("novo@email.com");
        dto.setTelefone("11999998888");
        dto.setEndereco("Rua");

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isOk());
    }

    @Test
    public void testHealthController() throws Exception {
        mockMvc.perform(get("/health")).andExpect(status().isOk());
        mockMvc.perform(get("/info")).andExpect(status().isOk());
    }
}