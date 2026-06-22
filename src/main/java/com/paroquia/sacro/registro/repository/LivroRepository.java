package com.paroquia.sacro.registro.repository;

import com.paroquia.sacro.registro.model.Livro;
import com.paroquia.sacro.registro.model.enums.TipoSacramento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends MongoRepository<Livro, String> {

    boolean existsByTipoSacramentoAndNumero(TipoSacramento tipoSacramento, Integer numero);

}