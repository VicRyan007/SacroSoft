package com.paroquia.sacro.registro.exception;

public class PessoaSemBatismoException extends RuntimeException {
    public PessoaSemBatismoException() {
        super("É necessário que a pessoa tenha sido batizada para realizar esta operação.");
    }
}