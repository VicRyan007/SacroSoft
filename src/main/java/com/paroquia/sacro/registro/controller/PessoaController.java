package com.paroquia.sacro.registro.controller;

import com.paroquia.sacro.registro.model.Pessoa;
import com.paroquia.sacro.registro.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll() {
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable String id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.create(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable String id, @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.update(id, pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}