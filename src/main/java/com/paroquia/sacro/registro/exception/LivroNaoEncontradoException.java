package com.paroquia.sacro.registro.exception;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException() {
        super("Livro não encontrado.");
    }
}
