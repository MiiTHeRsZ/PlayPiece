package com.validations;

import com.validations.interfaces.IValidarNome;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidarNome implements ConstraintValidator<IValidarNome, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        String[] nomeCompleto = valor.split(" ");

        if (nomeCompleto.length == 2) {
            String nome = nomeCompleto[0];
            String sobrenome = nomeCompleto[1];

            if (nome.trim() == ""
                    || sobrenome.trim() == ""
                    || nome.trim().length() < 3
                    || sobrenome.trim().length() < 3) {

                return false;

            } else {
                return true;
            }

        } else {
            return false;
        }

    }

}
