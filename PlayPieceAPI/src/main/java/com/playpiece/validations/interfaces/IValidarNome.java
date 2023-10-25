package com.playpiece.validations.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import com.playpiece.validations.ValidarNome;

@Documented
@Constraint(validatedBy = ValidarNome.class)
@Target({ FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidarNome {
    String message() default "Nome deve conter Nome e Sobrenome com 3 caracteres cada um";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
