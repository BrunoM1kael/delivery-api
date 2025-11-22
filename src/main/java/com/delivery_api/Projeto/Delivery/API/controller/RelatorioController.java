package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.RelatorioVendasDTO;
import com.delivery_api.Projeto.Delivery.API.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios", description = "Endpoints para extração de dados e métricas")
@CrossOrigin(origins = "*")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/vendas")
    @Operation(summary = "Relatório de vendas por período",
            description = "Retorna os pedidos realizados dentro de um intervalo de datas")
    public ResponseEntity<List<RelatorioVendasDTO>> gerarRelatorioVendas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        return ResponseEntity.ok(relatorioService.vendasPorPeriodo(inicio, fim));
    }

    @GetMapping("/faturamento-total")
    @Operation(summary = "Faturamento total", description = "Calcula a soma de todos os pedidos já realizados")
    public ResponseEntity<BigDecimal> faturamentoTotal() {
        return ResponseEntity.ok(relatorioService.calcularFaturamentoTotal());
    }
}