package com.delivery_api.Projeto.Delivery.API.dto;

import com.delivery_api.Projeto.Delivery.API.validation.ValidCEP;
import com.delivery_api.Projeto.Delivery.API.validation.ValidCategoria;
import com.delivery_api.Projeto.Delivery.API.validation.ValidTelefone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Dados para cadastro ou atualização de restaurante")
public class RestauranteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Schema(description = "Nome comercial do restaurante", example = "Pizzaria do Luigi", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    @ValidCategoria
    @Schema(description = "Categoria culinária (Italiana, Brasileira, Japonesa, Lanches)", example = "Italiana")
    private String categoria;

    @NotBlank(message = "O endereço é obrigatório")
    @Schema(description = "Logradouro completo", example = "Rua das Massas, 123")
    private String endereco;

    @NotBlank(message = "O CEP é obrigatório")
    @ValidCEP
    @Schema(description = "CEP sem formatação ou com traço", example = "12345678")
    private String cep;

    @NotBlank(message = "O telefone é obrigatório")
    @ValidTelefone
    @Schema(description = "Telefone de contato", example = "11999998888")
    private String telefone;

    @DecimalMin(value = "0.0", message = "A taxa de entrega deve ser positiva")
    @Schema(description = "Valor cobrado pela entrega", example = "5.50")
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    @Schema(description = "Tempo médio de entrega em minutos", example = "45")
    private Integer tempoEntrega;
}