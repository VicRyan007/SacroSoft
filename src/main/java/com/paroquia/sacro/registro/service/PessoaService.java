package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.exception.PessoaJaExistenteException;
import com.paroquia.sacro.registro.exception.PessoaNaoEncontradaException;
import com.paroquia.sacro.registro.exception.PessoaSemBatismoException;
import com.paroquia.sacro.registro.model.Pessoa;
import com.paroquia.sacro.registro.model.Documento;
import com.paroquia.sacro.registro.model.Endereco;
import com.paroquia.sacro.registro.model.Contato;
import com.paroquia.sacro.registro.repository.PessoaRepository;
import com.paroquia.sacro.registro.repository.LivroRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repo;

    @Autowired
    private LivroRepository livroRepository;

    public Pessoa createPessoa(Pessoa pessoa){
        if(pessoa.getId() != null && repo.existsById(pessoa.getId())){
            throw new PessoaJaExistenteException();
        }
        if (pessoa.getBatismo() == null) {
            throw new PessoaSemBatismoException();
        }
        if (pessoa.getLivroId() == null || pessoa.getLivroId().trim().isEmpty()) {
            throw new IllegalArgumentException("O campo livroId é obrigatório para registrar uma pessoa.");
        }
        if (!livroRepository.existsById(pessoa.getLivroId())) {
            throw new IllegalArgumentException("O livro informado não existe.");
        }
        return repo.save(pessoa);
    }

    public List<Pessoa> findAll() {
        return repo.findAll();
    }

    public Pessoa findById(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Id inválido.");
        }

        Optional<Pessoa> pessoa = repo.findById(id);

        if (pessoa.isPresent()) {
            if (pessoa.get().getBatismo() == null) {
                throw new PessoaSemBatismoException();
            }
        return pessoa.get();
        }
        else{
            throw new PessoaNaoEncontradaException();
        }

    }

    public Pessoa updatePessoa(String id, Pessoa updatedPessoa) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Id inválido.");
        }

        Optional<Pessoa> optionalPessoa = repo.findById(id);
        if (!optionalPessoa.isPresent()) {
            throw new PessoaNaoEncontradaException();
        }
        
        Pessoa existingPessoa = optionalPessoa.get();
        
        if (existingPessoa.getBatismo() == null) {
            throw new PessoaSemBatismoException();
        }

        if (updatedPessoa.getNome() != null && !updatedPessoa.getNome().trim().isEmpty()) {
            existingPessoa.setNome(updatedPessoa.getNome().trim());
        }
        if (updatedPessoa.getDataNascimento() != null) {
            existingPessoa.setDataNascimento(updatedPessoa.getDataNascimento());
        }
        if (updatedPessoa.getNomePai() != null && !updatedPessoa.getNomePai().trim().isEmpty()) {
            existingPessoa.setNomePai(updatedPessoa.getNomePai().trim());
        }
        if (updatedPessoa.getNomeMae() != null && !updatedPessoa.getNomeMae().trim().isEmpty()) {
            existingPessoa.setNomeMae(updatedPessoa.getNomeMae().trim());
        }

        if (updatedPessoa.getDocumentos() != null) {
            Documento existingDoc = existingPessoa.getDocumentos();
            Documento updatedDoc = updatedPessoa.getDocumentos();
            
            if (existingDoc == null) {
                existingDoc = new Documento();
            }
            
            if (updatedDoc.getCpf() > 0 && String.valueOf(updatedDoc.getCpf()).length() == 11) {
                existingDoc.setCpf(updatedDoc.getCpf());
            }
            if (updatedDoc.getRg() > 0 && String.valueOf(updatedDoc.getRg()).length() <= 9) {
                existingDoc.setRg(updatedDoc.getRg());
            }
            
            existingPessoa.setDocumentos(existingDoc);
        }
        
        if (updatedPessoa.getEndereco() != null) {
            Endereco existingEnd = existingPessoa.getEndereco();
            Endereco updatedEnd = updatedPessoa.getEndereco();
            
            if (existingEnd == null) {
                existingEnd = new Endereco();
            }
            
            if (updatedEnd.getRua() != null && !updatedEnd.getRua().trim().isEmpty()) {
                existingEnd.setRua(updatedEnd.getRua().trim());
            }
            if (updatedEnd.getBairro() != null && !updatedEnd.getBairro().trim().isEmpty()) {
                existingEnd.setBairro(updatedEnd.getBairro().trim());
            }
            if (updatedEnd.getCidade() != null && !updatedEnd.getCidade().trim().isEmpty()) {
                existingEnd.setCidade(updatedEnd.getCidade().trim());
            }
            if (updatedEnd.getEstado() != null && !updatedEnd.getEstado().trim().isEmpty()) {
                existingEnd.setEstado(updatedEnd.getEstado().trim());
            }
            if (updatedEnd.getCep() != null && !updatedEnd.getCep().trim().isEmpty()) {
                existingEnd.setCep(updatedEnd.getCep().trim());
            }
            if (updatedEnd.getNumero() > 0) {
                existingEnd.setNumero(updatedEnd.getNumero());
            }
            
            existingPessoa.setEndereco(existingEnd);
        }
        
        if (updatedPessoa.getContato() != null) {
            Contato existingCont = existingPessoa.getContato();
            Contato updatedCont = updatedPessoa.getContato();
            
            if (existingCont == null) {
                existingCont = new Contato();
            }
            
            if (updatedCont.getTelefone() != null && !updatedCont.getTelefone().trim().isEmpty()) {
                existingCont.setTelefone(updatedCont.getTelefone().trim());
            }
            if (updatedCont.getEmail() != null && !updatedCont.getEmail().trim().isEmpty()) {
                existingCont.setEmail(updatedCont.getEmail().trim());
            }
            
            existingPessoa.setContato(existingCont);
        }

        if (updatedPessoa.getBatismo() != null) {
            existingPessoa.setBatismo(updatedPessoa.getBatismo());
        }
        if (updatedPessoa.getEucaristia() != null) {
            existingPessoa.setEucaristia(updatedPessoa.getEucaristia());
        }
        if (updatedPessoa.getCrisma() != null) {
            existingPessoa.setCrisma(updatedPessoa.getCrisma());
        }
        if (updatedPessoa.getMatrimonio() != null) {
            existingPessoa.setMatrimonio(updatedPessoa.getMatrimonio());
        }

        if (updatedPessoa.getLivroId() != null && !updatedPessoa.getLivroId().trim().isEmpty()) {
            if (!livroRepository.existsById(updatedPessoa.getLivroId().trim())) {
                throw new IllegalArgumentException("O livro informado não existe.");
            }
            existingPessoa.setLivroId(updatedPessoa.getLivroId().trim());
        }

        return repo.save(existingPessoa);
    }

    public void deletePessoa(String id) {
        Pessoa existingPessoa = repo.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException());
        repo.delete(existingPessoa);
    }

} 