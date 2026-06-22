package com.paroquia.sacro.registro.exception;

public class ConjugeNaoEncontradoException extends RuntimeException {
    public ConjugeNaoEncontradoException() {
        super("Cônjuge não encontrado");
    }
}