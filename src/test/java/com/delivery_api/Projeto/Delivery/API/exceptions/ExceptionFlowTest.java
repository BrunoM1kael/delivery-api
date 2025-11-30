package com.delivery_api.Projeto.Delivery.API.exceptions;

import com.delivery_api.Projeto.Delivery.API.dto.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.service.RestauranteService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExceptionFlowTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private RestauranteService restauranteService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testConflictException() throws Exception {
        when(restauranteService.cadastrar(any())).thenThrow(new ConflictException("Já existe"));

        RestauranteRequestDTO dto = new RestauranteRequestDTO();
        dto.setNome("Teste");
        dto.setCategoria("Italiana");
        dto.setCep("12345678");
        dto.setTelefone("11999998888");
        dto.setTempoEntrega(30);
        dto.setEndereco("Rua");

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict()); // Espera 409
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testValidationException() throws Exception {
        RestauranteRequestDTO dto = new RestauranteRequestDTO();

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()); // Espera 400
    }
}