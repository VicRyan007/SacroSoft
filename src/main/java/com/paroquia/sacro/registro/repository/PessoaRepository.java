package com.paroquia.sacro.registro.repository;

import com.paroquia.sacro.registro.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PessoaRepository extends MongoRepository<Pessoa,String> {
} 