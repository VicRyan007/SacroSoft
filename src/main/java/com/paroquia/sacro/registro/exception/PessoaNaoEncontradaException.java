package com.paroquia.sacro.registro.exception;

public class PessoaNaoEncontradaException extends RuntimeException {
    public PessoaNaoEncontradaException() {
        super("Pessoa Não encontrada");
    }
}