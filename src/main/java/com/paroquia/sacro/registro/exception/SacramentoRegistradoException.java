package com.paroquia.sacro.registro.exception;

public class SacramentoRegistradoException extends RuntimeException {
    public SacramentoRegistradoException() {
        super("Sacramento já registrado para esta pessoa.");
    }
}