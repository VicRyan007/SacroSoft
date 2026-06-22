package com.paroquia.sacro.registro.controller;


import com.paroquia.sacro.registro.model.Livro;
import com.paroquia.sacro.registro.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<Livro>> getAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getById(@PathVariable String id) {
        return ResponseEntity.ok(livroService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Livro> create(@RequestBody Livro livro) {
        Livro novoLivro = livroService.create(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable String id, @RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.update(id,livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }


} 