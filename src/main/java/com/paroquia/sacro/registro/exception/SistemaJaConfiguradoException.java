package com.paroquia.sacro.registro.exception;

public class SistemaJaConfiguradoException extends RuntimeException {
    public SistemaJaConfiguradoException() {
        super("O sistema já foi configurado");
    }
}
