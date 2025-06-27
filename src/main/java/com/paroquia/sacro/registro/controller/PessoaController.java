package com.paroquia.sacro.registro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paroquia.sacro.registro.exception.PessoaJaExistenteException;
import com.paroquia.sacro.registro.exception.PessoaNaoEncontradaException;
import com.paroquia.sacro.registro.model.Pessoa;
import com.paroquia.sacro.registro.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    // GET - Listar todas as pessoas
    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodos(){
        try{
            List<Pessoa> pessoas = pessoaService.findAll();
            return ResponseEntity.ok(pessoas);
        } catch (PessoaNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET - Buscar pessoa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable String id){
        try{
            Pessoa pessoa = pessoaService.findById(id);
            return ResponseEntity.ok(pessoa);
        } catch (PessoaNaoEncontradaException p) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST - Cadastrar nova pessoa
    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@RequestBody Pessoa pessoa){
        try{
            Pessoa novaPessoa = pessoaService.createPessoa(pessoa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
        } catch ( PessoaJaExistenteException p){
            return ResponseEntity.badRequest().build();
        } catch( Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT - Atualizar pessoa existente
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable String id, @RequestBody Pessoa pessoa){
        try{
            Pessoa pessoaAtualizada = pessoaService.updatePessoa(id, pessoa);
            return ResponseEntity.ok(pessoaAtualizada);
        } catch (PessoaNaoEncontradaException p){
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

} 