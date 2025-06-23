package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.exception.UsuarioJaExistenteException;
import com.paroquia.sacro.registro.exception.UsuarioNaoAutorizadoException;
import com.paroquia.sacro.registro.exception.UsuarioNaoEncontradoException;
import com.paroquia.sacro.registro.model.Usuario;
import com.paroquia.sacro.registro.model.enums.PerfilUsuario;
import com.paroquia.sacro.registro.repository.UsuarioRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario createUsuario(Usuario usuarioPadre) {
        if(usuarioPadre.getPerfil() != PerfilUsuario.PADRE){
            throw new UsuarioNaoAutorizadoException();
        }

        // Login e senha aleatorios gerado
        String loginGerado = UUID.randomUUID().toString().substring(0,8);
        String senhaGerada = UUID.randomUUID().toString().substring(0,12);

        // Criptografia de senha
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(senhaGerada); // senha criptografada gerada

        // Criar o objeto usuário com os dados gerados
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioPadre.getNome());
        usuario.setLogin(loginGerado); // Atribui o login gerado
        usuario.setSenha(senhaCriptografada); // A senha gerada é criptografada e atribuída
        usuario.setPerfil(PerfilUsuario.SECRETARIO); // Perfil de funcionário
        usuario.setAtivo(true); // O usuário será ativo por padrão
        usuario.setDataCadastro(LocalDateTime.now()); // Data de cadastro
        usuario.setSenhaGerada(true); // Indica que a senha foi gerada automaticamente

        // Salva o novo usuário no banco de dados
        return repo.save(usuario);
    }

    public List<Usuario> findAll() {
        return  repo.findAll();
    }

    public Usuario findById(String id) throws Exception{
        if(!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Id inválido.");
        }

        Optional<Usuario> usuario = repo.findById(id);

        if(usuario.isPresent()){
            return usuario.get();
        }else{
            throw new UsuarioNaoEncontradoException();
        }
    }

    public Usuario updateUsuario(String id, Usuario updatedUsuario, Usuario usuarioPadre) throws Exception {

        if(usuarioPadre.getPerfil() != PerfilUsuario.PADRE) {
            throw new UsuarioNaoAutorizadoException();
        }

        Usuario existingUsuario = repo.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());

        // Atualização apenas dos campo permitidos
        if(updatedUsuario.getNome() != null) {
            existingUsuario.setNome(updatedUsuario.getNome());
        }

        if(updatedUsuario.getLogin() != null && !updatedUsuario.getLogin().equals(existingUsuario.getLogin())){
            existingUsuario.setLogin(updatedUsuario.getLogin());
        }

        if(updatedUsuario.getPerfil() != null && !existingUsuario.getPerfil().equals(updatedUsuario.getPerfil())){
            existingUsuario.setPerfil(updatedUsuario.getPerfil());
        }

        if(updatedUsuario.getAtivo() != null){
            existingUsuario.setAtivo(updatedUsuario.getAtivo());
        }

        return repo.save(existingUsuario);

    }

    public void deleteUsuario(String id, Usuario usuarioPadre) {
        if(usuarioPadre.getPerfil() != PerfilUsuario.PADRE){
            throw  new UsuarioNaoAutorizadoException();
        }
        Usuario existingUsuario = repo.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());
        repo.delete(existingUsuario);
    }

} 