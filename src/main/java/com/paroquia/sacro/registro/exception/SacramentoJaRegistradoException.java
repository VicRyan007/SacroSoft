package com.paroquia.sacro.registro.exception;

public class SacramentoJaRegistradoException extends RuntimeException {
    public SacramentoJaRegistradoException() {
        super("Este sacramento já foi registrado para esta pessoa.");
    }
}
