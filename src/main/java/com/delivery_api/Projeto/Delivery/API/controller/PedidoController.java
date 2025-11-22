package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.PedidoRequestDTO;
import com.delivery_api.Projeto.Delivery.API.dto.PedidoResponseDTO;
import com.delivery_api.Projeto.Delivery.API.enums.StatusPedido;
import com.delivery_api.Projeto.Delivery.API.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO novoPedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PutMapping("/{pedidoId}/status/{status}")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long pedidoId,
                                                             @PathVariable StatusPedido status) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(pedidoId, status));
    }
}