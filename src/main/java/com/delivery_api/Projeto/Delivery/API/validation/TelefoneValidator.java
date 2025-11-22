package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
        // Inicialização se necessária
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        // Se for null, consideramos válido (use @NotNull se for obrigatório)
        // para permitir campos opcionais
        if (telefone == null || telefone.trim().isEmpty()) {
            return true;
        }

        // Remove tudo que não é número para contar os dígitos
        String apenasNumeros = telefone.replaceAll("\\D", "");

        // Valida se tem 10 ou 11 dígitos (DDD + Número)
        // Ex: 11999998888 (11) ou 1133334444 (10)
        return apenasNumeros.length() >= 10 && apenasNumeros.length() <= 11;
    }
}