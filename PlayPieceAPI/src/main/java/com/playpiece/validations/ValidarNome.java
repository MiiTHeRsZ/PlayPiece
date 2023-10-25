package com.playpiece.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.playpiece.validations.interfaces.IValidarNome;

public class ValidarNome implements ConstraintValidator<IValidarNome, String> {

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        String[] nomeCompleto = valor.split(" ");
        for (String string : nomeCompleto) {
            System.out.println(string);
        }

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
