package com.delivery_api.Projeto.Delivery.API.dto;

import com.delivery_api.Projeto.Delivery.API.validation.ValidCEP;
import com.delivery_api.Projeto.Delivery.API.validation.ValidCategoria;
import com.delivery_api.Projeto.Delivery.API.validation.ValidTelefone;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RestauranteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    @ValidCategoria
    private String categoria;

    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O CEP é obrigatório")
    @ValidCEP
    private String cep;

    @NotBlank(message = "O telefone é obrigatório")
    @ValidTelefone
    private String telefone;

    @DecimalMin(value = "0.0", message = "A taxa de entrega deve ser positiva")
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega é obrigatório")
    @Min(value = 10, message = "Tempo mínimo é 10 minutos")
    @Max(value = 120, message = "Tempo máximo é 120 minutos")
    private Integer tempoEntrega;
}