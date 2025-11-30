package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.RestauranteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteService restauranteService;

    @Test
    public void deveCadastrarRestaurante() {
        RestauranteRequestDTO dto = new RestauranteRequestDTO();
        dto.setNome("Restaurante Teste");
        dto.setTaxaEntrega(BigDecimal.valueOf(10));
        dto.setCep("12345678");
        dto.setTelefone("11999999999");

        Restaurante restauranteSalvo = new Restaurante();
        restauranteSalvo.setId(1L);
        restauranteSalvo.setNome("Restaurante Teste");
        restauranteSalvo.setAtivo(true);

        when(restauranteRepository.findByNome(dto.getNome())).thenReturn(Optional.empty());
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restauranteSalvo);

        RestauranteResponseDTO result = restauranteService.cadastrar(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(restauranteRepository).save(any(Restaurante.class));
    }

    @Test
    public void deveListarAtivos() {
        when(restauranteRepository.findByAtivoTrue()).thenReturn(List.of(new Restaurante(), new Restaurante()));
        List<RestauranteResponseDTO> result = restauranteService.listarAtivos();
        assertEquals(2, result.size());
    }
}