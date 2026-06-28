package com.paroquia.sacro.registro.exception;

public class ConfiguracaoNaoEncontradaException extends RuntimeException {
    public ConfiguracaoNaoEncontradaException() {
        super("Configuração do sistema não encontrada");
    }
}
