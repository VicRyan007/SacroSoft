package com.paroquia.sacro.registro.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {
    public UsuarioNaoAutorizadoException() {
        super("Usuário não autorizado para realizar esta operação.");
    }
}