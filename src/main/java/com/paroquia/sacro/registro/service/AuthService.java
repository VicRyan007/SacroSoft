package com.paroquia.sacro.registro.service;

import com.paroquia.sacro.registro.dto.LoginRequest;
import com.paroquia.sacro.registro.dto.LoginResponse;
import com.paroquia.sacro.registro.exception.CredenciaisInvalidasException;
import com.paroquia.sacro.registro.model.Usuario;
import com.paroquia.sacro.registro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByLogin(request.getLogin())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        if (usuario.getAtivo() == null || !usuario.getAtivo() ) {
            throw new CredenciaisInvalidasException();
        }

        return new LoginResponse(
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil()
        );

    }


} 