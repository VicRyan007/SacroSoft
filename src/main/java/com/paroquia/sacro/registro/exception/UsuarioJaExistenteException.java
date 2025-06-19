package com.paroquia.sacro.registro.exception;

public class UsuarioJaExistenteException extends RuntimeException {
    public UsuarioJaExistenteException() {
        super("O login para este usuário já existe.");
    }
}
