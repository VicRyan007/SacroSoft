package com.paroquia.sacro.registro.exception;

public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException() {
        super("Credenciais inválidas. Tente novamente.");
    }
}
