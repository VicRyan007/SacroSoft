package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.dto.UsuarioCriadoResponse;
import com.paroquia.sacro.registro.exception.ConfiguracaoNaoEncontradaException;
import com.paroquia.sacro.registro.exception.IdInvalidoException;
import com.paroquia.sacro.registro.exception.UsuarioNaoEncontradoException;
import com.paroquia.sacro.registro.model.ConfiguracaoSistema;
import com.paroquia.sacro.registro.model.Usuario;
import com.paroquia.sacro.registro.model.enums.PerfilUsuario;
import com.paroquia.sacro.registro.repository.UsuarioRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConfiguracaoSistemaService configuracaoService;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IdInvalidoException();
        }
        return usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public UsuarioCriadoResponse create(Usuario usuario) {

        ConfiguracaoSistema configuracao = configuracaoService.buscarConfiguracao();
        if (configuracao == null) {
            throw new ConfiguracaoNaoEncontradaException();
        }

        String loginGerado = gerarLoginUnico(configuracao.getNomeParoquia());
        String senhaGerada = gerarSenha();

        usuario.setParoquia(configuracao.getNomeParoquia());
        usuario.setLogin(loginGerado);
        usuario.setSenha(senhaGerada);
        usuario.setPerfil(PerfilUsuario.SECRETARIO);
        usuario.setAtivo(true);
        usuario.setDataCadastro(LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioCriadoResponse(
                usuarioSalvo.getNome(),
                usuarioSalvo.getLogin(),
                senhaGerada,
                usuarioSalvo.getPerfil(),
                usuarioSalvo.getParoquia()
        );
    }

    public Usuario update(String id, Usuario usuarioAtualizado) {

        if (!ObjectId.isValid(id)) {
            throw new IdInvalidoException();
        }

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }

        if (usuarioAtualizado.getPerfil() != null) {
            usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());
        }

        if (usuarioAtualizado.getAtivo() != null) {
            usuarioExistente.setAtivo(usuarioAtualizado.getAtivo());
        }

        return usuarioRepository.save(usuarioExistente);

    }

    public void disable(String id) {
        Usuario usuarioExistente = findById(id);
        usuarioExistente.setAtivo(false);
        usuarioRepository.save(usuarioExistente);
    }

    private String gerarLoginUnico(String paroquia) {

        String login;

        do{
            login = gerarLogin(paroquia);
        }while (usuarioRepository.existsByLogin(login));

        return login;
    }

    private String gerarLogin(String nomeParoquia) {
        String prefixo = nomeParoquia
                .toLowerCase()
                .replace("paróquia","")
                .replace("paroquia","")
                .replace(" ","")
                .replace("ã", "a")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace("ç", "c");

        String sufixo = java.util.UUID.randomUUID().toString().substring(0, 4);

        return prefixo + "-sec-" + sufixo;

    }

    private String gerarSenha() {
        return "Sacro" + java.util.UUID.randomUUID().toString().substring(0, 4) + "@1";
    }


}
