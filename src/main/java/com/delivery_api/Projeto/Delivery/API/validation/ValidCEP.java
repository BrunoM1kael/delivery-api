package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CepValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCEP {
    String message() default "CEP inválido. Formato aceito: 12345-678 ou 12345678";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}