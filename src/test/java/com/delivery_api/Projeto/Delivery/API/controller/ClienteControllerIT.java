package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.ClienteResquetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Simula usuário logado
    public void deveCriarCliente_QuandoDadosValidos() throws Exception {
        ClienteResquetDTO dto = new ClienteResquetDTO();
        dto.setNome("Cliente Integração");
        dto.setEmail("integracao@email.com");
        dto.setTelefone("11999998888");
        dto.setEndereco("Rua IT");

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Cliente Integração"))
                .andExpect(jsonPath("$.email").value("integracao@email.com"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void deveRetornarErro_QuandoNomeVazio() throws Exception {
        ClienteResquetDTO dto = new ClienteResquetDTO();
        dto.setNome(""); // Inválido
        dto.setEmail("erro@email.com");

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}