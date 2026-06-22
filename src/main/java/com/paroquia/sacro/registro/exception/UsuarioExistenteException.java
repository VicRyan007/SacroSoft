package com.paroquia.sacro.registro.exception;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException() {
        super("Usuário já existe no sistema.");
    }
}