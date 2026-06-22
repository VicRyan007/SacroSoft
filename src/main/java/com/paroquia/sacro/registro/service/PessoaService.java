package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.exception.ConjugeNaoEncontradoException;
import com.paroquia.sacro.registro.exception.IdInvalidoException;
import com.paroquia.sacro.registro.exception.PessoaNaoEncontradaException;
import com.paroquia.sacro.registro.exception.PessoaSemBatismoException;
import com.paroquia.sacro.registro.exception.SacramentoRegistradoException;
import com.paroquia.sacro.registro.model.Matrimonio;
import com.paroquia.sacro.registro.model.Pessoa;
import com.paroquia.sacro.registro.model.Sacramento;
import com.paroquia.sacro.registro.repository.PessoaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepo;

    public List<Pessoa> findAll() {
        return pessoaRepo.findAll();
    }

    public Pessoa findById(String id) {
        if(!ObjectId.isValid(id)) {
            throw new IdInvalidoException();
        }
        return pessoaRepo.findById(id)
        .orElseThrow(PessoaNaoEncontradaException::new);
    }

    public Pessoa create(Pessoa pessoa){
        validarPrecedenciaEDuplicidade(null, pessoa);
        return pessoaRepo.save(pessoa);
    }

    public Pessoa update(String id, Pessoa pessoaAtualizada){
        Pessoa pessoaExistente = findById(id);

        validarPrecedenciaEDuplicidade(pessoaExistente, pessoaAtualizada);

        pessoaExistente.setNome(pessoaAtualizada.getNome());
        pessoaExistente.setDataNascimento(pessoaAtualizada.getDataNascimento());    
        pessoaExistente.setNomePai(pessoaAtualizada.getNomePai());
        pessoaExistente.setNomeMae(pessoaAtualizada.getNomeMae());
        
        pessoaExistente.setDocumentos(pessoaAtualizada.getDocumentos());
        pessoaExistente.setEndereco(pessoaAtualizada.getEndereco());
        pessoaExistente.setContato(pessoaAtualizada.getContato());

        pessoaExistente.setBatismo(pessoaAtualizada.getBatismo());
        pessoaExistente.setEucaristia(pessoaAtualizada.getEucaristia());
        pessoaExistente.setCrisma(pessoaAtualizada.getCrisma());
        pessoaExistente.setMatrimonio(pessoaAtualizada.getMatrimonio());

        return pessoaRepo.save(pessoaExistente);
    }

    public void delete(String id) {
        Pessoa pessoaExistente = findById(id);
        pessoaRepo.delete(pessoaExistente);
    }

    /**
     * Valida as regras de negócio dos sacramentos: precedência, duplicidade e cônjuges.
     */
    private void validarPrecedenciaEDuplicidade(Pessoa existente, Pessoa atualizada) {
        // 1. Validação de Duplicidade (SacramentoRegistradoException)
        // Se a pessoa já tem o sacramento gravado, ela não pode registrar outro diferente (mudando de livro/registro).
        if (existente != null) {
            if (existente.getBatismo() != null && atualizada.getBatismo() != null 
                    && !isMesmoSacramento(existente.getBatismo(), atualizada.getBatismo())) {
                throw new SacramentoRegistradoException();
            }
            if (existente.getEucaristia() != null && atualizada.getEucaristia() != null 
                    && !isMesmoSacramento(existente.getEucaristia(), atualizada.getEucaristia())) {
                throw new SacramentoRegistradoException();
            }
            if (existente.getCrisma() != null && atualizada.getCrisma() != null 
                    && !isMesmoSacramento(existente.getCrisma(), atualizada.getCrisma())) {
                throw new SacramentoRegistradoException();
            }
            if (existente.getMatrimonio() != null && atualizada.getMatrimonio() != null 
                    && !isMesmoSacramento(existente.getMatrimonio(), atualizada.getMatrimonio())) {
                throw new SacramentoRegistradoException();
            }
        }

        // 2. Validação de Precedência (PessoaSemBatismoException)
        // Para receber Eucaristia, Crisma ou Matrimônio, a pessoa DEVE ser batizada
        boolean temBatismo = (existente != null && existente.getBatismo() != null) || (atualizada.getBatismo() != null);
        if (!temBatismo) {
            if (atualizada.getEucaristia() != null || atualizada.getCrisma() != null || atualizada.getMatrimonio() != null) {
                throw new PessoaSemBatismoException();
            }
        }

        // 3. Validação do Cônjuge no Matrimônio (ConjugeNaoEncontradoException)
        if (atualizada.getMatrimonio() != null) {
            Matrimonio mat = atualizada.getMatrimonio();
            if (mat.getIdConjuge() != null && !mat.getIdConjuge().trim().isEmpty()) {
                if (!pessoaRepo.existsById(mat.getIdConjuge())) {
                    throw new ConjugeNaoEncontradoException();
                }
            }
        }
    }

    /**
     * Verifica se o sacramento recebido é o mesmo já registrado (ou seja, apenas uma edição de campos secundários).
     * É considerado o mesmo se possuir o mesmo livroId e número de registro.
     */
    private boolean isMesmoSacramento(Sacramento s1, Sacramento s2) {
        if (s1 == null || s2 == null) return false;
        
        // Se ambos não possuem livroId ou número de registro definidos ainda, assumimos que pode ser o mesmo em edição inicial
        if (s1.getLivroId() == null || s2.getLivroId() == null || s1.getNumeroRegistro() == null || s2.getNumeroRegistro() == null) {
            return true; 
        }

        return s1.getLivroId().equals(s2.getLivroId()) && s1.getNumeroRegistro().equals(s2.getNumeroRegistro());
    }
}