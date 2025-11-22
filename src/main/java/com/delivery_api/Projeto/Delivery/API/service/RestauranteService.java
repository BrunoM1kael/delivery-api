package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.RestauranteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public RestauranteResponseDTO cadastrar(RestauranteRequestDTO dto) {
        // Mudança 1: Usar ConflictException (Roteiro 6)
        if (restauranteRepository.findByNome(dto.getNome()).isPresent()) {
            throw new com.delivery_api.Projeto.Delivery.API.exceptions.ConflictException("Restaurante já cadastrado: " + dto.getNome());
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());

        // Mudança 2: Usar o CEP que validamos
        restaurante.setEndereco(dto.getEndereco() + " - CEP: " + dto.getCep());

        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());
        restaurante.setAtivo(true);
        restaurante.setAvaliacao(java.math.BigDecimal.ZERO);

        restaurante = restauranteRepository.save(restaurante);
        return new RestauranteResponseDTO(restaurante);
    }

    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> listarAtivos() {
        return restauranteRepository.findByAtivoTrue().stream()
                .map(RestauranteResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RestauranteResponseDTO buscarPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));
        return new RestauranteResponseDTO(restaurante);
    }

    public RestauranteResponseDTO atualizar(Long id, RestauranteRequestDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));

        restaurante.setNome(dto.getNome());
        restaurante.setCategoria(dto.getCategoria());
        restaurante.setEndereco(dto.getEndereco());
        restaurante.setTelefone(dto.getTelefone());
        restaurante.setTaxaEntrega(dto.getTaxaEntrega());

        restaurante = restauranteRepository.save(restaurante);
        return new RestauranteResponseDTO(restaurante);
    }

    public void inativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + id));
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);
    }

    public void deletar(Long id) {
        if (!restauranteRepository.existsById(id)) {
            throw new IllegalArgumentException("Restaurante não encontrado: " + id);
        }
        restauranteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RestauranteResponseDTO> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria).stream()
                .map(RestauranteResponseDTO::new)
                .toList();
    }
}