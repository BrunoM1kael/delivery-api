package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CategoriaValidator implements ConstraintValidator<ValidCategoria, String> {

    private final List<String> CATEGORIAS_PERMITIDAS = Arrays.asList(
            "Italiana", "Brasileira", "Japonesa", "Lanches", "Pizza"
    );

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor == null || valor.trim().isEmpty()) return true; // @NotBlank cuida disso
        return CATEGORIAS_PERMITIDAS.stream()
                .anyMatch(c -> c.equalsIgnoreCase(valor));
    }
}