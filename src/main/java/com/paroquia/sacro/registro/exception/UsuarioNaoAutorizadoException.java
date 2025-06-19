package com.paroquia.sacro.registro.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {
    public UsuarioNaoAutorizadoException() {
        super("Apenas o padre pode realizar essa operação.");
    }
}
