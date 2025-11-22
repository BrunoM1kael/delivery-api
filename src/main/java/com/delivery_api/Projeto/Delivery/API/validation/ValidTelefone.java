package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelefoneValidator.class) // Liga a anotação à classe de lógica
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTelefone {

    String message() default "Formato de telefone inválido. Use o formato (XX) XXXXX-XXXX ou apenas dígitos.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}