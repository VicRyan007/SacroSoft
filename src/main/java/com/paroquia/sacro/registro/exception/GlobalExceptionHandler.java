package com.paroquia.sacro.registro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<String> handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PessoaSemBatismoException.class)
    public ResponseEntity<String> handlePessoaSemBatismo(PessoaSemBatismoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PessoaJaExistenteException.class)
    public ResponseEntity<String> handlePessoaJaExistente(PessoaJaExistenteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioJaExistenteException.class)
    public ResponseEntity<String> handleUsuarioJaExistente(UsuarioJaExistenteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConjugeNaoEncontradoException.class)
    public ResponseEntity<String> handleConjugeNaoEncontrado(ConjugeNaoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SacramentoJaRegistradoException.class)
    public ResponseEntity<String> handleSacramentoJaRegistrado(SacramentoJaRegistradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        return new ResponseEntity<>("Erro interno do servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 