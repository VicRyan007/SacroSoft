package com.paroquia.sacro.registro.repository;

import com.paroquia.sacro.registro.model.ConfiguracaoSistema;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoSistemaRepository extends MongoRepository<ConfiguracaoSistema, String> {
}
