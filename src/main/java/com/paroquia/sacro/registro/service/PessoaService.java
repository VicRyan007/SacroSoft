package com.paroquia.sacro.registro.service;

import com.mongodb.client.model.ReturnDocument;
import com.paroquia.sacro.registro.model.Pessoa;
import com.paroquia.sacro.registro.repository.PessoaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repo;

    public List<Pessoa> findAll() { return repo.findAll(); }

    public Pessoa findById(String id) throws Exception {
        if(!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Id inválido.");
        }

        Optional<Pessoa> pessoa = repo.findById(id);

        return pessoa.orElseThrow(() -> new Exception("Usuário não encontrado. Id: " + id));

    }

} 