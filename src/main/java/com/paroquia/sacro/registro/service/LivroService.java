package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.exception.IdInvalidoException;
import com.paroquia.sacro.registro.exception.LivroNaoEncontradoException;
import com.paroquia.sacro.registro.model.Livro;
import com.paroquia.sacro.registro.repository.LivroRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    public Livro findById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IdInvalidoException();
        }
        return livroRepository.findById(id)
                .orElseThrow(LivroNaoEncontradoException::new);
    }

    public Livro create(Livro livro) {
        validarLivro(livro);
        return livroRepository.save(livro);
    }

    public Livro update(String id, Livro livroAtualizado) {
        Livro livroExistente = findById(id);

        validarLivro(livroAtualizado);

        livroExistente.setTipoSacramento(livroAtualizado.getTipoSacramento());
        livroExistente.setNumero(livroAtualizado.getNumero());
        livroExistente.setAnoInicio(livroAtualizado.getAnoInicio());
        livroExistente.setAnoFim(livroAtualizado.getAnoFim());

        return livroRepository.save(livroExistente);
    }

    public void delete(String id){
        Livro livroExistente = findById(id);
        livroRepository.delete(livroExistente);
    }

    private void validarLivro(Livro livro) {

        if (livro.getTipoSacramento() == null) {
            throw new RuntimeException("O tipo de sacramento do livro é obrigatório.");
        }

        if (livro.getNumero() == null) {
            throw new RuntimeException("O número do livro é obrigatório");
        }

        if (livro.getAnoInicio() == null || livro.getAnoFim() == null) {
            throw  new RuntimeException("O ano de início/fim não deve estar vazio");
        }

        if (livro.getAnoInicio() != null && livro.getAnoFim() != null) {
            if (livro.getAnoInicio() > livro.getAnoFim()) {
                throw new RuntimeException("O ano de início do livro deve ser menor ou igual ao ano de fim.");
            }
        }

//        if(livroRepository.existsByTipoSacramentoAndNumero(livro.getTipoSacramento(), livro.getNumero())){
//            throw new RuntimeException("Já existe um livro com esse tipo de sacramento e número");
//        }

    }






} 