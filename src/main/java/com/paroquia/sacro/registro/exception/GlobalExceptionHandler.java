package com.paroquia.sacro.registro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // UsuarioNaoAutorizadoException (Erro de permissão)
    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<String> handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    // PessoaSemBatismoException (Erro de cadastro sem batismo)
    @ExceptionHandler(PessoaSemBatismoException.class)
    public ResponseEntity<String> handlePessoaSemBatismo(PessoaSemBatismoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // UsuarioJaExistenteException (Erro de usuário duplicado)
    @ExceptionHandler(UsuarioJaExistenteException.class)
    public ResponseEntity<String> handleUsuarioJaExistente(UsuarioJaExistenteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ConjugeNaoEncontradoException (Erro de casamento sem cônjuge válido)
    @ExceptionHandler(ConjugeNaoEncontradoException.class)
    public ResponseEntity<String> handleConjugeNaoEncontrado(ConjugeNaoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // SacramentoJaRegistradoException (Erro de sacramento duplicado)
    @ExceptionHandler(SacramentoJaRegistradoException.class)
    public ResponseEntity<String> handleSacramentoJaRegistrado(SacramentoJaRegistradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Exceção genérica (para erros inesperados)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
