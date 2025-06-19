package com.paroquia.sacro.registro.exception;

public class PessoaSemBatismoException extends RuntimeException {
    public PessoaSemBatismoException() {
        super("Batismo obrigatório para cadastrar a pessoa.");
    }
}
