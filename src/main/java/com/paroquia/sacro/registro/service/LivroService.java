package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.exception.LivroNaoEncontradoException;
import com.paroquia.sacro.registro.model.Livro;
import com.paroquia.sacro.registro.repository.LivroRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repo;

    public Livro createLivro(Livro livro){
        if(livro.getId() != null && repo.existsById(livro.getId())){
            throw new LivroNaoEncontradoException();
        }
        return  repo.save(livro);
    }

    public List<Livro> findAll(){
        return repo.findAll();
    }

    public Livro findById(String id) throws Exception {
        if(!ObjectId.isValid(id)){
            throw new IllegalArgumentException("Id inválido.");
        }
        Optional<Livro> livro = repo.findById(id);

        if(!livro.isPresent()){
            throw new LivroNaoEncontradoException();
        }

        return livro.get();
    }

    public Livro updateLivro(String id, Livro updatedLivro) throws Exception {
            if(!ObjectId.isValid(id)){
                throw new IllegalArgumentException("Id inválido");
            }

            Optional<Livro> optionalLivro = repo.findById(id);
            if(!optionalLivro.isPresent()){
                throw new LivroNaoEncontradoException();
            }

            Livro existingLivro = optionalLivro.get();

            existingLivro.setTipoSacramento(updatedLivro.getTipoSacramento());
            existingLivro.setNumero(updatedLivro.getNumero());
            existingLivro.setAnoInicio(updatedLivro.getAnoInicio());
            existingLivro.setAnoFim(updatedLivro.getAnoFim());
            existingLivro.setParoquia(updatedLivro.getParoquia());
            existingLivro.setCelebranteResponsavel(updatedLivro.getCelebranteResponsavel());

            return repo.save(existingLivro);

    }

    public void deleteLivro(String id){
        Livro existingLivro = repo.findById(id).orElseThrow(() -> new LivroNaoEncontradoException());
        repo.delete(existingLivro);
    }

} 