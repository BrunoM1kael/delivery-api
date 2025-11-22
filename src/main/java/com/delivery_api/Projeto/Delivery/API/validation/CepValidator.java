package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<ValidCEP, String> {
    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.trim().isEmpty()) return true; // Deixa o @NotBlank tratar se for vazio
        String apenasNumeros = cep.replaceAll("\\D", "");
        return apenasNumeros.length() == 8;
    }
}