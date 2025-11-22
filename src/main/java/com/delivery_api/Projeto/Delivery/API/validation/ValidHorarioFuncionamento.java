package com.delivery_api.Projeto.Delivery.API.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]-([0-1][0-9]|2[0-3]):[0-5][0-9]$",
        message = "Horário inválido. Use o formato HH:MM-HH:MM (ex: 18:00-23:00)")
public @interface ValidHorarioFuncionamento {
    String message() default "Horário inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}