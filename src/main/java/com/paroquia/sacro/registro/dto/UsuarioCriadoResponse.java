package com.paroquia.sacro.registro.dto;

import com.paroquia.sacro.registro.model.enums.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCriadoResponse {

    private String nome;
    private String login;
    private String senhaGerada;
    private PerfilUsuario perfil;
    private String paroquia;

}
