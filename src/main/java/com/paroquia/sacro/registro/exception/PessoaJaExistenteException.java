package com.paroquia.sacro.registro.exception;

public class PessoaJaExistenteException extends RuntimeException {
    public PessoaJaExistenteException() {
        super("O registro para esta pessoa já existe.");
    }
}
