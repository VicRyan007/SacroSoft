package com.paroquia.sacro.registro.dto;

import com.paroquia.sacro.registro.model.enums.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String nome,login;
    private PerfilUsuario perfil;

}
