package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.*;
import com.delivery_api.Projeto.Delivery.API.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ControllerCoverageTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestauranteService restauranteService;
    @MockBean
    private ProdutoService produtoService;
    @MockBean
    private PedidoService pedidoService;
    @MockBean
    private AuthService authService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testRestauranteController() throws Exception {
        RestauranteRequestDTO dto = new RestauranteRequestDTO();
        dto.setNome("Restaurante");
        dto.setCep("12345678");
        dto.setTelefone("11999998888");
        dto.setTaxaEntrega(BigDecimal.ONE);
        dto.setCategoria("Italiana");
        dto.setEndereco("Rua A");
        dto.setTempoEntrega(30);

        when(restauranteService.cadastrar(any())).thenReturn(new RestauranteResponseDTO());
        when(restauranteService.listarAtivos()).thenReturn(Collections.emptyList());
        when(restauranteService.buscarPorId(anyLong())).thenReturn(new RestauranteResponseDTO());

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/restaurantes")).andExpect(status().isOk());
        mockMvc.perform(get("/restaurantes/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/restaurantes/1")).andExpect(status().isNoContent());
        mockMvc.perform(put("/restaurantes/1/inativar")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testProdutoController() throws Exception {
        ProdutoRequestDTO dto = new ProdutoRequestDTO();
        dto.setNome("Prod");
        dto.setPreco(BigDecimal.TEN);
        dto.setCategoria("Cat");
        dto.setDescricao("Descricao Longa");
        dto.setDisponivel(true);
        dto.setRestauranteId(1L);

        when(produtoService.cadastrar(any())).thenReturn(new ProdutoResponseDTO());

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/produtos")).andExpect(status().isOk());
        mockMvc.perform(get("/produtos/1")).andExpect(status().isOk());
        mockMvc.perform(delete("/produtos/1")).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"CLIENTE"})
    public void testPedidoController() throws Exception {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setClienteId(1L);
        dto.setRestauranteId(1L);
        dto.setValorTotal(BigDecimal.TEN);
        dto.setItens("Item 1");

        when(pedidoService.criarPedido(any())).thenReturn(new PedidoResponseDTO());

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/pedidos/1")).andExpect(status().isOk());
    }

    @Test
    public void testAuthController() throws Exception {
        LoginRequest login = new LoginRequest("email@teste.com", "123456");
        when(authService.login(any())).thenReturn(new LoginResponse());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk());
    }
}