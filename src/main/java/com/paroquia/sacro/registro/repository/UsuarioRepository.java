package com.paroquia.sacro.registro.repository;

import com.paroquia.sacro.registro.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);

    boolean existsByLogin(String login);


}