package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.RestauranteRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.RestauranteResponseDTO;
import com.delivery_api.Projeto.Delivery.API.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
@Tag(name = "Restaurantes", description = "Gerenciamento de restaurantes parceiros")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar novo restaurante", description = "Cria um restaurante com status ativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou violação de validação"),
            @ApiResponse(responseCode = "409", description = "Restaurante já cadastrado")
    })
    public ResponseEntity<RestauranteResponseDTO> cadastrar(@Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO novoRestaurante = restauranteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes ativos", description = "Retorna a lista de todos os restaurantes disponíveis")
    public ResponseEntity<List<RestauranteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(restauranteService.listarAtivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar dados do restaurante")
    public ResponseEntity<RestauranteResponseDTO> atualizar(@PathVariable Long id,
                                                            @Valid @RequestBody RestauranteRequestDTO dto) {
        return ResponseEntity.ok(restauranteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover restaurante", description = "Exclui logicamente ou fisicamente o restaurante")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        restauranteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar restaurante", description = "Muda o status do restaurante para inativo")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar por categoria")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(restauranteService.buscarPorCategoria(categoria));
    }
}