package com.paroquia.sacro.registro.exception;

public class ConjugeNaoEncontradoException extends RuntimeException {
    public ConjugeNaoEncontradoException() {
        super("O cônjuge não foi encontrado no sistema.");
    }
}
