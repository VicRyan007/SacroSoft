package com.paroquia.sacro.registro.repository;

import com.paroquia.sacro.registro.model.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LivroRepository extends MongoRepository<Livro,String> {

} 